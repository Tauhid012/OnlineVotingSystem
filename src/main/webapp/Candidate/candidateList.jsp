<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<title>Admin Controls</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
	margin: 0;
}

/* Style the header */
.header {
	padding: 80px;
	text-align: center;
	background: #1abc9c;
	color: white;
}

/* Increase the font size of the h1 element */
.header h1 {
	font-size: 40px;
}

/* Style the top navigation bar */
.navbar {
	overflow: hidden;
	background-color: #333;
}

/* Style the navigation bar links */
.navbar a {
	float: left;
	display: block;
	color: white;
	text-align: center;
	padding: 14px 20px;
	text-decoration: none;
}

/* Right-aligned link */
.navbar a.right {
	float: right;
}

/* Change color on hover */
.navbar a:hover {
	background-color: #ddd;
	color: black;
	table
	,
	th,
	td
	{
	border
	:
	1px
	solid
	black;
}
}
</style>
</head>
<body>

	<div class="header">
		<h1>Election Commission of India</h1>
		<p>Admin Dashboard</p>
	</div>

	<div class="navbar">
		<a href="/OnlineElectionSystem/voterApplication">Voter Application</a>
		<a href="/OnlineElectionSystem/voterList">Voter List</a> <a
			href="/OnlineElectionSystem/candidateApplication">Candidate
			Application</a> <a href="/OnlineElectionSystem/createElection">Create
			Election</a> <a href="/OnlineElectionSystem/declareResult">Declare
			Result</a> <a href="/OnlineElectionSystem/home" class="right">LogOut</a>
	</div>
	<table border="2">
		<tr>
			<th>Application No</th>
			<th>Voter Id</th>
			<th>Name</th>
			<th>Party Name</th>
			<th>Symbol</th>
			<th>Accept Request</th>
			<th>Reject Request</th>
		</tr>
		<c:forEach var="item" items="${list}">
			<tr>
				<td>${item.application_no}</td>
				<td>${item.voter_id}</td>
				<td>${item.name }</td>
				<td>${item.party_name}</td>
				<td>${item.symbol}</td>
				<td><form
						action="/OnlineElectionSystem/acceptCandidateApplication"
						method="post">
						<input type="hidden" name="application_no"
							value="${item.application_no}"> <input type="hidden"
							name="voter_id" value="${item.voter_id}">
						<button>Accept</button>
					</form></td>
				<td><form
						action="/OnlineElectionSystem/rejectCandidateApplication"
						method="post">
						<input type="hidden" value="${item.application_no}"
							name="application_no"> <input type="hidden"
							name="voter_id" value="${item.voter_id}">
						<button>Reject</button>
					</form></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>