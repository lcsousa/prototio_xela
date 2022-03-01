package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequestDTO {
	
	@ApiModelProperty(value = "Refresh Token do usuário", name = "token", dataType = "String", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsdWlzQWRtaW4iLCJDTEFJTV9UT0tFTl9UWVBFIjoiUkVGUkVTSF9UT0tFTiIsImV4cCI6MTY0NjA5NDQwNH0.nPHM_0v1Rx8uCXalqyKizHCftaWLeokZVP2z47qdP9m21f3ccwlV_j0iLPIaEsiVNGqj4rgzvQYgLqtcc_m27w")
	private String refreshToken;
	
}
