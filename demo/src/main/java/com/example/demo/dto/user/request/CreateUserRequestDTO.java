package com.example.demo.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;


@Data
public class CreateUserRequestDTO  {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private String phone;
    @NotNull
    private String password;

    public void isValid() throws DataIntegrityViolationException {
        if(!(this.email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))){
        throw new DataIntegrityViolationException("email non valida");
        }
        if((this.phone!=null) && !(this.phone.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$"))){
        throw  new DataIntegrityViolationException("telefono non valido");
        }
    }

    @Schema (example = "admin",description = "nome del user")
    public String getName(){
        return name;
    }
    @Schema (example = "admin@mail.it")
    public String getEmail(){
        return email;
    }
    @Schema(example = "APassword")
    public String getPassword(){
        return password;
    }
}
