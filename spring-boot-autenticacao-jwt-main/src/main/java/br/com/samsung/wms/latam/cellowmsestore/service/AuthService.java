package br.com.samsung.wms.latam.cellowmsestore.service;

import java.util.Date;
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

import br.com.samsung.wms.latam.cellowmsestore.dto.TokenDTO;
import br.com.samsung.wms.latam.cellowmsestore.model.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.service.util.AuthUtil;

@Component
public class AuthService {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthUtil authUtil;

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
	public UserEntity getUserByToken(TokenDTO token) {
		String login = getLoginByToken(token.getToken()) ;
		return userService.findByLogin(login);
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

	
}
