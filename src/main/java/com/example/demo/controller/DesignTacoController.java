package com.example.demo.controller;

import com.example.demo.domain.Ingredient;
import com.example.demo.model.Order;
import com.example.demo.model.Taco;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.Ingredient.Type;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository designRepo) {
        this.ingredientRepository = ingredientRepository;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "order")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design,
                                Errors errors,
                                @ModelAttribute Order order) {

        if(errors.hasErrors()) {
            return "design";
        }

        Taco saved = designRepo.save(design);

        order.addDesign(saved);

        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }


    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

        return ingredients.stream()
                .filter(t-> t.getType().equals(type))
                .collect(Collectors.toList());
    }
}
