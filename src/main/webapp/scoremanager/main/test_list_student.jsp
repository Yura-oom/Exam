<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績一覧（学生）</c:param>

	<c:param name="content">
		<style>
			.test-list-wrap {
				padding: 0 10px;
			}

			.test-list-title {
				background-color: #f3f3f3;
				padding: 10px 16px;
				font-size: 20px;
				font-weight: bold;
				color: #222;
				margin-bottom: 12px;
			}

			.search-box {
				border: 1px solid #dcdcdc;
				border-radius: 4px;
				padding: 18px 20px 10px 20px;
				margin: 0 12px 14px 12px;
			}

			.search-row {
				display: flex;
				align-items: center;
				margin-bottom: 14px;
				gap: 16px;
			}

			.search-left-label {
				width: 90px;
				font-size: 13px;
				color: #444;
			}

			.search-divider {
				border-top: 1px solid #e5e5e5;
				margin: 10px 0 14px 0;
			}

			.field-group {
				display: flex;
				flex-direction: column;
				gap: 6px;
			}

			.field-label {
				font-size: 13px;
				color: #444;
			}

			.search-select,
			.search-input {
				height: 34px;
				padding: 6px 10px;
				font-size: 13px;
				border: 1px solid #ced4da;
				border-radius: 4px;
				box-sizing: border-box;
			}

			.search-select.year {
				width: 120px;
			}

			.search-select.classnum {
				width: 120px;
			}

			.search-select.subject {
				width: 180px;
			}

			.search-input.studentno {
				width: 220px;
			}

			.search-btn {
				background-color: #6c757d;
				color: white;
				border: none;
				border-radius: 4px;
				padding: 8px 16px;
				font-size: 13px;
				cursor: pointer;
			}

			.student-name {
				margin: 0 12px 6px 12px;
				font-size: 13px;
				color: #333;
			}

			.common-error {
				margin: 0 12px 10px 12px;
				font-size: 13px;
				color: #49c5e6;
			}

			.score-table-wrap {
				margin: 0 12px;
			}

			.score-table {
				width: 100%;
				border-collapse: collapse;
				font-size: 13px;
			}

			.score-table th,
			.score-table td {
				border-top: 1px solid #e5e5e5;
				padding: 6px 8px;
				text-align: left;
				color: #333;
			}

			.score-table th:nth-child(2),
			.score-table td:nth-child(2),
			.score-table th:nth-child(3),
			.score-table td:nth-child(3),
			.score-table th:nth-child(4),
			.score-table td:nth-child(4) {
				text-align: center;
				width: 90px;
			}
		</style>

		<div class="test-list-wrap">
			<div class="test-list-title">成績一覧（学生）</div>

			<div class="search-box">

				<form action="TestListSubject.action" method="get">
					<div class="search-row">
						<div class="search-left-label">科目情報</div>

						<div class="field-group">
							<label class="field-label">入学年度</label>
							<select name="entYear" class="search-select year">
								<option value="">----------</option>
								<c:forEach var="year" items="${entYearSet}">
									<option value="${year}" <c:if test="${year == entYear}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="field-group">
							<label class="field-label">クラス</label>
							<select name="classNum" class="search-select classnum">
								<option value="">----------</option>
								<c:forEach var="cnum" items="${classNumSet}">
									<option value="${cnum}" <c:if test="${cnum == classNum}">selected</c:if>>
										${cnum}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="field-group">
							<label class="field-label">科目</label>
							<select name="subjectCd" class="search-select subject">
								<option value="">----------</option>
								<c:forEach var="sub" items="${subjectSet}">
									<option value="${sub.cd}" <c:if test="${sub.cd == subjectCd}">selected</c:if>>
										${sub.name}
									</option>
								</c:forEach>
							</select>
						</div>

						<div style="padding-top: 22px;">
							<button type="submit" class="search-btn">検索</button>
						</div>
					</div>
				</form>

				<div class="search-divider"></div>

				<form action="TestListStudent.action" method="get">
					<div class="search-row">
						<div class="search-left-label">学生情報</div>

						<div class="field-group">
							<label class="field-label">学生番号</label>
							<input
								type="text"
								name="studentNo"
								value="${studentNo}"
								class="search-input studentno"
								placeholder="学生番号を入力してください"
								required
							>
						</div>

						<div style="padding-top: 22px;">
							<button type="submit" class="search-btn">検索</button>
						</div>
					</div>
				</form>
			</div>

			<c:if test="${not empty student}">
				<div class="student-name">
					氏名：${student.name} (${student.no})
				</div>
			</c:if>

			<c:if test="${not empty commonError}">
				<div class="common-error">${commonError}</div>
			</c:if>

			<c:if test="${not empty testList}">
				<div class="score-table-wrap">
					<table class="score-table">
						<thead>
							<tr>
								<th>科目名</th>
								<th>科目コード</th>
								<th>回数</th>
								<th>点数</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${testList}">
								<tr>
									<td>${test.subjectName}</td>
									<td>${test.subjectCd}</td>
									<td>${test.no}</td>
									<td>${test.point}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>

		</div>
	</c:param>
</c:import>