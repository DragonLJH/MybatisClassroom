package gupt.dragon.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gupt.dragon.common.Management;
import gupt.dragon.service.ManagementService;
import gupt.dragon.service.ModalInforService;

/**
 * Servlet implementation class ManagementServlet
 */
@WebServlet("/management")
public class ManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagementServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 设置请求数据的解码字符集
		ManagementService managementService = new ManagementService();
		String date = request.getParameter("date");
		date = date + "%";

		List<Management> listManagement = managementService.selectAllByDate(date);

		String result = stringName(listManagement);
		System.out.println(result);
		response.setCharacterEncoding("utf-8"); // 设置返回值的编码字符集
		response.getWriter().write(result); // 将值直接返回至客户端

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tf = request.getParameter("batch");
		tf = tf != null ? tf : "";

		if (tf.isEmpty()) {
			Management management = new Management();
			String date = request.getParameter("date");
			String day = request.getParameter("day");
			day = day.replace("日", "");
			day = day.length() > 1 ? day : "0" + day;
			date = date + "-" + day;

			String mor = request.getParameter("mor");
			String classroom = request.getParameter("classroom");
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

			String reservationPerson = request.getParameter("reservationPerson").trim();
			String projectName = request.getParameter("projectName").trim();
			String projectManager = request.getParameter("projectManager").trim();
			String remarks = request.getParameter("remarks").trim();

			management.setDate(date);
			management.setClassroom(classroom);
			management.setMor(mor);
			management.setProjectName(projectName);
			management.setProjectManager(projectManager);
			management.setReserve(reservationPerson);
			management.setRemark(remarks);

			ManagementService managementService = new ManagementService();
			ModalInforService modalInforService = new ModalInforService();

			if (modalInforService.selectAllByDateAndMor(management) == null) {
				managementService.creationManagement(management);
			} else {
				managementService.updateManagement(management);
			}

			response.sendRedirect("/testTry/index.html?date=" + date);
			return;

		} else {

			String batch = request.getParameter("batch");
			String[] batchList = batch.split("@");
			System.out.println(batch);
			for (int i = 0; i < batchList.length; i++) {
				String[] list = batchList[i].split(",");

				if (list.length != 0) {
					Management management = new Management();

					// 由于日期格式为：2019/10/10，将日期格式转为：2019-10-10
					management.setDate(list[0].replaceAll("/", "-"));
					management.setMor(list[1]);
					management.setReserve(list[2]);
					management.setProjectName(list[3]);
					management.setProjectManager(list[4]);
					management.setClassroom(list[5]);
					management.setRemark(list.length == 6 ? " " : list[6]);

					ManagementService managementService = new ManagementService();
					ModalInforService modalInforService = new ModalInforService();

					if (modalInforService.selectAllByDateAndMor(management) == null) {
						managementService.creationManagement(management);
					} else {
						managementService.updateManagement(management);
					}
				}

			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
		dispatcher.forward(request, response);

	}

	// 生成时间=上下午=项目名称的符串，以逗号分隔
	private String stringName(List<Management> listManagement) {
		List<String> list = new ArrayList<String>();
		for (int x = 0; x < listManagement.size(); x++) {
			Management management = (Management) listManagement.get(x);
			String sum = "";
			sum = management.getDate() + "=" + management.getMor() + "=" + management.getProjectName() + "="
					+ management.getClassroom();
			list.add(sum);
		}
		return list.toString();
	}

}
