package gr.aueb.elearn.teachersWebApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("")
    public String showHomePage() {

        return "index";
    }
}
