package com.prayagrajsharma.linkedInProject.userService.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
