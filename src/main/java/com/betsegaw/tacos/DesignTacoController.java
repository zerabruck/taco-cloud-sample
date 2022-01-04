package com.betsegaw.tacos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.betsegaw.tacos.Ingredient.Type;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository repository;

    @GetMapping("/design")
    public String showDesignForm(Model model) {

        Type[] types = Type.values();

        List<Ingredient> ingredients = new ArrayList<>();
        this.repository.findAll().forEach(i -> ingredients.add(i));

        for (Type t : types) {
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }
        return "design";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @PostMapping("/design")
    public String processTaco(@Valid Taco taco, @ModelAttribute TacoOrder order, Errors errors) {

        if (errors.hasErrors()) {
            return "design";
        }

        order.addTaco(taco);

        return "redirect:/orders/current";
    }
}