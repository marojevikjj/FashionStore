package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.ConfirmationToken;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.service.ConfirmationTokenService;
import onlineshopapp.fashionstore.service.EmailService;
import onlineshopapp.fashionstore.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class PasswordController {
    private final UserService userService;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;


    public PasswordController(UserService userService, EmailService emailService, ConfirmationTokenService confirmationTokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.confirmationTokenService = confirmationTokenService;
        this.passwordEncoder = passwordEncoder;
    }


    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("forgotPassword");
        return modelAndView;
    }

    // Receive the address and send an email
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public ModelAndView forgotUserPassword(ModelAndView modelAndView, User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

            // Save it
            confirmationTokenService.save(confirmationToken);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("test-email@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:9091/confirm-reset?token="+confirmationToken.getConfirmationToken());

            // Send the email
            emailService.sendEmail(mailMessage);

            modelAndView.addObject("message", "Request to reset password received. Check your inbox for the reset link.");
            modelAndView.setViewName("successForgotPassword");

        } else {
            modelAndView.addObject("message", "This email address does not exist!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
    @RequestMapping(value="/confirm-reset", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token")String confirmationToken) {
        ConfirmationToken token = this.confirmationTokenService.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userService.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userService.save(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("emailId", user.getEmail());
            modelAndView.setViewName("resetPassword");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    // Endpoint to update a user's password
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
        if (user.getEmail() != null) {
            // Use email to find user
            User tokenUser = userService.findByEmail(user.getEmail());
            tokenUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(tokenUser);
            modelAndView.addObject("message", "Password successfully reset. You can now log in with the new credentials.");
            modelAndView.setViewName("successResetPassword");
        } else {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

}