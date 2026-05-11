<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
 
<c:import url="/common/base.jsp">
	<c:param name="title">成績管理</c:param>
 
	<c:param name="content">
		<style>
			.test-regist-wrap {
				width: 100%;
				padding: 0 10px;
				box-sizing: border-box;
			}
 
			.test-regist-title {
				background-color: #f2f2f2;
				font-size: 20px;
				font-weight: bold;
				color: #222;
				padding: 10px 16px;
				margin-bottom: 18px;
			}
 
			.test-search-box {
				border: 1px solid #dddddd;
				border-radius: 4px;
				padding: 14px 14px 18px 14px;
				margin-bottom: 14px;
			}
 
			.test-search-row {
				display: flex;
				align-items: flex-end;
				gap: 24px;
				flex-wrap: wrap;
			}
 
			.test-search-item {
				display: flex;
				flex-direction: column;
			}
 
			.test-search-label {
				font-size: 13px;
				color: #444;
				margin-bottom: 8px;
			}
 
			.test-search-select {
				height: 38px;
				padding: 0 12px;
				font-size: 13px;
				border: 1px solid #ced4da;
				border-radius: 4px;
				background-color: #fff;
				color: #333;
			}
 
			.w-year { width: 145px; }
			.w-class { width: 145px; }
			.w-subject { width: 315px; }
			.w-count { width: 145px; }
 
			.test-search-btn-wrap {
				margin-left: 28px;
			}
 
			.test-search-btn {
				height: 40px;
				padding: 0 18px;
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
 
			.test-info {
				margin: 0 0 10px 0;
				font-size: 13px;
				color: #333;
			}
 
			.test-error {
				margin: 0 0 10px 0;
				font-size: 12px;
				color: #f0a020;
			}
 
			.test-result-table {
				width: 100%;
				border-collapse: collapse;
				font-size: 13px;
			}
 
			.test-result-table th,
			.test-result-table td {
				border-top: 1px solid #dddddd;
				padding: 8px 10px;
				text-align: left;
				vertical-align: top;
			}
 
			.test-result-table th {
				font-weight: bold;
				color: #333;
			}
 
			.point-input {
				width: 120px;
				height: 32px;
				padding: 4px 8px;
				font-size: 13px;
				border: 1px solid #999;
				box-sizing: border-box;
			}
 
			.point-error {
				margin-top: 4px;
				font-size: 12px;
				color: #f0a020;
			}
 
			.test-register-btn-wrap {
				margin-top: 14px;
			}
 
			.test-register-btn {
				height: 40px;
				padding: 0 18px;
				border: none;
				border-radius: 4px;
				background-color: #6c757d;
				color: #fff;
				font-size: 13px;
				font-weight: bold;
				cursor: pointer;
			}
 
			.test-register-btn:hover {
				opacity: 0.9;
			}
		</style>
 
		<div class="test-regist-wrap">
			<div class="test-regist-title">成績管理</div>
 
			<div class="test-search-box">
				<form action="TestRegist.action" method="get">
					<div class="test-search-row">
 
						<div class="test-search-item">
							<label class="test-search-label" for="entYear">入学年度</label>
							<select name="ent_year" id="entYear" class="test-search-select w-year" required>
								<option value="">---------</option>
								<c:forEach var="year" items="${entYearSet}">
									<option value="${year}" <c:if test="${year == ent_year || year.toString() == ent_year}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>
 
						<div class="test-search-item">
							<label class="test-search-label" for="classNum">クラス</label>
							<select name="class_num" id="classNum" class="test-search-select w-class" required>
								<option value="">---------</option>
								<c:forEach var="cnum" items="${classNumSet}">
									<option value="${cnum}" <c:if test="${cnum == class_num}">selected</c:if>>
										${cnum}
									</option>
								</c:forEach>
							</select>
						</div>
 
						<div class="test-search-item">
							<label class="test-search-label" for="subjectCd">科目</label>
							<select name="subject_cd" id="subjectCd" class="test-search-select w-subject" required>
								<option value="">---------</option>
								<c:forEach var="subject" items="${subjectSet}">
									<option value="${subject.cd}" <c:if test="${subject.cd == subject_cd}">selected</c:if>>
										${subject.name}
									</option>
								</c:forEach>
							</select>
						</div>
 
						<div class="test-search-item">
							<label class="test-search-label" for="count">回数</label>
							<select name="no" id="count" class="test-search-select w-count" required>
								<option value="">---------</option>
								<option value="1" <c:if test="${no == '1' || no == 1}">selected</c:if>>1</option>
								<option value="2" <c:if test="${no == '2' || no == 2}">selected</c:if>>2</option>
							</select>
						</div>
 
						<div class="test-search-btn-wrap">
							<button type="submit" class="test-search-btn">検索</button>
						</div>
 
					</div>
				</form>
			</div>
 
			<c:if test="${not empty subject and not empty no}">
				<div class="test-info">
					科目：${subject.name}（${no}回）
				</div>
			</c:if>
 
			<c:if test="${not empty error}">
				<div class="test-error">${error}</div>
			</c:if>
 
			<c:if test="${not empty studentList}">
				<form action="TestRegistExecute.action" method="post">
					<input type="hidden" name="ent_year" value="${ent_year}">
					<input type="hidden" name="class_num" value="${class_num}">
					<input type="hidden" name="subject_cd" value="${subject_cd}">
					<input type="hidden" name="no" value="${no}">
 
					<table class="test-result-table">
						<thead>
							<tr>
								<th style="width:120px;">入学年度</th>
								<th style="width:90px;">クラス</th>
								<th style="width:120px;">学生番号</th>
								<th style="width:160px;">氏名</th>
								<th style="width:180px;">点数</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="student" items="${studentList}">
								<tr>
									<td>${ent_year}</td>
									<td>${class_num}</td>
									<td>${student.no}</td>
									<td>${student.name}</td>
									<td>
										<input
											type="text"
											name="point_${student.no}"
											class="point-input"
											value="${pointMap[student.no]}"
										>
										<c:if test="${not empty pointErrorMap[student.no]}">
											<div class="point-error">${pointErrorMap[student.no]}</div>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
 
					<div class="test-register-btn-wrap">
						<button type="submit" class="test-register-btn">登録して終了</button>
					</div>
				</form>
			</c:if>
 
		</div>
	</c:param>
</c:import>