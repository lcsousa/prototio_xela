package br.com.samsung.wms.latam.cellowmsestore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.samsung.wms.latam.cellowmsestore.dto.TestDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.TestEntity;
import br.com.samsung.wms.latam.cellowmsestore.exception.MessageBusiness;
import br.com.samsung.wms.latam.cellowmsestore.mapper.TestMapper;
import br.com.samsung.wms.latam.cellowmsestore.repository.TestRepository;
import br.com.samsung.wms.latam.cellowmsestore.service.http.FeignClientDocumentService;

@Service
public class TestService {

	@Autowired
	private TestMapper testMapper;

	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private FeignClientDocumentService feignClientDocumentService;

	public List<TestDTO> findAll() {

		return testMapper.convertListEntitysToDtos(testRepository.findAll());

	}

	public TestDTO findById(Long id) {
		Optional<TestEntity> testEntity = testRepository.findById(id);
		
		if (testEntity.isPresent()) {
			
			TestDTO testDTO = testMapper.convertEntityToDto(testEntity.get());
			
			String documentNumber = feignClientDocumentService.listDocuments().get(0).getDocumentNumber();
			
			testDTO.setDescription(testDTO.getDescription().concat(documentNumber));
			
			return testDTO;
		}else {
			throw MessageBusiness.NOT_FOUND.createException();
		}
		

	}

	public TestDTO save(TestDTO testDTO) {
		if (!testDTO.getName().contains("0")) {
			throw MessageBusiness.NUMBER_0_REQUIRED.createException();
		}

		return testMapper.convertEntityToDto(testRepository.save(testMapper.convertDtoToEntity(testDTO)));

	}

	public void delete(Long id) {
		testRepository.deleteById(id);
	}
	
	public void update(TestDTO testDTO) {
		findById(testDTO.getId());
		testRepository.save(testMapper.convertDtoToEntity(testDTO));
		
	}
}
