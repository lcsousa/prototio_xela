package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDTO {
	@NotNull(message = "Campo Login é obrigatório")
	@ApiModelProperty(value = "Login do usuário", name = "login", dataType = "String", example = "luisAdmin",position = 1)
	private String login;
	
	
	@NotNull(message = "Campo Password é obrigatório")
	@ApiModelProperty(value = "Senha atual do usuário", name = "password", dataType = "String", example = "jah",position = 2)
	private String password;
	
	@NotNull(message = "Campo new Password é obrigatório")
	@ApiModelProperty(value = "Nova Senha do usuário", name = "password", dataType = "String", example = "jah2",position = 3)
	private String newPassword;
	
}
