package com.example.demo.model;

import com.example.demo.domain.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class Taco {
    private String name;
    private List<String> ingredients;
}
