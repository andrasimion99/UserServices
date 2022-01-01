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
import facade.UserFacade;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet("/user")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserFacade userFacade;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
    	userFacade = new UserFacade();
    }

    public UserInfoServlet(UserFacade userFacade) {
    	this.userFacade = userFacade;
    }


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(ParametersConstants.ID);
		if (id != null) {
			try {
				response.getWriter().write(new Gson().toJson(userFacade.getById(Integer.valueOf(id))));
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (EntityNotFoundException ex) {
				response.getWriter().write(new Gson().toJson(ex.getMessage()));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

		} else {
			response.getWriter().write(new Gson().toJson(userFacade.getAllUsers()));
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}



}
