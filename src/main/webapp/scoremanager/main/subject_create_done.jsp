<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">
		<style>
			.done-wrapper {
				width: 100%;
				min-height: 400px;
				padding: 15px 10px 0 10px;
			}

			.done-title {
				background-color: #eeeeee;
				font-size: 20px;
				font-weight: bold;
				padding: 8px 15px;
				margin-bottom: 15px;
			}

			.done-message {
				background-color: #8cc7aa;
				text-align: center;
				font-size: 13px;
				padding: 6px 0;
				margin-bottom: 95px;
			}

			.done-links {
				font-size: 13px;
			}

			.done-links a {
				color: #007bff;
				text-decoration: underline;
				margin-right: 55px;
			}
		</style>

		<div class="done-wrapper">

			<div class="done-title">
				科目情報登録
			</div>

			<div class="done-message">
				登録が完了しました
			</div>

			<div class="done-links">
				<a href="SubjectCreate.action">戻る</a>
				<a href="SubjectList.action">科目一覧</a>
			</div>

		</div>
	</c:param>
</c:import>