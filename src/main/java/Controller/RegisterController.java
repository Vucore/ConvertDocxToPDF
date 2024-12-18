package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.ClientHandlerBO;

@WebServlet("/register")
public class RegisterController extends HttpServlet{
private static final long serialVersionUID = 1L;
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
                
        try {
        	boolean isRegisterSuccess = ClientHandlerBO.getInstance().createAccount(username, email, password);
        	if (isRegisterSuccess) {
        	    response.sendRedirect(request.getContextPath() + "/view/register.jsp?error=true");
        	} else {
        		response.sendRedirect(request.getContextPath() + "/view/register.jsp?error=false");

        	}
     
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
