package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
	@NotNull(message = "Campo Token é obrigatório")
	private String token;
}
