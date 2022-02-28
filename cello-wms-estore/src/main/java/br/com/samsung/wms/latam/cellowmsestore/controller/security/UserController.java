package br.com.samsung.wms.latam.cellowmsestore.controller.security;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.samsung.wms.latam.cellowmsestore.dto.security.SaveUserRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.UserDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.exception.BusinessException.BusinessExceptionBody;
import br.com.samsung.wms.latam.cellowmsestore.service.security.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = { "User" })
@RequestMapping("/v1/user")
public class UserController {
	@Autowired
	private UserService userService;

	@ApiOperation(value = "Endpoint de listagem de Usuários")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso", response = UserDTO.class,responseContainer = "List"),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_USER_FINDALL')")
	public ResponseEntity<List<UserDTO>> findAll() {
		log.info("User findAll");
		return ResponseEntity.ok(userService.findAll());
	}

	@ApiOperation(value = "Endpoint de criação de Usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário cadastrado com sucesso", response = SaveUserRequestDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)	
	@PreAuthorize("hasAnyRole('ROLE_USER_CREATE')")
	public ResponseEntity<UserDTO> salvar(@Valid @RequestBody @ApiParam(value = "SaveUserRequestDTO", required = true, name = "SaveUserRequestDTO") SaveUserRequestDTO usuario) {
		log.info("User findAll");
		return ResponseEntity.ok(userService.save(usuario));
	}

}
