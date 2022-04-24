<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Election Commission of India</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
	box-sizing: border-box;
}

/* Style the body */
body {
	font-family: Arial, Helvetica, sans-serif;
	margin: 0;
}

/* Header/logo Title */
.header {
	padding: 80px;
	text-align: center;
	background: #3232a8;
	color: white;
}

/* Increase the font size of the heading */
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

/* Column container */
.row {
	display: -ms-flexbox; /* IE10 */
	display: flex;
	-ms-flex-wrap: wrap; /* IE10 */
	flex-wrap: wrap;
}

/* Create two unequal columns that sits next to each other */
/* Sidebar/left column */
.side {
	-ms-flex: 30%; /* IE10 */
	flex: 30%;
	background-color: #f1f1f1;
	padding: 20px;
}

/* Main column */
.main {
	-ms-flex: 70%; /* IE10 */
	flex: 70%;
	background-color: white;
	padding: 20px;
}

/* Fake image, just for this example */
.fakeimg {
	background-color: #aaa;
	width: 100%;
	padding: 20px;
}

/* Footer */
.footer {
	padding: 20px;
	text-align: center;
	background: #ddd;
}

/* Responsive layout - when the screen is less than 700px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 700px) {
	.row {
		flex-direction: column;
	}
}

/* Responsive layout - when the screen is less than 400px wide, make the navigation links stack on top of each other instead of next to each other */
@media screen and (max-width: 400px) {
	.navbar a {
		float: none;
		width: 100%;
	}
}
</style>
</head>
<body>

	<div class="header">
		<h1>Election Commission of India</h1>
		<p>Online voting Portal</p>
	</div>

	<div class="navbar">
		<a href="/OnlineElectionSystem/VoterLoginPage">Voter</a> <a
			href="/OnlineElectionSystem/candidateApplyForm">Apply for
			Election</a> <a href="/OnlineElectionSystem/adminLoginPage">Admin</a> <a
			href="/OnlineElectionSystem/seeResult" class="right">See Results</a>
	</div>

	<div class="row">
		<div class="side">
			<h2>Chief Election Commissioner</h2>
			<h5>Surenderpal Singh Rai</h5>
			<div class="fakeimg" style="height: 200px;">Image</div>
			<p>From May 2021 to June 2022</p>
			<h3>More Text</h3>
			<p>Lorem ipsum dolor sit ame.</p>
			<div class="fakeimg" style="height: 60px;">Image</div>
			<br>
			<div class="fakeimg" style="height: 60px;">Image</div>
			<br>
			<div class="fakeimg" style="height: 60px;">Image</div>
		</div>
		<div class="main">
			<h2>About Application</h2>
			<h5>Rohtak, March 3, 2022</h5>
			<div class="fakeimg" style="height: 200px;">Image</div>
			<p>About this Web Application</p>
			<p>This voting system is a great solution for any kind of voting
				process. where admin can add multiple parties and voters can choose
				their candidate. Admin can analyze the results and declare a winning
				candidate.</p>
			<br>
			<p>Voting system is dedicated to select a candidate from the
				list, electing, or determining. The main goal of voting is to come
				up with leaders of the people’s choice. In most countries and cities
				voting is a manual process, Some of the problems involved include
				ridging votes during election, insecure or inaccessible polling
				stations, inadequate polling materials and also inexperienced
				personnel.</p>
			<br>
			<p>This online voting/polling system seeks to address the above
				issues. It should be noted that with this system in place, the
				users, citizens, in this case, shall be given ample time during the
				voting period. They shall also be trained on how to vote online
				before the election time.</p>
			<p>It’s a secure platform, to make it secure and fair it’s using
				a unique voter id so single users and voters uniquely also can vote
				only once for each election. It is a web application and users can
				access it by using a browser that is a secure and smooth process.</p>

		</div>
	</div>

	<div class="footer">
		<h2>Java Project Batch 1 Group 2</h2>
		<p>This project is completed by Java Project Batch 1 group 2</p>
	</div>

</body>
</html>