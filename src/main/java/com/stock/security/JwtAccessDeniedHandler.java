package com.stock.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler extends AccessDeniedHandlerImpl {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		// This is invoked when user tries to access a secured REST resource without the necessary privileges
		// We should just send a 403 Forbidden response because there is no 'error' page to redirect to
		// Here you can place any message you want
		response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
	}
}
