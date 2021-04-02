package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.Order;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.OrderStatus;
import onlineshopapp.fashionstore.service.OrderService;
import onlineshopapp.fashionstore.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/postman")
public class PostmanController {

    private final OrderService orderService;
    private final UserService userService;


    public PostmanController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_POSTMAN')")
    @GetMapping("/orders")
    public String getPostmanOrders(Model model, HttpServletRequest req){

        String username = req.getRemoteUser();
        List<Order> orders = this.orderService.findOrdersByPostman((User) this.userService.loadUserByUsername(username));
        model.addAttribute("orders", orders);
        String [] status = {OrderStatus.Ordered.toString(), OrderStatus.Confirmed.toString(), OrderStatus.Prepared.toString(),
                OrderStatus.ReceivedByPostman.toString(), OrderStatus.OnYourWay.toString(), OrderStatus.Delivered.toString()};
        model.addAttribute("status", status);

        return "postmanOrders";
    }
    @PreAuthorize("hasAnyRole('ROLE_POSTMAN')")
    @PostMapping("/changeStatus/{id}")
    public String changeOrderStatus(@PathVariable Long id, @RequestParam String status){

        Order order = this.orderService.findById(id).get();
        if(!status.equals("Select Status"))
            this.orderService.changeOrderStatus(id, status);

        return "redirect:/postman/orders";
    }
}
