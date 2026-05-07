<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目情報削除</c:param>

	<c:param name="content">

		<style>
			.subject-delete-done-wrap {
				padding: 0 10px;
			}

			.subject-delete-done-title {
				background-color: #f1f1f1;
				font-size: 32px;
				font-weight: bold;
				padding: 12px 18px;
				margin-bottom: 20px;
				color: #222;
			}

			.subject-delete-done-message {
				background-color: #97c9b3;
				color: #333;
				text-align: center;
				padding: 10px 0;
				margin-bottom: 60px;
				font-size: 22px;
			}

			.subject-delete-done-link {
				margin-left: 16px;
				font-size: 22px;
			}

			.subject-delete-done-link a {
				color: #4a73ff;
				text-decoration: underline;
			}
		</style>

		<div class="subject-delete-done-wrap">
			<div class="subject-delete-done-title">科目情報削除</div>

			<div class="subject-delete-done-message">
				削除が完了しました
			</div>

			<div class="subject-delete-done-link">
				<a href="SubjectList.action">科目一覧</a>
			</div>
		</div>

	</c:param>
</c:import>