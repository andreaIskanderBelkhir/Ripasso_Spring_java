package com.example.demo.dto.user.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;

@Data
//Api updateUser cambia solo nome e email
public class PutUserRequestDTO {
    private String name;
    private String email;
    private String phone;

    public void isValid() throws DataIntegrityViolationException {
        if(!(this.email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))){
            throw new DataIntegrityViolationException("email non valida");
        }
        if((this.phone!=null) && !(this.phone.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$"))){
            throw  new DataIntegrityViolationException("telefono non valido");
        }
    }

}
