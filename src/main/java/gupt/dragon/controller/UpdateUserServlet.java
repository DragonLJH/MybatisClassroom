package gupt.dragon.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gupt.dragon.service.UserService;

/**
 * 修改用户密码
 */
@WebServlet(urlPatterns = { "/updatePassword" })
public final class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    public UpdateUserServlet() {
        super();
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

    	request.setCharacterEncoding("utf-8");
    	
		 // 获取用户输入的数据
    	String jobNumber = request.getParameter("jobNumber").trim();						
		String password = request.getParameter("oldPassword").trim();
		/*String managePwd = MD5Util.string2MD5(Pwd);*/
		String newPassword = request.getParameter("password").trim();
		/*String newPwd = MD5Util.string2MD5(Pwd1);*/
		String newPwdAgain = request.getParameter("againPassword").trim();
		
		// 设置转发的页面
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("/update.html");
	
        if(newPassword.equals(newPwdAgain)){
        	// 调用后台模块保存信息
        	UserService userService = UserService.INSTANCE;
        	
        	boolean result = userService.updateUser(jobNumber, password, newPassword);
        	if(result){
        		dispatcher = request.getRequestDispatcher("/login.html"); 
        	}
        }
        
     // 转发
        dispatcher.forward(request, response);
     }
}
