package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.ConfirmationToken;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.exceptions.InvalidUserCredentialsException;
import onlineshopapp.fashionstore.repository.ConfirmationTokenRepository;
import onlineshopapp.fashionstore.service.AuthService;
import onlineshopapp.fashionstore.service.EmailService;
import onlineshopapp.fashionstore.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "account";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try {
            user = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }
}



