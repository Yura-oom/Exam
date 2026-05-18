<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
 
<c:import url="/common/base.jsp">
	<c:param name="title">成績管理</c:param>
 
	<c:param name="content">
		<style>
			.test-regist-done-wrap {
				width: 100%;
				padding: 0 10px;
				box-sizing: border-box;
			}
 
			.test-regist-done-title {
				background-color: #f2f2f2;
				font-size: 20px;
				font-weight: bold;
				color: #222;
				padding: 10px 16px;
				margin-bottom: 12px;
			}
 
			.test-regist-done-message {
				background-color: #97c9b3;
				color: #333;
				text-align: center;
				padding: 8px 0;
				margin-bottom: 90px;
				font-size: 13px;
			}
 
			.test-regist-done-link {
				font-size: 13px;
				margin-left: 12px;
			}
 
			.test-regist-done-link a {
				color: #4a73ff;
				text-decoration: underline;
				padding: 0 30px 0 0;
			}
		</style>
 
		<div class="test-regist-done-wrap">
			<div class="test-regist-done-title">成績管理</div>
 
			<div class="test-regist-done-message">
				登録が完了しました
			</div>
 
			<div class="test-regist-done-link">
				<a href="TestRegist.action">戻る</a>
				<a href="TestList.action">成績参照</a>
			</div>
		</div>
	</c:param>
</c:import>