<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績参照</c:param>

	<c:param name="content">
		<style>
			.test-list-wrap {
				width: 100%;
				padding: 0 10px;
				box-sizing: border-box;
			}

			.test-list-title {
				background-color: #f2f2f2;
				font-size: 20px;
				font-weight: bold;
				color: #222;
				padding: 10px 16px;
				margin-bottom: 14px;
			}

			.test-search-box {
				border: 1px solid #dddddd;
				border-radius: 4px;
				padding: 12px 14px 14px 14px;
				margin-bottom: 14px;
			}

			.test-search-row {
				display: flex;
				align-items: flex-end;
				gap: 18px;
				margin-bottom: 12px;
				flex-wrap: wrap;
			}

			.test-side-label {
				width: 85px;
				font-size: 13px;
				color: #444;
				padding-bottom: 8px;
			}

			.test-field {
				display: flex;
				flex-direction: column;
			}

			.test-field-label {
				font-size: 13px;
				color: #444;
				margin-bottom: 6px;
			}

			.test-select,
			.test-input {
				height: 34px;
				padding: 0 10px;
				font-size: 13px;
				border: 1px solid #ced4da;
				border-radius: 4px;
				background-color: #fff;
				color: #333;
				box-sizing: border-box;
			}

			.w-year {
				width: 95px;
			}

			.w-class {
				width: 85px;
			}

			.w-subject {
				width: 190px;
			}

			.w-studentno {
				width: 190px;
			}

			.test-search-btn {
				height: 34px;
				padding: 0 16px;
				border: none;
				border-radius: 4px;
				background-color: #6c757d;
				color: #fff;
				font-size: 13px;
				font-weight: bold;
				cursor: pointer;
			}

			.test-search-btn:hover {
				opacity: 0.9;
			}

			.test-divider {
				border-top: 1px solid #dddddd;
				margin: 8px 0 10px 0;
			}

			.test-guide {
				font-size: 13px;
				color: #58c7ea;
				margin-bottom: 10px;
			}

			.test-error {
				font-size: 13px;
				color: #f0a020;
				margin-bottom: 10px;
			}

			.test-info {
				font-size: 13px;
				color: #333;
				margin-bottom: 6px;
			}

			.test-result-table {
				width: 100%;
				border-collapse: collapse;
				font-size: 13px;
			}

			.test-result-table th,
			.test-result-table td {
				border-top: 1px solid #dddddd;
				padding: 7px 8px;
				text-align: left;
				color: #333;
			}

			.test-result-table th {
				font-weight: bold;
			}

			.center {
				text-align: center;
			}
		</style>

		<div class="test-list-wrap">
			<div class="test-list-title">成績参照</div>

			<div class="test-search-box">
				<!-- 科目情報検索 -->
				<form action="TestListSubject.action" method="get">
					<div class="test-search-row">
						<div class="test-side-label">科目情報</div>

						<div class="test-field">
							<label class="test-field-label" for="entYear">入学年度</label>
							<select name="ent_year" id="entYear" class="test-select w-year">
								<option value="">----</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == ent_year || year.toString() == ent_year}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="test-field">
							<label class="test-field-label" for="classNum">クラス</label>
							<select name="class_num" id="classNum" class="test-select w-class">
								<option value="">----</option>
								<c:forEach var="cnum" items="${class_num_set}">
									<option value="${cnum}" <c:if test="${cnum == class_num}">selected</c:if>>
										${cnum}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="test-field">
							<label class="test-field-label" for="subjectCd">科目</label>
							<select name="subject_cd" id="subjectCd" class="test-select w-subject">
								<option value="">----</option>
								<c:forEach var="subject" items="${subject_set}">
									<option value="${subject.cd}" <c:if test="${subject.cd == subject_cd}">selected</c:if>>
										${subject.name}
									</option>
								</c:forEach>
							</select>
						</div>

						<div style="padding-bottom: 0;">
							<button type="submit" class="test-search-btn">検索</button>
						</div>
					</div>
				</form>

				<div class="test-divider"></div>

				<!-- 学生情報検索 -->
				<form action="TestListStudentExecute.action" method="get">
					<div class="test-search-row">
						<div class="test-side-label">学生情報</div>

						<div class="test-field">
							<label class="test-field-label" for="studentNo">学生番号</label>
							<input
								type="text"
								name="student_no"
								id="studentNo"
								value="${student_no}"
								class="test-input w-studentno"
								placeholder="学生番号を入力してください"
								required
							>
						</div>

						<div style="padding-bottom: 0;">
							<button type="submit" class="test-search-btn">検索</button>
						</div>
					</div>
				</form>
			</div>

			<c:if test="${empty student and empty error and empty list}">
				<div class="test-guide">
					科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
				</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="test-error">${error}</div>
			</c:if>

			<c:if test="${not empty student}">
				<div class="test-info">
					氏名：${student.name}（${student.no}）
				</div>
			</c:if>

			<c:if test="${not empty list}">
				<table class="test-result-table">
					<thead>
						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th class="center">回数</th>
							<th class="center">点数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="test" items="${list}">
							<tr>
								<td>${test.subjectName}</td>
								<td>${test.subjectCd}</td>
								<td class="center">${test.num}</td>
								<td class="center">${test.point}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</c:param>
</c:import>