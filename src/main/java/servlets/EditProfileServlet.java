package servlets;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
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
 * Servlet implementation class EditProfileServlet
 */
@WebServlet("/edit")
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserFacade userFacade;

	public EditProfileServlet() {
		userFacade = new UserFacade();
	}
	public EditProfileServlet(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	public void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter(ParametersConstants.NAME);
		String email = request.getParameter(ParametersConstants.EMAIL);
		String password = request.getParameter(ParametersConstants.PASSWORD);
		String id = request.getParameter(ParametersConstants.ID);
		if (id != null) {
			try {
			UserDto user = userFacade.editProfile(Integer.valueOf(id), name, email, password);
			response.getWriter().write(new Gson().toJson(user));
			response.setStatus(HttpServletResponse.SC_OK);
			}catch(EntityNotFoundException ex) {
				response.getWriter().write(new Gson().toJson(ex.getMessage()));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
