package com.xmyy.demand.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = {"com.xmyy.demand.web"})
public class SwaggerConfig {

	@Bean
	public Docket platformApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(true);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("APP需求模块").description("发需求/接需求/指定买手").build();
	}
}