<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目情報変更</c:param>

	<c:param name="content">
		<style>
			.subject-update-wrap {
				padding: 0 10px;
			}

			.subject-update-title {
				background-color: #f3f3f3;
				padding: 10px 16px;
				font-size: 20px;
				font-weight: bold;
				color: #222;
				margin-bottom: 18px;
			}

			.subject-update-form {
				margin: 0 12px;
			}

			.subject-update-label {
				display: block;
				font-size: 13px;
				color: #444;
				margin-bottom: 6px;
			}

			.subject-update-code {
				font-size: 13px;
				color: #333;
				margin: 0 0 18px 0;
			}

			.subject-update-error {
				color: #f0a020;
				font-size: 12px;
				margin: 0 0 18px 0;
			}

			.subject-update-input {
				width: 100%;
				height: 34px;
				padding: 6px 10px;
				font-size: 13px;
				border: 1px solid #ced4da;
				border-radius: 4px;
				box-sizing: border-box;
				margin-bottom: 12px;
			}

			.subject-update-btn {
				display: inline-block;
				background-color: #0d6efd;
				color: #fff;
				border: none;
				border-radius: 4px;
				padding: 6px 14px;
				font-size: 13px;
				cursor: pointer;
			}

			.subject-update-btn:hover {
				opacity: 0.9;
			}

			.subject-update-back {
				margin-top: 10px;
				font-size: 13px;
			}

			.subject-update-back a {
				color: #4a73ff;
				text-decoration: underline;
			}
		</style>

		<div class="subject-update-wrap">
			<div class="subject-update-title">科目情報変更</div>

			<div class="subject-update-form">
				<form action="SubjectUpdateExecute.action" method="post">

					<label class="subject-update-label">科目コード</label>
					<div class="subject-update-code">${cd}</div>
					<input type="hidden" name="cd" value="${cd}">

					<c:if test="${not empty error}">
						<div class="subject-update-error">${error}</div>
					</c:if>

					<label class="subject-update-label" for="name">科目名</label>
					<input
						type="text"
						id="name"
						name="name"
						class="subject-update-input"
						value="${name}"
						placeholder="科目名を入力してください"
						required
					>

					<div>
						<button type="submit" class="subject-update-btn">変更</button>
					</div>

					<div class="subject-update-back">
						<a href="SubjectList.action">戻る</a>
					</div>

				</form>
			</div>
		</div>
	</c:param>
</c:import>