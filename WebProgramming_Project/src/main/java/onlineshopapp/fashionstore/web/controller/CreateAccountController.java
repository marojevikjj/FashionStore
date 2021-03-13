package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.model.exceptions.InvalidArgumentsException;
import onlineshopapp.fashionstore.model.exceptions.PasswordsDoNotMatchException;
import onlineshopapp.fashionstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/create-account")
public class CreateAccountController {

    private final UserService userService;


    public CreateAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getCreateAccountPagePage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "createAccount";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String email,
                           Role role,
                           Model model) {
        try{
            this.userService.register(name, username,password,repeatedPassword,role,email);
            return "redirect:/products";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/createAccount?error=" + exception.getMessage();
        }
    }
}

