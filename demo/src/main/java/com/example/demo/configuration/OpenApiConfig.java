package com.example.demo.configuration;

import com.example.demo.dto.game.response.CreateGameResponseDTO;
import com.example.demo.dto.user.request.CreateUserRequestDTO;
import com.example.demo.dto.user.response.CreateUserResponseDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;


import io.swagger.v3.oas.annotations.media.Schema;
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
@OpenAPIDefinition
public class OpenApiConfig {
    @Bean
    public OpenAPI baseOpenApi(){
        ApiResponse badRequestAddUser = new ApiResponse().content(
                new Content()).description("User cant be added to the db, some data are already present in the db");
        ApiResponse internalServerErrorAddUser = new ApiResponse().content(
                new Content()).description("User cant be added to the db, the given data is not valid ");
        ApiResponse successfulResponseAddUser = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(new JSONObject().put("name","string").put("mail","string@mail.dom").put("phone","365821648").put("password","string").toMap()))
                                ));//to add more example

        Components components = new Components();
        components.addResponses("badRequestAddUser",badRequestAddUser);
        components.addResponses("internalServerErrorAddUser",internalServerErrorAddUser);
        components.addResponses("successfulResponseAddUser",successfulResponseAddUser);





            //Nel caso meno pulito per controller ma con schema al posto di example
       /* description ="User got added on the db",
                content ={ @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                schema = @Schema(implementation = CreateUserResponseDTO.class))}*/


        return new OpenAPI().components(components).info(new Info().title("OpenApi swagger for Studio SpringBoot").version("1.0.0").description("thats the descripion"));
    }
}
