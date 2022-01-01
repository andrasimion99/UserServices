package test.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

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
import servlets.LoginServlet;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {
    LoginServlet loginServlet;
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
        loginServlet = new LoginServlet(userFacade);
        userDto = new UserDto();
    }

    @Test
    public void givenLogin_whenWrongPassword_thenStatusIsBadRequest() throws ServletException, IOException {
        when(request.getParameter(ParametersConstants.EMAIL)).thenReturn(TestConstants.EMAIL);
        when(request.getParameter(ParametersConstants.PASSWORD)).thenReturn(TestConstants.PASSWORD);
        when(userFacade.login(TestConstants.EMAIL, TestConstants.PASSWORD)).thenReturn(null);

        loginServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void givenLogin_whenValidCredentials_thenStatusIsOk() throws ServletException, IOException {
        when(request.getParameter(ParametersConstants.EMAIL)).thenReturn(TestConstants.EMAIL);
        when(request.getParameter(ParametersConstants.PASSWORD)).thenReturn(TestConstants.PASSWORD);
        when(userFacade.login(TestConstants.EMAIL, TestConstants.PASSWORD)).thenReturn(userDto);
        when(response.getWriter()).thenReturn(printWriter);

        loginServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}	
