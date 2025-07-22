$(function() {

	// ✅ 現在のJST時刻をISO形式（小数秒除去）で取得して hidden にセット
	const now = new Date(Date.now() + 9 * 60 * 60 * 1000); // JST（+9時間）
	const set = now.toISOString().slice(0, 19);            // "YYYY-MM-DDTHH:MM:SS"
	$('#lastModified').val(set);

	// ✅ 「非同期保存」ボタン押下 → 確認モーダル表示
	$('#asyncSaveBtn').click(() => {
		$('#confirmModal').modal('show');
	});

	// ✅ 確認モーダル「OK」ボタン → モーダルを閉じて Ajax送信処理へ
	$('#confirmOkBtn').click(() => {
		$('#confirmModal').modal('hide');

		// 🔄 送信データ収集（グループ編集用）
		const postData = {
			groupName: $('#groupName').val(),
			groupId: $('#groupId').val(),
			lastModified: $('#lastModified').val()
		};

		// ✅ jQuery.ajax() による非同期送信（メイン実装）
		$.ajax({
			url: '/api/tel/saveGroup', 
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(postData),
			success: function () {
				$('#successModal').modal('show'); // 成功モーダル表示
			},
			error: function (xhr) {
				let message;
				try {
					const err = JSON.parse(xhr.responseText);
					if (xhr.status === 409 && err.optimisticError) {
						message = err.optimisticError;
					} else if (xhr.status === 400) {
						message = Object.values(err).join('\n');
					} else {
						message = '予期せぬエラーが発生しました';
					}
				} catch (e) {
					message = 'エラーが発生しました';
				}
				$('#errorModal .modal-body').html(message.replace(/\n/g, '<br>'));
				$('#errorModal').modal('show');
			}
		});

		/*
		// ✅ fetch による Ajax送信（コメントで残す）
		fetch('/api/tel/saveGroup', {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify(postData)
		})
		.then(response => {
			if (response.ok) {
				$('#successModal').modal('show');
				return;
			}
			return response.json().then(err => {
				if (response.status === 409 && err.optimisticError) {
					throw { type: 'optimistic', message: err.optimisticError };
				} else if (response.status === 400) {
					throw { type: 'validation', message: err };
				} else {
					throw { type: 'system', message: "予期せぬエラーが発生しました" };
				}
			});
		})
		.catch(err => {
			let message;
			if (err.type === 'optimistic') {
				message = err.message;
			} else if (err.type === 'validation') {
				message = Object.values(err.message).join('\n');
			} else {
				message = err.message || 'エラーが発生しました';
			}
			$('#errorModal .modal-body').text(message);
			$('#errorModal').modal('show');
		});
		*/
	});

	// ✅ 成功モーダル「OK」ボタン → 一覧画面へ遷移
	$('#successOkBtn').click(() => {
		window.location.href = '/'; // 必要に応じて '/group/list' 等に変更
	});

});
