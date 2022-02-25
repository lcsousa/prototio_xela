package br.com.samsung.wms.latam.cellowmsestore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig  {

	   @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("br.com.samsung.wms.latam.cellowmsestore.controller"))
	                .build()
	                .apiInfo(metaData());
	    }

	    private ApiInfo metaData() {
	        return new ApiInfoBuilder()
	                .title("WMS Connector API - SAMSUNG SDS")
	                .description("WMS Mobile API REST")
	                .version("1.0")
	                .contact(new Contact("Samsung SDS", "http://cellolatam.cellologistics.com.br/cello-marketing/", "mm.nogueira@samsung.com"))
	                .license("Samsung SDS Latin America")
	                .licenseUrl("http://cellolatam.cellologistics.com.br/cello-marketing/")
	                .build();
	    }

//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("br.com.samsung.wms.latam.cellowmsestore.controller"))
//				.paths(PathSelectors.any()).build()
//				.pathMapping("/")
//				.useDefaultResponseMessages(false)
//				.tags(new Tag("cellowmsestore", "Description of Cello WMS Estore API"))
//				.apiInfo(apiInfo());
//
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Cello WMS Estore API Rest")
//				.description("Documentation of Cello WMS estore API Rest")
//				.version("1.0")
//				.license("Samsung SDS Latin America")
//				.licenseUrl("http://cellolatam.cellologistics.com.br/cello-marketing/")
//				.contact(new Contact("Samsung SDS", "http://cellolatam.cellologistics.com.br/cello-marketing/", "mm.nogueira@samsung.com"))
//				.build();
//	}


}
