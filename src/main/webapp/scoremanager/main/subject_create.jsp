<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">
		<style>
			.form-wrapper {
				width: 100%;
				min-height: 400px;
				padding: 15px 10px 0 10px;
			}

			.form-title {
				background-color: #eeeeee;
				font-size: 20px;
				font-weight: bold;
				padding: 8px 15px;
				margin-bottom: 20px;
			}

			.form-group {
				margin-bottom: 15px;
			}

			.form-group label {
				display: block;
				font-size: 13px;
				margin-bottom: 5px;
			}

			.form-group input {
				width: 100%;
				height: 32px;
				padding: 5px 10px;
				font-size: 13px;
				border: 1px solid #ccc;
				border-radius: 3px;
			}

			.form-actions {
				margin-top: 10px;
			}

			.btn-submit {
				background-color: #007bff;
				color: white;
				border: none;
				padding: 6px 15px;
				font-size: 13px;
				border-radius: 4px;
				cursor: pointer;
			}

			.btn-submit:hover {
				background-color: #0056b3;
			}

			.back-link {
				margin-top: 10px;
				font-size: 13px;
			}

			.back-link a {
				color: #007bff;
				text-decoration: underline;
			}
		</style>

		<div class="form-wrapper">

			<div class="form-title">
				科目情報登録
			</div>

			<form action="SubjectCreateExecute.action" method="post">

				<div class="form-group">
					<label>科目コード</label>
					<input type="text" name="cd" placeholder="科目コードを入力してください">
				</div>

				<div class="form-group">
					<label>科目名</label>
					<input type="text" name="name" placeholder="科目名を入力してください">
				</div>

				<div class="form-actions">
					<input type="submit" value="登録" class="btn-submit">
				</div>

				<div class="back-link">
					<a href="SubjectList.action">戻る</a>
				</div>

			</form>

		</div>
	</c:param>
</c:import>