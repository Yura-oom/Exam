<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
 
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
 
	<c:param name="content">
		<style>
			.subject-wrapper {
				width: 100%;
				min-height: 400px;
				padding: 15px 10px 0 10px;
			}
 
			.subject-title {
				background-color: #eeeeee;
				font-size: 20px;
				font-weight: bold;
				padding: 8px 15px;
				margin-bottom: 12px;
			}
 
			.subject-add {
				text-align: right;
				margin-bottom: 5px;
				font-size: 13px;
			}
 
			.subject-add a {
				color: #007bff;
				text-decoration: underline;
			}
 
			.subject-table {
				width: 100%;
				border-collapse: collapse;
				font-size: 13px;
			}
 
			.subject-table th {
				text-align: left;
				border-bottom: 1px solid #dee2e6;
				padding: 8px 10px;
				font-weight: bold;
			}
 
			.subject-table td {
				border-bottom: 1px solid #dee2e6;
				padding: 8px 10px;
			}
 
			.subject-table .code {
				width: 120px;
			}
 
			.subject-table .name {
				width: auto;
			}
 
			.subject-table .link {
				width: 80px;
				text-align: center;
			}
 
			.subject-table a {
				color: #007bff;
				text-decoration: underline;
			}
		</style>
 
		<div class="subject-wrapper">
 
			<div class="subject-title">
				科目管理
			</div>
 
			<div class="subject-add">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
 
			<table class="subject-table">
				<tr>
					<th class="code">科目コード</th>
					<th class="name">科目名</th>
					<th class="link"></th>
					<th class="link"></th>
				</tr>
 
				<c:forEach var="subject" items="${subjects}">
					<tr>
						<td class="code">${subject.cd}</td>
						<td class="name">${subject.name}</td>
						<td class="link">
							<a href="SubjectUpdate.action?cd=${subject.cd}">変更</a>
						</td>
						<td class="link">
							<a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
 
		</div>
	</c:param>
</c:import>