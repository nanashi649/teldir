$(function() {

	// ✅ 現在のJST時刻をISO形式（小数秒除去）で取得して hidden にセット
	const now = new Date(Date.now() + 9 * 60 * 60 * 1000); // JST: UTC+9
	const set = now.toISOString().slice(0, 19);            // "YYYY-MM-DDTHH:MM:SS"
	$('#lastModified').val(set);

	// ✅ 「非同期保存」ボタン押下 → 確認モーダル表示
	$('#asyncSaveBtn').click(() => {
		$('#confirmModal').modal('show');
	});

	// ✅ 確認モーダル「OK」ボタン → モーダルを閉じて Ajax送信処理へ
	$('#confirmOkBtn').click(() => {
		$('#confirmModal').modal('hide');

		// 🔄 送信データ収集
		const postData = {
			userName: $('#userName').val(),
			telNo: $('#telNo').val(),
			mailAddr: $('#mailAddr').val(),
			id: $('#id').val(),
			groupId: $('#groupId').val(),
			lastModified: $('#lastModified').val()
		};

		// ✅ jQuery.ajax() による非同期送信処理（推奨）
		$.ajax({
			url: '/api/tel/save',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(postData),
			success: function() {
				// ✅ 成功モーダル表示
				$('#successModal').modal('show');
			},
			error: function(xhr) {
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
				// ✅ エラーモーダルにメッセージ表示
				$('#errorModal .modal-body').html(message.replace(/\n/g, '<br>'));
				$('#errorModal').modal('show');
			}
		});


		/*
		// ✅ fetch による非同期送信（参考用：必要があれば切り替えて使用）
		fetch('/api/tel/save', {
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

	// ✅ 成功モーダルの「OK」クリック → 一覧画面へ遷移（必要に応じてURLを変更）
	$('#successOkBtn').click(() => {
		window.location.href = '/'; // 例: '/group/list' に変更してもOK
	});
});
