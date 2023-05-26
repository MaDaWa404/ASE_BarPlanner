package de.dhbw.plugins.userInterface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "owner")
    public String get() {
        return "owner";
    }

}
