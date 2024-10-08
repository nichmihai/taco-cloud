package com.example.demo.model;

import lombok.Data;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;
    @Size(min = 1, message = "You must chose at least 1 ingredient")
    private List<String> ingredients;
    private Date createdAt;
}
