package br.com.samsung.wms.latam.cellowmsestore.mapper.security;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.samsung.wms.latam.cellowmsestore.dto.security.SaveUserRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.UserDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.UserEntity;


@Mapper(componentModel = "spring")
public interface UserMapper {

	 List<UserDTO> convertListEntitysToDtos(List<UserEntity> carEntities);
	 
	 UserDTO convertEntityToDto(UserEntity testEntity);
	 
	 UserEntity convertDtoToEntity(UserDTO testDTO);
	 
	 UserEntity convertSaveDtoToEntity(SaveUserRequestDTO testDTO);
}
