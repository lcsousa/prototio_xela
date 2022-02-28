package br.com.samsung.wms.latam.cellowmsestore.controller.security;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.samsung.wms.latam.cellowmsestore.dto.security.HasRolesRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.HasRolesResponseDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.LoginRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.TokenDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.UserDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.RoleAuthEnum;
import br.com.samsung.wms.latam.cellowmsestore.exception.BusinessException.BusinessExceptionBody;
import br.com.samsung.wms.latam.cellowmsestore.service.security.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = { "Security" })
@RequestMapping("/v1/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	/*
	 * @Autowired private PasswordEncoder encoder;
	 */
	@ApiOperation(value = "Endpoint de autenticação de usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso", response = TokenDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@PostMapping(value="/login",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequestDTO usuario) {
		log.info("Login user");
		return ResponseEntity.ok(service.login(usuario.getLogin(), usuario.getPassword()));
	}
	
	@ApiOperation(value = "Endpoint de refreshToken - Obtém um novo token válido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso", response = TokenDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@PostMapping(value="/refreshToken",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_AUTH_REFRESH_TOKEN')")	
	public ResponseEntity<TokenDTO> login(@Valid @RequestBody TokenDTO token) {
		log.info("refreshToken");
		return ResponseEntity.ok(service.refreshToken(token));
	}
	
	

	@ApiOperation(value = "Endpoint que obtém os dados do usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso", response = UserDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@PostMapping(value="/identityUser",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_AUTH_IDENTITY_USER')")	
	public ResponseEntity<UserDTO> getUserByToken(@Valid @RequestBody TokenDTO token) {
		log.info("getUserByToken");
		return ResponseEntity.ok(service.getUserByToken(token));
	}
	
	
	@ApiOperation(value = "Endpoint que obtém as roles do usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso", response = RoleAuthEnum.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@PostMapping(value="/getRoles",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_AUTH_GET_ROLES')")	
	public ResponseEntity<List<RoleAuthEnum>> getRoles(@Valid @RequestBody TokenDTO token) {
		log.info("getRoles");
		return ResponseEntity.ok(service.getRolesByToken(token));
	}
	
	@ApiOperation(value = "Endpoint que verifica se o usuário possui uma determinada Role")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso", response = HasRolesResponseDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@PostMapping(value="/hasRole",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_AUTH_HAS_ROLE')")	
	public ResponseEntity<HasRolesResponseDTO> hasRole(@Valid @RequestBody HasRolesRequestDTO token) {
		log.info("hasRole");
		return ResponseEntity.ok(new HasRolesResponseDTO(service.hasRole(token)));
	}
	
}