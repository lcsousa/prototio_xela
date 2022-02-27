package br.com.samsung.wms.latam.cellowmsestore.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.samsung.wms.latam.cellowmsestore.model.RoleAuthEnum;
import br.com.samsung.wms.latam.cellowmsestore.service.AuthService;
import br.com.samsung.wms.latam.cellowmsestore.service.UserService;

public class AuthValidateCustomFilter extends OncePerRequestFilter {
	@Autowired
	private  AuthService authServiceImpl;
	@Autowired
	private  UserService userService;
	

	
	public static final String HEADER_ATRIBUTO = "Authorization";
	public static final String ATRIBUTO_PREFIXO = "Bearer ";

	

	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String atributo = request.getHeader(HEADER_ATRIBUTO);

		if (atributo == null) {
			chain.doFilter(request, response);
			return;
		}

		if (!atributo.startsWith(ATRIBUTO_PREFIXO)) {
			chain.doFilter(request, response);
			return;
		}

		String token = atributo.replace(ATRIBUTO_PREFIXO, "");
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		String login = null;
		if(authServiceImpl.validateToken(token)) {
			login = authServiceImpl.getLoginByToken(token);			
		}

		if (login == null) {
			return null;
		}
				
		return new UsernamePasswordAuthenticationToken(login, null, getRoles(userService.findByLogin(login).getListRole()));
	}
	
	private List<GrantedAuthority> getRoles(List<RoleAuthEnum> listaRole){
		List<GrantedAuthority> retorno = new ArrayList<>();
		if(!ObjectUtils.isEmpty(listaRole)) {
			listaRole.stream().forEach(role ->{
				retorno.add(new SimpleGrantedAuthority(role.toString()));
			});
		}
		
		return retorno;
	}
}
