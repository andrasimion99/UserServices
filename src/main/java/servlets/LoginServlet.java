package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import constants.ParametersConstants;
import dto.UserDto;
import facade.UserFacade;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserFacade userFacade;
       
    public LoginServlet() {
		userFacade = new UserFacade();
    }
    public LoginServlet(UserFacade userFacade) {
		this.userFacade = userFacade;
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter(ParametersConstants.EMAIL);
		String password = request.getParameter(ParametersConstants.PASSWORD);
		UserDto user = userFacade.login(email, password);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (user != null) {
			response.getWriter().write(new Gson().toJson(user));
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
