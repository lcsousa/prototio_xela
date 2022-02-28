package br.com.samsung.wms.latam.cellowmsestore.dto;

import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConstructorBinding
public class HasRolesResponseDTO {
	private boolean hasRole;

	public HasRolesResponseDTO(boolean hasRole) {
		super();
		this.hasRole = hasRole;
	}
	
	
}
