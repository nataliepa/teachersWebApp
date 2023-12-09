package gr.aueb.elearn.teachersWebApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VersionController {
    @GetMapping("/version")
    public String showVersion() {
        return "version";
    }
}
