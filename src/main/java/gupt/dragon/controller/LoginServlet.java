/*
COPYRIGHT (C) 2017 BY MULDINI. ALL RIGHTS RESERVED.
*/

package gupt.dragon.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gupt.dragon.common.StringConstant;
import gupt.dragon.service.UserService;

/**
 * 登录
 */
@WebServlet(name = "loginServlet", urlPatterns = { "/login" })
public final class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8"); // 设置请求数据的解码字符集

		// 获取用户输入的数据
		String jobNumber = request.getParameter("id").trim();
		String userPwd = request.getParameter("pwd").trim();

		request.setAttribute("jobNumber", jobNumber);
		request.setAttribute("userPwd", userPwd);

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求数据的解码字符集
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		String jobNumber = (String) request.getAttribute("jobNumber");
		String userPwd = (String) request.getAttribute("userPwd");

		// 设置转发的页面
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");

		UserService userService = UserService.INSTANCE;

		// 将用户输入的jobNumber, userPwd和数据库中的jobNumber, userPwd比较,成功返回true，失败返回false
		boolean result = userService.loginUser(jobNumber, userPwd);

		if (result) {
			session.setAttribute(StringConstant.SESSION_USER, jobNumber);
			dispatcher = request.getRequestDispatcher("/index.html");
		}
		dispatcher.forward(request, response);

	}
}
