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
import br.com.samsung.wms.latam.cellowmsestore.dto.security.TokenDTO;
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

	public TokenDTO login(String username, String password) throws UsernameNotFoundException {
		TokenDTO retorno = null;
		UserEntity usuario = userService.findByLoginAndPassword(username, password);
		if (!ObjectUtils.isEmpty(usuario)) {
			retorno = new TokenDTO();
			retorno.setToken(createToken(username, null));			
		}

		return retorno;
	}

	public TokenDTO refreshToken(TokenDTO token) {
		TokenDTO retorno = new TokenDTO();
		DecodedJWT decode = JWT.decode(token.getToken());
		String login = decode.getSubject();
		Map<String, Claim> mapClaims = decode.getClaims();
		retorno.setToken(createToken(login, mapClaims));
		return retorno;
	}
	public UserDTO getUserByToken(TokenDTO token) {
		String login = getLoginByToken(token.getToken()) ;
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
	
	public List<RoleAuthEnum> getRolesByToken(TokenDTO token) {
		List<RoleAuthEnum> retorno = null;
		String login = getLoginByToken(token.getToken()) ;
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
