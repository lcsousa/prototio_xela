package br.com.samsung.wms.latam.cellowmsestore.service.security;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.samsung.wms.latam.cellowmsestore.dto.security.HasRolesRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.LoginRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.TokenLoginResponseDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.UserDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.RoleAuthEnum;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.mapper.security.UserMapper;
import br.com.samsung.wms.latam.cellowmsestore.util.security.AuthUtil;

@Component
public class AuthService {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthUtil authUtil;
	
	@Autowired
	private UserMapper userMapper;

	public TokenLoginResponseDTO login(LoginRequestDTO userLogin) throws UsernameNotFoundException {
		String username = userLogin.getLogin();
		String password = userLogin.getPassword();
		
		TokenLoginResponseDTO retorno = null;
		UserEntity usuario = userService.findByLoginAndPassword(username, password);
		if (!ObjectUtils.isEmpty(usuario)) {
			retorno = new TokenLoginResponseDTO();
			retorno.setAccessToken(createToken(username, null));
			retorno.setExpiresIn(authUtil.getExpiration());
			retorno.setTokenType(authUtil.getTokenTyper());
		}

		return retorno;
	}

	public TokenLoginResponseDTO refreshToken(String token) {
		TokenLoginResponseDTO retorno = new TokenLoginResponseDTO();
		DecodedJWT decode = JWT.decode(token);
		String login = decode.getSubject();
		Map<String, Claim> mapClaims = decode.getClaims();
		retorno.setAccessToken(createToken(login, mapClaims));
		retorno.setExpiresIn(authUtil.getExpiration());
		retorno.setTokenType(authUtil.getTokenTyper());
		return retorno;
	}
	public UserDTO getUserByToken(String token) {
		String login = getLoginByToken(token) ;
		return userMapper.convertEntityToDto(userService.findByLogin(login));
	}

	public boolean validateToken(String token) throws UsernameNotFoundException {
		boolean validate = true;
		try {

			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(authUtil.getSecret())).build();
			verifier.verify(token);
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+
			// JWT.require(Algorithm.HMAC512(authUtil.getSecret())).build().verify(token).getClaim("nome"));
			// JWT.decode(token).getClaim("nome")
		} catch (Exception e) {
			validate = false;
		}

		return validate;
	}

	public String getLoginByToken(String token) throws UsernameNotFoundException {
		String login = JWT.require(Algorithm.HMAC512(authUtil.getSecret())).build().verify(token).getSubject();

		return login;
	}

	

	public String createToken(String login, Map<String, Claim> mapClaims) {
		Builder builder = JWT.create().withSubject(login);
		if (!ObjectUtils.isEmpty(mapClaims)) {
			Set<String> listKey = mapClaims.keySet();
			listKey.stream().forEach(key -> {
				builder.withClaim(key, mapClaims.get(key).asString());
			});
		}
		return builder.withExpiresAt(new Date(System.currentTimeMillis() + authUtil.getExpiration()))
				.sign(Algorithm.HMAC512(authUtil.getSecret()));
	}
	
	public List<RoleAuthEnum> getRolesByToken(String token) {
		List<RoleAuthEnum> retorno = null;
		String login = getLoginByToken(token) ;
		if(!ObjectUtils.isEmpty(login)) {
			UserEntity user = userService.findByLogin(login);
			if(!ObjectUtils.isEmpty(user)) {
				retorno = user.getListRole();
			}
		}		
		return retorno;
	}
	
	public boolean hasRole(HasRolesRequestDTO hasRole) {
		boolean retorno = false;
		String login = getLoginByToken(hasRole.getToken()) ;
		RoleAuthEnum role = RoleAuthEnum.valueOf(hasRole.getRole());
		if(!ObjectUtils.isEmpty(login) && !ObjectUtils.isEmpty(role)) {
			UserEntity user = userService.findByLogin(login);
			List<RoleAuthEnum> lisRoles = null;
			if(!ObjectUtils.isEmpty(user)) {
				lisRoles = user.getListRole();
			}
			if(!ObjectUtils.isEmpty(lisRoles) && lisRoles.contains(role)) {
				retorno = true;
			}
		}		
		return retorno;
	}

	
}
