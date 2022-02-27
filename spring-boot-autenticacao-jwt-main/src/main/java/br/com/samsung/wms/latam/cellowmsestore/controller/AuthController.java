package br.com.samsung.wms.latam.cellowmsestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.samsung.wms.latam.cellowmsestore.dto.TokenDTO;
import br.com.samsung.wms.latam.cellowmsestore.model.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	/*
	 * @Autowired private PasswordEncoder encoder;
	 */

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody UserEntity usuario) {
	//	usuario.setPassword(encoder.encode(usuario.getPassword()));
		return ResponseEntity.ok(service.login(usuario.getLogin(), usuario.getPassword()));
	}
	
	@PostMapping("/refreshToken")
	@PreAuthorize("hasAnyRole('ROLE_AUTH_REFRESH_TOKEN')")	
	public ResponseEntity<TokenDTO> login(@RequestBody TokenDTO token) {
	//	usuario.setPassword(encoder.encode(usuario.getPassword()));
		return ResponseEntity.ok(service.refreshToken(token));
	}
	
	@PostMapping("/identityUser")
	@PreAuthorize("hasAnyRole('ROLE_AUTH_IDENTITY_USER')")	
	public ResponseEntity<UserEntity> getUserByToken(@RequestBody TokenDTO token) {
	//	usuario.setPassword(encoder.encode(usuario.getPassword()));
		return ResponseEntity.ok(service.getUserByToken(token));
	}
	
}
