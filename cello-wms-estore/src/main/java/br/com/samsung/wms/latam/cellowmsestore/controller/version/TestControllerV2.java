package br.com.samsung.wms.latam.cellowmsestore.controller.version;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.samsung.wms.latam.cellowmsestore.dto.TestDTO;
import br.com.samsung.wms.latam.cellowmsestore.exception.BusinessException.BusinessExceptionBody;
import br.com.samsung.wms.latam.cellowmsestore.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/v2/test")
@Slf4j
@Api(tags = {"cellowmsestore"})
public class TestControllerV2 {

	@Autowired
	public TestService service;

	@ApiOperation(value = "Endpoint que busca todos os registros test")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso", response = TestDTO.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class)

	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TestDTO>> findAll() throws Exception {

		log.info("Find all tests");

		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@ApiOperation(value = "Endpoint que busca registro test por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição processada com Sucesso. registro localizado.", response = TestDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 404, message = "Not Found. Registro(s) não encontrado(s).", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class) })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestDTO> findById(@Valid @PathVariable Long id) throws Exception {

		log.info("Buscar test {}", id);

		return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
	}

	@ApiOperation(value = "Endpoint que salvar registro test")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Registro criado.", response = TestDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 400, message = "Bad Request. Parâmetro(s) inválido(s)", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class) })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestDTO> save(
			@Valid @RequestBody @ApiParam(value = "TestDto", required = true, name = "testDto") TestDTO testDTO)
			throws Exception {

		log.info("Save test {}", testDTO);

		return ResponseEntity.status(HttpStatus.OK).body(service.save(testDTO));
	}

	@ApiOperation(value = "Endpoint que atualiza registro test")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Registro Atualizado.", response = TestDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 404, message = "Not Found. Registro(s) não encontrado(s).", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class) })
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestDTO> update(@Valid @PathVariable Long id,
			@Valid @RequestBody @ApiParam(value = "TestDtos", required = true, name = "testDto") TestDTO testDTO)
			throws Exception {

		log.info("Atualiza test {}", testDTO);
		testDTO.setId(id);
		service.update(testDTO);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Endpoint que Exclui registro test")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Registro Excluído.", response = TestDTO.class),
			@ApiResponse(code = 500, message = "Erro interno", response = BusinessExceptionBody.class),
			@ApiResponse(code = 404, message = "Not Found. Registro(s) não encontrado(s).", response = BusinessExceptionBody.class),
			@ApiResponse(code = 401, message = "Unauthorized - Usuário não Autorizado", response = BusinessExceptionBody.class),
			@ApiResponse(code = 403, message = "Forbidden - Usuário não Autenticado", response = BusinessExceptionBody.class) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {

		log.info("Delete test {}", id);
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}