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

import br.com.samsung.wms.latam.cellowmsestore.dto.security.ChangePasswordRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.HasRolesRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.LoginRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.RefreshTokenResponseDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.ResetPasswordRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.TokenLoginResponseDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.UserDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.RoleAuthEnum;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.TokenTypeEnum;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.exception.MessageBusiness;
import br.com.samsung.wms.latam.cellowmsestore.mapper.security.UserMapper;
import br.com.samsung.wms.latam.cellowmsestore.util.Constants;
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
			retorno.setAccessToken(createAccessToken(username, null));
			retorno.setExpiresIn(authUtil.getExpirationAccessToken());
			retorno.setTokenType(authUtil.getTokenTyper());
			retorno.setRefreshToken(createRefreshToken(username, null));
		}else {
			throw MessageBusiness.LOGIN_OR_PASSWORD_INVALID.createException();
		}

		return retorno;
	}
	
	public void changePassword(ChangePasswordRequestDTO userLogin) throws UsernameNotFoundException {
		String username = userLogin.getLogin();
		String password = userLogin.getPassword();
		
	
		UserEntity usuario = userService.findByLoginAndPassword(username, password);
		if (!ObjectUtils.isEmpty(usuario)) {
			usuario.setPassword(userLogin.getNewPassword());
			userService.save(usuario);
		}else {
			throw MessageBusiness.LOGIN_OR_PASSWORD_INVALID.createException();
		}
		
	}
	
	public void resetPassword(ResetPasswordRequestDTO userLogin) throws UsernameNotFoundException {
		String username = userLogin.getLogin();
	
		UserEntity usuario = userService.findByLogin(username);
		if (!ObjectUtils.isEmpty(usuario)) {
			usuario.setPassword(userLogin.getNewPassword());
			userService.save(usuario);
		}else {
			throw MessageBusiness.LOGIN_OR_PASSWORD_INVALID.createException();
		}
		
	}
	

	public RefreshTokenResponseDTO refreshToken(String token) {
		if(!isRefreshToken(token) || !validateToken(token)) {
			throw MessageBusiness.REFRESH_TOKEN_INVALID.createException();
		}
		
		RefreshTokenResponseDTO retorno = new RefreshTokenResponseDTO();
		
		String login = getLoginByToken(token);
		Map<String, Claim> mapClaims = getClaimsByToken(token);
		retorno.setAccessToken(createAccessToken(login, mapClaims));
		retorno.setExpiresIn(authUtil.getExpirationAccessToken());
		retorno.setTokenType(authUtil.getTokenTyper());
		
		return retorno;
	}
	
	public TokenTypeEnum getTypeTokenByToken(String token) {		
		DecodedJWT decode = JWT.decode(token);
		String typeToken = decode.getClaim(Constants.CLAIM_TOKEN_TYPE.toString()).asString();		
		return TokenTypeEnum.valueOfLabel(typeToken);
	}
	
	public boolean isAccessToken(String token) {
		return TokenTypeEnum.ACCESS_TOKEN.equals(getTypeTokenByToken(token));
	}
	
	public boolean isRefreshToken(String token) {
		return TokenTypeEnum.REFRESH_TOKEN.equals(getTypeTokenByToken(token));
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
			
		} catch (Exception e) {
			validate = false;
		}

		return validate;
	}

	public String getLoginByToken(String token) throws UsernameNotFoundException {
	
		return  JWT.decode(token).getSubject();
	}
	
	public Map<String, Claim> getClaimsByToken(String token) throws UsernameNotFoundException {
		
		return  JWT.decode(token).getClaims();
	}

	
	public String createAccessToken(String login, Map<String, Claim> mapClaims) {
		return createToken(login, mapClaims,TokenTypeEnum.ACCESS_TOKEN);
	}
	
	public String createRefreshToken(String login, Map<String, Claim> mapClaims) {
		return createToken(login, mapClaims,TokenTypeEnum.REFRESH_TOKEN);
	}
	public String createToken(String login, Map<String, Claim> mapClaims, TokenTypeEnum tokenTypeEnum) {
		Builder builder = JWT.create().withSubject(login);
		if (!ObjectUtils.isEmpty(mapClaims)) {
			Set<String> listKey = mapClaims.keySet();
			listKey.stream().forEach(key -> {
				if (!key.equalsIgnoreCase(Constants.CLAIM_TOKEN_TYPE)) {
					builder.withClaim(key, mapClaims.get(key).asString());
				}

			});
		}
		builder.withClaim(Constants.CLAIM_TOKEN_TYPE, tokenTypeEnum.toString());
		int expiration = authUtil.getExpirationAccessToken();
		if(TokenTypeEnum.REFRESH_TOKEN.equals(tokenTypeEnum)) {
			expiration =  authUtil.getExpirationRefreshToken();
		}
		return builder.withExpiresAt(new Date(System.currentTimeMillis() + expiration)).sign(Algorithm.HMAC512(authUtil.getSecret()));
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
