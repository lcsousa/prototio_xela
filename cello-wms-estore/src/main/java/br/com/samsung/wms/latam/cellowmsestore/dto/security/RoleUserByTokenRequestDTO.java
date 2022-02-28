package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUserByTokenRequestDTO {
	
	@ApiModelProperty(value = "Access Token do usuário", name = "token", dataType = "String", example = "adsds.sdsd.sdsd")
	private String accessToken;
	
}
