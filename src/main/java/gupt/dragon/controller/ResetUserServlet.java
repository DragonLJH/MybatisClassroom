package gupt.dragon.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gupt.dragon.common.StringConstant;
import gupt.dragon.service.UserService;



/**
 * 重置用户密码
 */
@WebServlet(urlPatterns = { "/resetPassword" })
public final class ResetUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    public ResetUserServlet() {
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
		String password = request.getParameter("password").trim();					
		/*String userPwd = MD5Util.string2MD5(Pwd);*/
		String againPassword = request.getParameter("againPassword").trim();
		
		// 设置转发的页面
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("/index.html");
	
        if(password.equals(againPassword)){ //校验两次输入的密码是否相同
        	// 调用后台模块保存信息
        	UserService userService = UserService.INSTANCE;
        	
        	boolean result = userService.resetUserPassword(jobNumber, password);
        	if (result) {
                request.setAttribute(StringConstant.REQ_MSG, "修改失败");            
            } else {
                request.setAttribute(StringConstant.REQ_MSG, "修改成功"); 
            }
        }
     // 转发
        dispatcher.forward(request, response);
     }
}
