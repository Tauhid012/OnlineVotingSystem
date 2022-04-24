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
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Admin {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@GetMapping("/adminLoginPage")
	public String adminLoginPage() {
		return "Admin/adminLogin";
	}

	@PostMapping("/adminLogin")
	public String adminLogin(HttpServletRequest req) throws SQLException {
		String id = req.getParameter("id");
		String psw = req.getParameter("psw");

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from login where id=?");
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			String getPSW = rs.getString("psw");
			if (psw.equals(getPSW))
				return "Admin/adminControls";
		}
		return "Admin/adminLogin";
	}

	@GetMapping("/voterApplication")
	public String voterApplication(HttpServletRequest req) throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from signUpRequest");
		ResultSet res = stmt.executeQuery();

		while (res.next()) {
			Map<String, String> s = new HashMap<String, String>();
			s.put("request_id", res.getString("request_id"));
			s.put("name", res.getString("Name"));
			s.put("fathername", res.getString("FatherName"));
			s.put("email", res.getString("email"));
			s.put("address", res.getString("address"));
			list.add(s);
		}
		req.setAttribute("list", list);
		return "Admin/voterApplicationList";
	}

	@PostMapping("/acceptApplication")
	public String acceptApplication(HttpServletRequest req) throws SQLException {
		String voter_id = this.voterId();
		String psw = this.generatePsw();
		String request_id = req.getParameter("request_id");
		String message = "Your request id " + request_id + " accept. Your voter id is " + voter_id + " password " + psw
				+ ". Use this for login on voter portal";

		this.sendEmail(request_id, "Request Accepted", message);

		Connection con = jdbcTemplate.getDataSource().getConnection();
		CallableStatement stmt = con.prepareCall("call acceptRequest(?,?,?)");
		stmt.setString(1, voter_id);
		stmt.setString(2, psw);
		stmt.setString(3, request_id);
		stmt.executeUpdate();

		String direct = this.voterApplication(req);
		return direct;
	}

	private void sendEmail(String request_id, String subject, String message) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select email from signUpRequest where request_id=?");
		stmt.setString(1, request_id);
		ResultSet res = stmt.executeQuery();
		String email = "";
		if (res.next())
			email = res.getString("email");

		SendEmail.sendMail(email, subject, message);
	}

	@SuppressWarnings("resource")
	private String voterId() throws SQLException {
		// TODO Auto-generated method stub
		String id = this.generateVoterId();
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from login where id=?");
		stmt.setString(1, id);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			id = this.generateVoterId();
			stmt.setString(1, id);
			res = stmt.executeQuery();
		}
		return id;
	}

	private String generatePsw() {
		String psw = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++)
			psw += random.nextInt(10);
		return psw;
	}

	private String generateVoterId() {
		String voter_id = "OES";
		Random random = new Random();
		for (int i = 0; i < 4; i++)
			voter_id += random.nextInt(10);
		return voter_id;
	}

	@PostMapping("/rejectApplication")
	public String rejectRequest(HttpServletRequest req) throws SQLException {
		String request_id = req.getParameter("request_id");

		String message = "Your request id " + request_id + " for voter registration is reject for invalid region";
		this.sendEmail(request_id, "Request Rejected", message);

		Connection con = jdbcTemplate.getDataSource().getConnection();
		CallableStatement stmt = con.prepareCall("call rejectRequest(?)");
		stmt.setString(1, request_id);
		stmt.executeUpdate();

		return this.voterApplication(req);
	}

	@GetMapping("/voterList")
	public String voterList(HttpServletRequest req) throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from voter_list");
		ResultSet res = stmt.executeQuery();

		while (res.next()) {
			Map<String, String> s = new HashMap<String, String>();
			s.put("voter_id", res.getString("voter_id"));
			s.put("name", res.getString("Name"));
			s.put("fathername", res.getString("FatherName"));
			s.put("email", res.getString("email"));
			s.put("address", res.getString("address"));
			list.add(s);
		}
		req.setAttribute("list", list);
		return "Admin/voterList";
	}

	@PostMapping("/removeFromVoterList")
	public String removeFromVoterList(HttpServletRequest req) throws SQLException {
		String voter_id = req.getParameter("voter_id");

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("delete from voter_list where voter_id=?");
		stmt.setString(1, voter_id);
		stmt.executeUpdate();

		stmt = con.prepareStatement("delete from login where id=?");
		stmt.setString(1, voter_id);
		stmt.executeUpdate();

		return this.voterList(req);
	}
}
