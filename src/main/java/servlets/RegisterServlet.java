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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserFacade userFacade;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
		userFacade = new UserFacade();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter(ParametersConstants.NAME);
		String email = request.getParameter(ParametersConstants.EMAIL);
		String password = request.getParameter(ParametersConstants.PASSWORD);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		UserDto userDto = userFacade.createUser(email, password, name);
		if (userDto != null) {
			response.getWriter().write(new Gson().toJson(userDto));
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
