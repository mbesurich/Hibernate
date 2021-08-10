package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model) {

//        System.out.printf("Hello, %s %s \n", name, surname);
        model.addAttribute("message", String.format("Hello, %s %s \n", name, surname));

        return "first/hello";
    }

    @GetMapping("/calculator")
    public String calculator(@RequestParam("a") int a, @RequestParam("b") int b,
                             @RequestParam("action") String action, Model model) {
        double result = 0;
        switch (action) {
            case "add":
                result = a + b;
                break;
            case "minus":
                result = a - b;
                break;
            case "multiply":
                result = a * b;
                break;
            case "divide":
                result = a / (double)b;
                break;
        }

        model.addAttribute("result", result);

        return "first/calculator";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }
}
