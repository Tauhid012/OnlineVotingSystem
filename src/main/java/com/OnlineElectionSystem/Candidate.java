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
public class Candidate {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@GetMapping("/candidateApplyForm")
	public String candidateApplyForm() {
		return "Candidate/candidateApplyForm";
	}

	@PostMapping("/apply")
	public String apply(HttpServletRequest req) throws SQLException {
		String application_no = this.generateRequestNo();
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("insert into candidateApplication values (?, ?, ?, ?, ?)");
		stmt.setString(1, application_no);
		stmt.setString(2, req.getParameter("voter_id"));
		stmt.setString(3, req.getParameter("name"));
		stmt.setString(4, req.getParameter("party_name"));
		stmt.setString(5, req.getParameter("symbol"));
		stmt.executeUpdate();

		req.setAttribute("application_no", application_no);

		return "Candidate/successfullyApply";
	}

	@GetMapping("/candidateApplication")
	public String candidateApplication(HttpServletRequest req) throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from candidateApplication");
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
		return "Candidate/candidateList";
	}

	private void sendEmail(String voter_id, String subject, String message) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from voter_list where voter_id=?");
		stmt.setString(1, voter_id);
		ResultSet res = stmt.executeQuery();
		String email = "";
		if (res.next())
			email = res.getString("email");

		SendEmail.sendMail(email, subject, message);
	}

	@PostMapping("/acceptCandidateApplication")
	public String acceptCandidateApplication(HttpServletRequest req) throws SQLException {
		String voter_id = req.getParameter("voter_id");
		String application_no = req.getParameter("application_no");
		String message = "Your application no " + application_no + " for fight election is Accepted";
		this.sendEmail(voter_id, "Candidate Ship is accepted", message);

		Connection con = jdbcTemplate.getDataSource().getConnection();
		CallableStatement stmt = con.prepareCall("call acceptCandidateApplication(?)");
		stmt.setString(1, application_no);
		stmt.executeUpdate();

		return this.candidateApplication(req);
	}

	@PostMapping("/rejectCandidateApplication")
	public String rejectCandidateApplication(HttpServletRequest req) throws SQLException {
		String voter_id = req.getParameter("voter_id");
		String application_no = req.getParameter("application_no");
		String message = "Your application no " + application_no + " for fight election is rejected";
		this.sendEmail(voter_id, "Electiion form Rejected", message);

		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("delete from candidateApplication where application_no=?");
		stmt.setString(1, application_no);
		stmt.executeUpdate();

		return this.candidateApplication(req);
	}

	@SuppressWarnings("resource")
	private String generateRequestNo() throws SQLException {
		String num = Candidate.geneateNo();
		Connection con = jdbcTemplate.getDataSource().getConnection();
		String query = "select * from candidateApplication where application_no=?";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, num);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			num = Candidate.geneateNo();
			stmt.setString(1, num);
			rs = stmt.executeQuery();
		}
		return num;
	}

	private static String geneateNo() {
		Random random = new Random();
		String num = "";
		for (int i = 1; i <= 10; i++)
			num += random.nextInt(10);
		return num;
	}
}
