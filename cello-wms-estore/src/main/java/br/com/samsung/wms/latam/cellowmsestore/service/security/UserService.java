package br.com.samsung.wms.latam.cellowmsestore.service.security;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import br.com.samsung.wms.latam.cellowmsestore.dto.security.SaveUserRequestDTO;
import br.com.samsung.wms.latam.cellowmsestore.dto.security.UserDTO;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.RoleAuthEnum;
import br.com.samsung.wms.latam.cellowmsestore.entity.security.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.mapper.security.UserMapper;
import br.com.samsung.wms.latam.cellowmsestore.repository.security.UserRepository;

@Component
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository repository;

	public List<UserDTO> findAll() throws UsernameNotFoundException {
		List<UserEntity> listAll = repository.findAll();
		if(!ObjectUtils.isEmpty(listAll)) {
			listAll.stream().forEach(model ->{
				 
				setRole(model);
			 });
			
		}
		
		return userMapper.convertListEntitysToDtos(listAll);
	}
	
	public UserEntity findByLogin(String username) throws UsernameNotFoundException {
		Optional<UserEntity> usuario = repository.findByLogin(username);
		if (usuario.isPresent()) {
			UserEntity model = usuario.get();

			setRole(model);
			return model;
		}

		return null;
	}
	
	public UserEntity findByLoginAndPassword(String username,String password) throws UsernameNotFoundException {
		Optional<UserEntity> usuario = repository.findByLoginAndPassword(username,password);
		if (usuario.isPresent()) {
			UserEntity model = usuario.get();

			setRole(model);
			return model;
		}

		return null;
	}
	
	
	
	public UserDTO save(SaveUserRequestDTO usuarioModel) throws UsernameNotFoundException {
		UserEntity entity = repository.save(userMapper.convertSaveDtoToEntity(usuarioModel));
		setRole(entity);
		return userMapper.convertEntityToDto(entity);
	}
	
	private void setRole(UserEntity usuarioModel) {
		List<RoleAuthEnum> listaRole =  new ArrayList<>();
		if (usuarioModel.getId() % 2 == 1) {
			listaRole.add(RoleAuthEnum.ROLE_USER_CREATE);
			listaRole.add(RoleAuthEnum.ROLE_USER_FINDALL);
			listaRole.add(RoleAuthEnum.ROLE_AUTH_REFRESH_TOKEN);
			listaRole.add(RoleAuthEnum.ROLE_AUTH_IDENTITY_USER);
			listaRole.add(RoleAuthEnum.ROLE_AUTH_GET_ROLES);
			listaRole.add(RoleAuthEnum.ROLE_AUTH_HAS_ROLE);
			
			
		} else {
			listaRole.add(RoleAuthEnum.ROLE_USER_FINDALL);
		}
		usuarioModel.setListRole(listaRole);
	}
	
	

}
