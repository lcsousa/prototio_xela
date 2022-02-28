package br.com.samsung.wms.latam.cellowmsestore.service.http;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.samsung.wms.latam.cellowmsestore.dto.DocumentDto;

@FeignClient(name = "testfeign", url = "${url.feign.base.client}")
public interface FeignClientDocumentService {
	
	  @GetMapping("/docs")
	  List<DocumentDto> listDocuments();

}
