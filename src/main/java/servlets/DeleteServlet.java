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
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserFacade userFacade;

	public DeleteServlet() {
		userFacade = new UserFacade();
	}
	public DeleteServlet(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter(ParametersConstants.ID);
		if (id != null) {
			try {
				userFacade.deleteUser(Integer.valueOf(id));
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} catch (EntityNotFoundException ex) {
				response.getWriter().write(new Gson().toJson(ex.getMessage()));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
