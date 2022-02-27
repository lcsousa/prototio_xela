package br.com.samsung.wms.latam.cellowmsestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.samsung.wms.latam.cellowmsestore.model.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.service.UserService;

@RestController
@RequestMapping("/api/usuario")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/findAll")
	@PreAuthorize("hasAnyRole('ROLE_USER_FINDALL')")
	public ResponseEntity<List<UserEntity>> findAll() {

		return ResponseEntity.ok(userService.findAll());
	}

	@PostMapping("/salvar")
	
	@PreAuthorize("hasAnyRole('ROLE_USER_CREATE')")
	public ResponseEntity<UserEntity> salvar(@RequestBody UserEntity usuario) {
		// usuario.setPassword(encoder.encode(usuario.getPassword()));
		return ResponseEntity.ok(userService.save(usuario));
	}

}
