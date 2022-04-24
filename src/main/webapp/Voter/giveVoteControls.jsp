<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<title>Voter DashBoard</title>
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
}
</style>
</head>
<body>

	<div class="header">
		<h1>Election Commission of India</h1>
		<p>Voter Dashboard</p>
	</div>

	<div class="navbar">
		<a href="/OnlineElectionSystem/giveVoteControls">Give Vote</a> <a
			href="/OnlineElectionSystem/seeResult">See Result</a> <a
			href="/OnlineElectionSystem/home" class="right">LogOut</a>
	</div>
	<table border="2">
		<tr>
			<th>Name</th>
			<th>Party Name</th>
			<th>Symbol</th>
			<th>Press here for Vote</th>
		</tr>
		<c:forEach var="item" items="${list}">
			<tr>
				<td>${item.name }</td>
				<td>${item.party_name}</td>
				<td>${item.symbol}</td>
				<td><form action="/OnlineElectionSystem/giveVote" method="post">
						<input type="hidden" name="application_no"
							value="${item.application_no}"> <input type="text"
							name="voter_id" placeholder="Enter voter id">
						<button>Vote</button>
					</form>
			</tr>
		</c:forEach>
	</table>

</body>
</html>