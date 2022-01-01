package test.servlets;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
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
import servlets.EditProfileServlet;

@RunWith(MockitoJUnitRunner.class)
public class EditProfileTest {
    EditProfileServlet editProfileServlet;
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
        editProfileServlet = new EditProfileServlet(userFacade);
        userDto = new UserDto();
    }

    @Test
    public void givenEditProfile_whenUserIsNotFound_thenStatusIsBadRequest() throws ServletException, IOException {
        when(request.getParameter(ParametersConstants.EMAIL)).thenReturn(TestConstants.EMAIL);
        when(request.getParameter(ParametersConstants.PASSWORD)).thenReturn(TestConstants.PASSWORD);
        when(request.getParameter(ParametersConstants.NAME)).thenReturn(TestConstants.NAME);
        when(request.getParameter(ParametersConstants.ID)).thenReturn(TestConstants.ID);
        doThrow(new EntityNotFoundException()).when(userFacade).editProfile(TestConstants.ID_INT, TestConstants.NAME, TestConstants.EMAIL, TestConstants.PASSWORD);
        when(response.getWriter()).thenReturn(printWriter);

        editProfileServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void givenEditProfile_whenUserIdIsValid_thenStatusIsOK() throws ServletException, IOException {
        when(request.getParameter(ParametersConstants.EMAIL)).thenReturn(TestConstants.EMAIL);
        when(request.getParameter(ParametersConstants.PASSWORD)).thenReturn(TestConstants.PASSWORD);
        when(request.getParameter(ParametersConstants.NAME)).thenReturn(TestConstants.NAME);
        when(request.getParameter(ParametersConstants.ID)).thenReturn(TestConstants.ID);
        when(userFacade.editProfile(TestConstants.ID_INT, TestConstants.NAME, TestConstants.EMAIL, TestConstants.PASSWORD)).thenReturn(userDto);
        when(response.getWriter()).thenReturn(printWriter);

        editProfileServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
