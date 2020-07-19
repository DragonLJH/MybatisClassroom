package gupt.dragon.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gupt.dragon.common.StringConstant;
import gupt.dragon.service.UserService;

/**
 * Servlet implementation class UserPower
 */
@WebServlet("/UserPower")
public class UserPowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPowerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 设置请求数据的解码字符集
		
		HttpSession session = request.getSession();
		
		UserService userService = UserService.INSTANCE;
		String jobNumber = (String) session.getAttribute(StringConstant.SESSION_USER);
		
		String result = userService.retrievePowerByJobNumber(jobNumber);
		
		response.setCharacterEncoding("utf-8"); // 设置返回值的编码字符集
		response.getWriter().write(result); // 将值直接返回至客户端
		
		
	}

}
