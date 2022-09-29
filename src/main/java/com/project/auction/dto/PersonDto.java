package com.project.auction.dto;

import com.project.auction.constraints.BirthDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class PersonDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;

    @NotEmpty
    @Size(min = 2, max = 32)
    private String username;

    @NotEmpty(message="El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede tener más de 45 caracteres")
    private String name;

    @NotEmpty(message="El apellido es obligatorio")
    @Size(max = 45, message = "El apellido no puede tener más de 45 caracteres")
    private String lastName;

    @NotEmpty(message="La contraseña es obligatoria")
    @Size(min = 6, max = 128, message = "La contraseña debe tener entre 6 y 128 caracteres")
    private String password;

    @NotEmpty(message="El email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    @NotEmpty(message="El pais es obligatorio")
    private String country;

    @NotEmpty(message="El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede tener más de 15 dígitos")
    private String phone;

    @NotNull(message = "The birth date is required.")
    @BirthDate(message = "The birth date must be greater or equal than 18")
    @Past(message = "The birth date must be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    private MultipartFile avatar;

    private boolean accountVerified = false;

    private boolean accountBanned = false;

}
