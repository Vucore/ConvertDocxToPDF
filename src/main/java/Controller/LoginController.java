package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.ClientHandlerBO;
import Utils.TaskWorker;
import java.io.IOException;

@WebServlet("/authenticate")
public class LoginController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static boolean taskWorkerRunning = false;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repoUser = "folderConverted";
                
        try {
        	boolean isLoginSuccess = ClientHandlerBO.getInstance().isValidUser(username, password);
        	if(isLoginSuccess) {
        		Integer userId = ClientHandlerBO.getInstance().getIDUser(username);
        		HttpSession session = request.getSession();
        		session.setAttribute("userId", userId);
        		session.setAttribute("username", username);
        		session.setAttribute("repoUser", repoUser);
        		
        		if (!taskWorkerRunning) { 
        			TaskWorker taskWorker = new TaskWorker();
        			System.out.println("Đã tạo TaskWorker");
        			taskWorker.start(); 
        			taskWorkerRunning = true;
        		}
        		response.sendRedirect(request.getContextPath() + "/view/dashboard.jsp");
        	}else {
        		response.sendRedirect(request.getContextPath() + "/view/login.jsp?error=Invalid+username+or+password");

        	}      
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
