package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
	@NotNull(message = "Campo Login é obrigatório")
	@ApiModelProperty(value = "Login do usuário", name = "login", dataType = "String", example = "luisAdmin")
	private String login;
	
	@NotNull(message = "Campo Password é obrigatório")
	@ApiModelProperty(value = "Senha do usuário", name = "password", dataType = "String", example = "jah")
	private String password;
}
