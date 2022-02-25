package br.com.samsung.wms.latam.cellowmsestore.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.samsung.wms.latam.cellowmsestore.dto.TestDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.TestEntity;


@Mapper(componentModel = "spring")
public interface TestMapper {

	 List<TestDTO> convertListEntitysToDtos(List<TestEntity> carEntities);
	 
	 TestDTO convertEntityToDto(TestEntity testEntity);
	 
	 TestEntity convertDtoToEntity(TestDTO testDTO);
}
