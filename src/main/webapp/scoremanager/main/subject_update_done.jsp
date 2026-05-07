<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目情報変更</c:param>

	<c:param name="content">
		<style>
			.subject-update-done-wrap {
				padding: 0 10px;
			}

			.subject-update-done-title {
				background-color: #f3f3f3;
				padding: 10px 16px;
				font-size: 20px;
				font-weight: bold;
				color: #222;
				margin-bottom: 12px;
			}

			.subject-update-done-message {
				background-color: #97c9b3;
				color: #333;
				text-align: center;
				padding: 8px 0;
				margin: 0 0 90px 0;
				font-size: 13px;
			}

			.subject-update-done-link {
				margin-left: 12px;
				font-size: 13px;
			}

			.subject-update-done-link a {
				color: #4a73ff;
				text-decoration: underline;
			}
		</style>

		<div class="subject-update-done-wrap">
			<div class="subject-update-done-title">科目情報変更</div>

			<div class="subject-update-done-message">
				変更が完了しました
			</div>

			<div class="subject-update-done-link">
				<a href="SubjectList.action">科目一覧</a>
			</div>
		</div>
	</c:param>
</c:import>