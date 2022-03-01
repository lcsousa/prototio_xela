package br.com.samsung.wms.latam.cellowmsestore.util.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class AuthUtil {
	@Value ( "${jwt.secret}" )
	private String secret;
	
	@Value("${jwt.expiration.access-token}" )
	private int expirationAccessToken;
	
	@Value("${jwt.expiration.refresh-token}" )
	private int expirationRefreshToken;
	
	@Value("${jwt.token-type}" )
	private String tokenTyper;
	
}
