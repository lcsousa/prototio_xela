package br.com.samsung.wms.latam.cellowmsestore.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.samsung.wms.latam.cellowmsestore.dto.TestDTO;
import br.com.samsung.wms.latam.cellowmsestore.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test")
@Slf4j
@Api(tags = {"cellowmsestore"})
public class TestController {

    @Autowired
    public TestService service;

    @ApiOperation(value = "Endpoint que busca todos os registros test" )
    @GetMapping
    public ResponseEntity<List<TestDTO>> findAll() throws Exception {
    	
        log.info("Find all tests");

        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
    
    @ApiOperation(value = "Endpoint que salvar registro test" )
    @PostMapping
    public ResponseEntity<TestDTO> save(@Valid @RequestBody @ApiParam(value = "TestDtos", required = true , name = "testDto") TestDTO testDTO) throws Exception {
    	
        log.info("Save test {}", testDTO );

        return ResponseEntity.status(HttpStatus.OK).body(service.save(testDTO));
    }



}