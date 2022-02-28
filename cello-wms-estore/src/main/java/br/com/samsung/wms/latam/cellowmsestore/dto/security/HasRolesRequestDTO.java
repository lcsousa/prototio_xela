package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HasRolesRequestDTO {
	
	@NotNull(message = "Campo Token é obrigatório")
	@ApiModelProperty(value = "Token do usuário", name = "token", dataType = "String", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsdWlzQWRtaW4iLCJleHAiOjE2NDYwODA1MTB9.C1g67D05FyIBDt8w9PKWHp_4bzqkz-n_AI3fY5Qd-2D00ndWGhxWHrBaV6_ybRXpUHbcBP260KSAZy-bSWW_4g")
	private String token;
	@NotNull(message = "Campo Role é obrigatório")
	@ApiModelProperty(value = "Role do Sistema", name = "role", dataType = "String", example = "ROLE_AUTH_HAS_ROLE")
	private String role;
}
