package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
	@NotNull(message = "Campo Login é obrigatório")
	@ApiModelProperty(value = "Login do usuário", name = "login", dataType = "String", example = "luisAdmin",position = 1)
	private String login;
	
	
	@NotNull(message = "Campo New Password é obrigatório")
	@ApiModelProperty(value = "Nova Senha do usuário", name = "password", dataType = "String", example = "jah",position = 2)
	private String newPassword;
	
	
}
