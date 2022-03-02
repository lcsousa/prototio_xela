package br.com.samsung.wms.latam.cellowmsestore.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.samsung.wms.latam.cellowmsestore.exception.SecurityBusinessException;

@Component
public class SecurityExceptionHandleEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		
    	 httpServletResponse.setContentType("application/json");
    	 httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	SecurityBusinessException ex = SecurityBusinessException.builder()
				.httpStatusCode(HttpStatus.UNAUTHORIZED)
				.code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
				.message("Acesso Negado.")
				.description("Necessário estar autenticado no sistema para acessar a Url solicitada." )
				.build();
	
    	
    	ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(httpServletResponse.getOutputStream(), ex.getOnlyBody());

    }
    
   
   
}


