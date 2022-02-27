package br.com.samsung.wms.latam.cellowmsestore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import br.com.samsung.wms.latam.cellowmsestore.model.RoleAuthEnum;
import br.com.samsung.wms.latam.cellowmsestore.model.UserEntity;
import br.com.samsung.wms.latam.cellowmsestore.repository.UserRepository;

@Component
public class UserService {
	@Autowired
	private UserRepository repository;

	public List<UserEntity> findAll() throws UsernameNotFoundException {
		List<UserEntity> listAll = repository.findAll();
		if(!ObjectUtils.isEmpty(listAll)) {
			listAll.stream().forEach(model ->{
				 
				setRole(model);
			 });
			
		}
		
		return listAll;
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
	
	
	
	public UserEntity save(UserEntity usuarioModel) throws UsernameNotFoundException {
		usuarioModel = repository.save(usuarioModel);
		setRole(usuarioModel);
		return usuarioModel;
	}
	
	private void setRole(UserEntity usuarioModel) {
		List<RoleAuthEnum> listaRole =  new ArrayList<>();
		if (usuarioModel.getId() % 2 == 1) {
			listaRole.add(RoleAuthEnum.ROLE_USER_CREATE);
			listaRole.add(RoleAuthEnum.ROLE_USER_FINDALL);
			listaRole.add(RoleAuthEnum.ROLE_AUTH_REFRESH_TOKEN);
			listaRole.add(RoleAuthEnum.ROLE_AUTH_IDENTITY_USER);
		} else {
			listaRole.add(RoleAuthEnum.ROLE_USER_FINDALL);
		}
		usuarioModel.setListRole(listaRole);
	}
	
	

}
