package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUserRequestDTO {

    

	@NotNull(message = "Campo login é obrigatório")
	@ApiModelProperty(value = "Login do usuário", name = "login", dataType = "String", example = "luis")
    private String login;

	@NotNull(message = "Campo password é obrigatório")
	@ApiModelProperty(value = "Password do usuário", name = "password", dataType = "String", example = "jah")
    private String password;

   

}
