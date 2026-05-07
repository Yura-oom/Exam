<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目情報削除</c:param>

	<c:param name="content">
		<style>
			.subject-delete-wrap {
				padding: 0 10px;
			}

			.subject-delete-title {
				background-color: #f3f3f3;
				padding: 10px 16px;
				font-size: 20px;
				font-weight: bold;
				color: #222;
				margin-bottom: 28px;
				
			}

			.subject-delete-message {
				font-size: 13px;
				color: #444;
				margin: 0 0 14px 12px;
			}

			.subject-delete-button-area {
				margin-left: 12px;
				margin-bottom: 55px;
			}

			.subject-delete-btn {
				display: inline-block;
				background-color: #dc3545;
				color: #fff;
				border: none;
				border-radius: 4px;
				padding: 8px 14px;
				font-size: 13px;
				text-decoration: none;
				cursor: pointer;
			}

			.subject-delete-btn:hover {
				opacity: 0.9;
			}

			.subject-delete-back {
				margin-left: 12px;
				font-size: 13px;
			}

			.subject-delete-back a {
				color: #4a73ff;
				text-decoration: underline;
			}
		</style>

		<div class="subject-delete-wrap">
			<div class="subject-delete-title">科目情報削除</div>

			<div class="subject-delete-message">
				「${subject.name}(${subject.cd})」を削除してもよろしいですか
			</div>

			<div class="subject-delete-button-area">
				<form action="SubjectDeleteExecute.action" method="post" style="margin:0;">
					<input type="hidden" name="cd" value="${subject.cd}">
					<button type="submit" class="subject-delete-btn">削除</button>
				</form>
			</div>

			<div class="subject-delete-back">
				<a href="SubjectList.action">戻る</a>
			</div>
		</div>
	</c:param>
</c:import>