package br.com.samsung.wms.latam.cellowmsestore.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import br.com.samsung.wms.latam.cellowmsestore.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "Test")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(of = {"id","name" ,"description", "insertDate","insertId" })
public class TestDTO {
	
	private Long id;
	
	@NotNull(message = "Campo name é obrigatório")
	@ApiModelProperty(value = "Nome do Teste", name = "name", dataType = "String", example = "Teste0")
	private String name;
	
	@ApiModelProperty(value = "Descrição do Teste",name = "description", dataType = "String", example = "Teste0")
	private String description;

	@NotNull(message = "Campo insertDate é obrigatório")
	@ApiModelProperty(value = "Data de Criação do Teste",name = "insertDate", dataType = "LocalDateTime", example = "01/01/2022 00:00:00")
	@JsonFormat(pattern = Constants.DATETIME_PATTERN, locale = "pt-BR", timezone = "Brazil/East")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime insertDate;

	@ApiModelProperty(value = "Id do usuário criador do Teste",name = "insertId", dataType = "String", example = "paulo.valentim")
	@NotNull(message = "Campo insertId é obrigatório")
	private String insertId;

}
