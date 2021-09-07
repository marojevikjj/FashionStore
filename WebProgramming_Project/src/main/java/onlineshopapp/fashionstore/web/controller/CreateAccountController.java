package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.model.exceptions.*;
import onlineshopapp.fashionstore.service.PostmanOrderSerivce;
import onlineshopapp.fashionstore.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final PostmanOrderSerivce postmanOrderSerivce;


    public CreateAccountController(UserService userService, PostmanOrderSerivce postmanOrderSerivce) {
        this.userService = userService;
        this.postmanOrderSerivce = postmanOrderSerivce;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String getCreateAccountPagePage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "createAccount";
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public String createAccount(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String repeatedPassword,
                                @RequestParam String name,
                                @RequestParam String email,
                                @RequestParam Role role,
                                @RequestParam(required = false) String city,
                                Model model) {
        try{
            User user= this.userService.register(name, username,password,repeatedPassword,role,email);
            if( city!= null && role.equals(Role.ROLE_POSTMAN)) {
                   this.postmanOrderSerivce.create(user,city);
            }
            model.addAttribute("success",true);
            return "createAccount";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException | UsernameAlreadyExistsException | InvalidEmailException | EmailAlreadyExistsException exception) {
            return "redirect:/create-account?error=" + exception.getMessage();
        }
    }
}

