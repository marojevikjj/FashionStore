package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.ConfirmationToken;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;

import onlineshopapp.fashionstore.model.exceptions.*;
import onlineshopapp.fashionstore.service.ConfirmationTokenService;
import onlineshopapp.fashionstore.service.EmailService;
import onlineshopapp.fashionstore.service.UserService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    private final EmailService emailSenderService;

   private final ConfirmationTokenService confirmationTokenService;


    public RegisterController(UserService userService, EmailService emailSenderService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam(required = false) Role role,
                           Model model) {
        if(role==null) {
            role = Role.ROLE_USER;
        }
        try {
            User user = this.userService.register(name, username,password,repeatedPassword,role,email);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            this.confirmationTokenService.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("chand312902@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:9091/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);


            model.addAttribute("emailId", user.getEmail());

            return "successfulRegistration";
        }
        catch (InvalidArgumentsException | PasswordsDoNotMatchException | UsernameAlreadyExistsException | InvalidEmailException | EmailAlreadyExistsException exception) {
            return "redirect:/register?error="+ exception.getMessage();
        }
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = this.confirmationTokenService.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            System.out.println("razlicno of null");
            User user = userService.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            this.userService.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            System.out.println("here");
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
}
