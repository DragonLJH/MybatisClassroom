package gupt.dragon.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gupt.dragon.common.Management;
import gupt.dragon.service.ModalInforService;

/**
 * Servlet implementation class ModalInforServlet
 */
@WebServlet("/modalInfor")
public class ModalInforServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModalInforServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 设置请求数据的解码字符集
		String date = request.getParameter("date");

		String mor = request.getParameter("mor");

		String classroom = request.getParameter("classroom");

		mor = mor.equals("1") ? "上午" : "下午";

		if ("1".equals(classroom)) {
			classroom = "第1课室";
		} else if ("2".equals(classroom)) {
			classroom = "第2课室";
		} else if ("3".equals(classroom)) {
			classroom = "第3课室";
		} else if ("4".equals(classroom)) {
			classroom = "第4课室";
		} else if ("5".equals(classroom)) {
			classroom = "第5课室";
		} else if ("6".equals(classroom)) {
			classroom = "第6课室";
		}
		Management management = new Management();

		management.setDate(date);
		management.setMor(mor);
		management.setClassroom(classroom);

		ModalInforService modalInforService = new ModalInforService();

		String result = modalInforService.selectAllByDateAndMor(management) != null
				? modalInforService.selectAllByDateAndMor(management).toString()
				: "";
		response.setCharacterEncoding("utf-8"); // 设置返回值的编码字符集
		response.getWriter().write(result); // 将值直接返回至客户端

	}

}
