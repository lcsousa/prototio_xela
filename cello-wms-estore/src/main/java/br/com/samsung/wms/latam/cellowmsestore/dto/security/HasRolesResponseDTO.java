package br.com.samsung.wms.latam.cellowmsestore.dto.security;

import org.springframework.boot.context.properties.ConstructorBinding;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConstructorBinding
public class HasRolesResponseDTO {
	
	@ApiModelProperty(value = "Informa se o usuário possui a Role", name = "hasRole", dataType = "String", example = "true")
	private boolean hasRole;

	public HasRolesResponseDTO(boolean hasRole) {
		super();
		this.hasRole = hasRole;
	}
	
	
}
