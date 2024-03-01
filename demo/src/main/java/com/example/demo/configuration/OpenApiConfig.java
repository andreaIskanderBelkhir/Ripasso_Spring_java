package com.example.demo.configuration;

import com.example.demo.dto.game.response.CreateGameResponseDTO;
import com.example.demo.dto.user.request.CreateUserRequestDTO;
import com.example.demo.dto.user.response.CreateUserResponseDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        servers = {@Server(description = "serverl locale generale",url="http://localhost:8080")}
)
public class OpenApiConfig {

    public static final String SUCCESSFUL_RESPONSE_ADD_USER="successfulResponseAddUser";
    public static final String INTERNAL_SERVER_ERROR_ADD_USER ="internalServerErrorAddUser";
    public static final String BAD_REQUEST_ADD_USER="badRequestAddUser";

    @Bean
    public OpenAPI baseOpenApi() {
        ApiResponse badRequestAddUser = new ApiResponse().content(
                new Content()).description("User cant be added to the db, some data are already present in the db");
        ApiResponse internalServerErrorAddUser = new ApiResponse().content(
                new Content()).description("User cant be added to the db, the given data is not valid ");
        ApiResponse successfulResponseAddUser = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(new JSONObject().put("name", "admin").put("mail", "admin@mail.it").put("phone", "4684449511").put("password", "APassword").toMap()))
                ));//to add more example

        Components components = new Components();
        //components.addSecuritySchemes("Basic Authentication",createApiScheme());
        components.addResponses(BAD_REQUEST_ADD_USER, badRequestAddUser);
        components.addResponses(INTERNAL_SERVER_ERROR_ADD_USER, internalServerErrorAddUser);
        components.addResponses(SUCCESSFUL_RESPONSE_ADD_USER, successfulResponseAddUser);


        //Nel caso meno pulito per controller ma con schema al posto di example
       /* description ="User got added on the db",
                content ={ @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                schema = @Schema(implementation = CreateUserResponseDTO.class))}*/


            return new OpenAPI().components(components).info(new Info().title("OpenApi swagger for Studio SpringBoot").version("1.0.0").description("thats the description " ));

    }

    @Bean
    public void defineString(){

    }

}
