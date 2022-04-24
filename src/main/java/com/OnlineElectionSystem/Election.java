package com.OnlineElectionSystem;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Election {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@GetMapping("/giveVoteControls")
	public String showAcceptedCandidateList(HttpServletRequest req) throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from electionActive");
		ResultSet res = stmt.executeQuery();
		if (res.next()) {
			if (res.getString("active").equals("0")) {
				req.setAttribute("message", "No election is available wait for elections");
				return "Voter/general";
			}
		}
		stmt = con.prepareStatement("select * from vote");
		res = stmt.executeQuery();

		while (res.next()) {
			Map<String, String> s = new HashMap<String, String>();
			s.put("application_no", res.getString("application_no"));
			s.put("name", res.getString("name"));
			s.put("party_name", res.getString("party_name"));
			s.put("symbol", res.getString("symbol"));
			list.add(s);
		}
		req.setAttribute("list", list);
		return "Voter/giveVoteControls";
	}

	@GetMapping("/createElection")
	public String createElection(HttpServletRequest req) throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from candidate");
		ResultSet res = stmt.executeQuery();

		while (res.next()) {
			Map<String, String> s = new HashMap<String, String>();
			s.put("application_no", res.getString("application_no"));
			s.put("voter_id", res.getString("voter_id"));
			s.put("name", res.getString("name"));
			s.put("party_name", res.getString("party_name"));
			s.put("symbol", res.getString("symbol"));
			list.add(s);
		}
		req.setAttribute("list", list);

		stmt = con.prepareStatement("update electionActive set active='1'");
		stmt.executeUpdate();

		stmt = con.prepareStatement("truncate table vote");
		stmt.executeUpdate();

		stmt = con.prepareStatement("truncate table alreadyVoted");
		stmt.executeUpdate();

		stmt = con.prepareStatement("select * from candidate");
		res = stmt.executeQuery();
		stmt = con.prepareStatement("insert into vote values(?,?,?,?,0)");
		while (res.next()) {
			stmt.setString(1, res.getString("application_no"));
			stmt.setString(2, res.getString("name"));
			stmt.setString(3, res.getString("party_name"));
			stmt.setString(4, res.getString("symbol"));
			stmt.executeUpdate();
		}
		return "Admin/electionCreated";
	}

	@PostMapping("/giveVote")
	public String giveVote(HttpServletRequest req) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from alreadyVoted where voter_id=?");
		stmt.setString(1, req.getParameter("voter_id"));
		ResultSet res = stmt.executeQuery();
		req.setAttribute("voter_id", req.getParameter("voter_id"));
		if (res.next()) {
			req.setAttribute("message", "Your already voted");
			return "Voter/general";
		}
		stmt = con.prepareStatement("insert into alreadyVoted values (?)");
		stmt.setString(1, req.getParameter("voter_id"));
		stmt.executeUpdate();

		CallableStatement cStmt = con.prepareCall("call increaseVote(?)");
		cStmt.setString(1, req.getParameter("application_no"));
		cStmt.executeUpdate();

		req.setAttribute("message", "Your vote is register successfully");
		return "Voter/general";
	}

	@GetMapping("/declareResult")
	public String declareResult1(HttpServletRequest req) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		CallableStatement stmt = con.prepareCall("call endElection()");
		stmt.executeUpdate();
		PreparedStatement stmt1 = con.prepareStatement("select * from vote");
		ResultSet res = stmt1.executeQuery();

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		while (res.next()) {
			Map<String, String> s = new HashMap<String, String>();
			s.put("application_no", res.getString("application_no"));
			s.put("name", res.getString("name"));
			s.put("party_name", res.getString("party_name"));
			s.put("symbol", res.getString("symbol"));
			s.put("votes", res.getString("votes"));
			list.add(s);
		}
		req.setAttribute("list", list);

		return "Voter/result";
	}
}
