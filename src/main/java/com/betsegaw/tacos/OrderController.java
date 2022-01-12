package com.betsegaw.tacos;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class OrderController {

    private final TacoOrderRepository repository;

    @GetMapping("/orders/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping("/orders")
    public String processOrder(@Valid @ModelAttribute TacoOrder order, Errors errors, SessionStatus status) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        this.repository.save(order);
        status.setComplete();

        return "redirect:/";
    }
}
