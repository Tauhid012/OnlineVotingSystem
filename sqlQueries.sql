-- for create and use databse
create database election;
use election;

-- this table is use to store data for voter list request
create table signUpRequest (request_id varchar(10), Name varchar(20), FatherName varchar(20), email varchar(40), address text);

-- login used to take id and password which used to store data and password of user
create table login(id varchar(10), psw varchar(10));
insert into login values("admin","admin");

-- used to store voter list
create table voter_list(voter_id varchar(10), name varchar(20), fathername varchar(20), email varchar(40),  address text);

-- store data of that application which is used for store reject application details
create table rejectedApplications (request_id varchar(10), Name varchar(20), FatherName varchar(20), email varchar(40), address text);

-- table for store candidate application
create table candidateApplication(voter_id varchar(10), name varchar(20), party_name varchar(20), symbol varchar(20));

-- table store candidate whose application is accepted
create table candidate(application_no varchar(10), voter_id varchar(10), name varchar(20), party_name varchar(20), symbol varchar(20));

-- store votes during voting
create table vote(application_no varchar(10), name varchar(20), party_name varchar(20), symbol varchar(20), votes int);

-- store voter id that already voted
create table alreadyVoted(voter_id varchar(10));

-- this table tells election is active or not it store 0 it election is inacitive during election on it store 1
create table electionActive(active char);
insert into electionActive values('0');

-- this sotored procedure contain execute during candidate appliction is accepted
delimiter //
CREATE PROCEDURE `acceptCandidateApplication`(in application_no varchar(10))
BEGIN
declare voter_id1 varchar(10);
declare name1 varchar(20);
declare party_name1 varchar(20);
declare symbol1 varchar(20);
select voter_id, name, party_name, symbol into voter_id1, name1, party_name1, symbol1 from candidateApplication where candidateApplication.application_no=application_no;
insert into candidate values(application_no,voter_id1, name1, party_name1, symbol1);
delete from candidateApplication where candidateApplication.application_no=application_no;
END //

-- stored procedure used to store data of voter
delimiter //
CREATE PROCEDURE `acceptRequest`(in voter_id varchar(10),in psw varchar(10),in request_id varchar(10))
BEGIN
declare name1 varchar(20) default null;
declare fathername1 varchar(20) default null;
declare email1 varchar(40) default null;
declare address1 text;
select name, FatherName, email, address into name1, fathername1, email1, address1
 from signUpRequest where signUpRequest.request_id=request_id;
insert into voter_list values(voter_id, name1, fathername1, email1, address1);
insert into login values (voter_id, psw);
delete from signUpRequest where signUpRequest.request_id=request_id;
END //

-- used to end election
delimiter //
CREATE PROCEDURE `endElection`()
BEGIN
update electionActive set active='0';
truncate table candidate;
truncate table alreadyVoted;
END //

-- store voting in table
delimiter //
CREATE PROCEDURE `increaseVote`(in application_no varchar(10))
BEGIN
declare temp int;
select votes into temp from vote where vote.application_no=application_no;
set temp = temp+1;
update vote set votes = temp where vote.application_no=application_no;
END //

-- stored rejected request in table
delimiter //
CREATE PROCEDURE `rejectRequest`(in request_id varchar(10))
BEGIN
declare request_id1 varchar(10);
declare name1 varchar(20);
declare fathername1 varchar(20);
declare email1 varchar(40);
declare address1 text;
select request_id, name, fathername, email, address into request_id1, name1, fathername1, email1, address1 from signUpRequest where signUpRequest.request_id=request_id;
insert into rejectedApplications values(request_id1, name1, fathername1, email1, address1);
delete from signUpRequest where signUpRequest.request_id=request_id;
END //

-- used to store request in table
delimiter //
CREATE PROCEDURE `storedRequest`(in request_id varchar(10), in Name varchar(20), 
in FatherName varchar(20), in email varchar(40), in address text)
BEGIN
insert into signUpRequest values (request_id, Name, FatherName, email, address);
END //