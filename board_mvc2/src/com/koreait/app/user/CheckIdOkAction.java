package com.koreait.app.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.user.dao.UserDAO;

public class CheckIdOkAction  implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserDAO udao = new UserDAO();
		String userid = req.getParameter("userid");
		PrintWriter out = resp.getWriter();
		if(udao.checkId(userid)) {
			out.write("O");
		} else {
			out.write("X");
		}
		out.close();
		return null;
	}
}
