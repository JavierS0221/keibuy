package com.project.auction.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private String username;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date birthDate;

    private MultipartFile avatar;
    private String pathAvatar;

    private boolean accountVerified;

}
