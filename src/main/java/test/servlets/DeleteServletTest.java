package test.servlets;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import constants.ParametersConstants;
import constants.TestConstants;
import dto.UserDto;
import facade.UserFacade;
import servlets.DeleteServlet;

@RunWith(MockitoJUnitRunner.class)
public class DeleteServletTest {
    DeleteServlet deleteServlet;
    UserDto userDto;
    @Mock
    UserFacade userFacade;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    PrintWriter printWriter;

    @Before
    public void setup() {
        deleteServlet = new DeleteServlet(userFacade);
        userDto = new UserDto();
    }

    @Test
    public void givenDeleteUser_whenIdIsInvalid_thenStatusIsBadRequest() throws ServletException, IOException {
        when(request.getParameter(ParametersConstants.ID)).thenReturn(TestConstants.ID);
        doThrow(new EntityNotFoundException()).when(userFacade).deleteUser(TestConstants.ID_INT);
        when(response.getWriter()).thenReturn(printWriter);

        deleteServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void givenDeleteUser_whenIdIsValid_thenStatusIsNoContent() throws ServletException, IOException {
        when(request.getParameter(ParametersConstants.ID)).thenReturn(TestConstants.ID);

        deleteServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
