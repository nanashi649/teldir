$(function() {


	// 削除ボタン（動的追加行対応）
	$(document).on('click', '.btn-delete', function() {
		$(this).closest('tr').remove();
		reindexTable();
	});

	// 行追加ボタン
	$('#addRow').click(function() {
		const index = $('#table tbody tr').length;

		/*const set = new Date(Date.now() + 9 * 60 * 60 * 1000);
		const now = set.toISOString().slice(0, 19);*/

		const newRow = `
      <tr>
        <td>
			<input type="hidden" name="users[${index}].id" value="" />
            <input type="text" name="users[${index}].userName" class="form-control" placeholder="氏名" required /></td>
        <td><input type="tel" name="users[${index}].telNo" class="form-control" placeholder="電話番号" required /></td>
        <td><input type="email" name="users[${index}].mailAddr" class="form-control" placeholder="メールアドレス" required /></td>
        <td><button type="button" class="btn btn-danger btn-sm btn-delete">削除</button></td>
      </tr>`;

		$('#table tbody').append(newRow);
	});

	// 再採番処理
	function reindexTable() {
		$('#table tbody tr').each(function(i) {
			$(this).find('input').each(function() {
				if (this.name.includes('userName')) {
					this.name = `users[${i}].userName`;
				} else if (this.name.includes('telNo')) {
					this.name = `users[${i}].telNo`;
				} else if (this.name.includes('mailAddr')) {
					this.name = `users[${i}].mailAddr`;
				}
			});
		});
	}


});
