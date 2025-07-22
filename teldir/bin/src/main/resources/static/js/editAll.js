$(function() {

	// 「非同期保存」ボタン押下時に確認モーダル表示
	$('#asyncSaveBtn').click(() => {
		$('#confirmModal').modal('show');
	});

	// モーダルの「OK」押下時にPOST実行
	$('#confirmOkBtn').click(() => {
		$('#confirmModal').modal('hide');

		const users = [];

		// テーブルの各行から値を取得
		$('#table tbody tr').each(function() {
			const user = {
				id: $(this).find('input[name*=".id"]').val(),
				userName: $(this).find('input[name*=".userName"]').val(),
				telNo: $(this).find('input[name*=".telNo"]').val(),
				mailAddr: $(this).find('input[name*=".mailAddr"]').val(),
				lastModified: $(this).find('input[name*=".lastModified"]').val()
			};
			users.push(user);
		});

		const postData = { users };

		// $.ajax による非同期送信
		$.ajax({
			url: '/api/tel/saveAll',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(postData),
			success: function() {
				$('#successModal').modal('show');
			},
			error: function(jqXHR) {
				let message;
				const status = jqXHR.status;
				const err = jqXHR.responseJSON;

				if (status === 409 && err?.optimisticError) {
					message = err.optimisticError;
				} else if (status === 400 && err) {
					message = Object.values(err).join('\n');
				} else {
					message = '予期せぬエラーが発生しました';
				}

				$('#errorModal .modal-body').html(message.replace(/\n/g, '<br>'));
				$('#errorModal').modal('show');
			}
		});

		/*
		// 元の fetch 実装（参考用にコメントアウトで残しておく）
		fetch('/api/tel/saveAll', {
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
