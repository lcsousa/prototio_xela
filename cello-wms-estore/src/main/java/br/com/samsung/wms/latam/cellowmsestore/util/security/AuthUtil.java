package br.com.samsung.wms.latam.cellowmsestore.util.security;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class AuthUtil {
	//@Value ( "${jwt.secret}" )
	private String secret = "463568a1-54c9-4307-bb1c-6cced559f5a7";
	//@Value ( "${jwt.expiration}" )
	private int expiration = 600000;
	
}
