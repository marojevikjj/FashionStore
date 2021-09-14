package onlineshopapp.fashionstore.web.controller;
import com.itextpdf.html2pdf.HtmlConverter;
import com.lowagie.text.DocumentException;
import onlineshopapp.fashionstore.model.*;
import onlineshopapp.fashionstore.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller

public class OrderController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final UserVoucherService userVoucherService;
    private final VoucherService voucherService;
    private final OrderService orderService;
    private final PostmanOrderSerivce postmanOrderSerivce;
    private final ClothesService clothesService;

    public OrderController(UserService userService, ShoppingCartService shoppingCartService, UserVoucherService userVoucherService, VoucherService voucherService, OrderService orderService, PostmanOrderSerivce postmanOrderSerivce, ClothesService clothesService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.userVoucherService = userVoucherService;
        this.voucherService = voucherService;
        this.orderService = orderService;
        this.postmanOrderSerivce = postmanOrderSerivce;
        this.clothesService = clothesService;
    }


    @GetMapping("/orders")
    public String showOrderPage(Model model, HttpServletRequest req) {

        String username = req.getRemoteUser();
        User user = (User) this.userService.loadUserByUsername(username);
        List<Order> orders = this.orderService.findOrdersByUser(user);
        model.addAttribute("orders", orders);

        return "orders";
    }


    @PostMapping("/purchase")
    public String purchaseProducts(@RequestParam String name, @RequestParam String surname, @RequestParam String address,
                                              @RequestParam String telephone, @RequestParam String card, @RequestParam Double discount, @RequestParam Double total,
                                              @RequestParam(required = false) Long voucher, @RequestParam String city, HttpServletRequest req, Model model) {
        if(name == null || surname == null || address == null || telephone == null || card == null || city == null || discount == null)
            return "redirect:/products";

        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        List<Postman> postman = this.postmanOrderSerivce.findFromRegion(city);
        System.out.println(postman);
        Postman p = postman.stream().findAny().get();
        for(Postman po : postman)
            if(po.getCount() < p.getCount())
                p = po;
        Order o = this.orderService.createOrder(name, surname, address, telephone, city, discount, (User) this.userService.loadUserByUsername(username), shoppingCart.getOrderedClothes(), p.getUser());

        this.shoppingCartService.deleteOrderedClothes(shoppingCart);
        List<Order> orders = this.orderService.findOrdersByUser((User) this.userService.loadUserByUsername(username));
        model.addAttribute("orders", orders);
        if(voucher != null && total > discount)
            this.userVoucherService.delete(this.voucherService.findById(voucher).get(), (User) this.userService.loadUserByUsername(username));


        return "redirect:/orders";
    }

    @GetMapping("/download-pdf/{id}")
    public void pdfOrder(HttpServletResponse response, HttpServletRequest req, @PathVariable Long id) throws IOException, DocumentException {
        String username = req.getRemoteUser();
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Order order = this.orderService.findById(id).get();

        PDFExport exporter = new PDFExport(order);
        exporter.export(response);
    }


    @PostMapping("/makeOrder")
    public String makeOrderPage(@RequestParam Double total, HttpServletRequest req, Model model){

        model.addAttribute("total", total);
        model.addAttribute("discount", total);
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("products", shoppingCart.getOrderedClothes());

        return "makeOrder";
    }


    @GetMapping("/makeOrder/discountPrice")
    public String makeOrderPage(@RequestParam String name, @RequestParam String surname, @RequestParam String address,
                                @RequestParam String telephone, @RequestParam String card, @RequestParam Double total,
                                @RequestParam(required = false) Long voucher, @RequestParam String city, HttpServletRequest req, Model model){

        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        String message = "";
        double discount = total;
        if(voucher != null){
            UserVoucher uv = this.userVoucherService.searchByUserAndVoucher((User) this.userService.loadUserByUsername(username), this.voucherService.findById(voucher));
            if(uv != null){
                discount = total - discount * (uv.getVoucher().getDiscount()/100.00);
                message = "* You get " + uv.getVoucher().getDiscount() + "% discount!";
            }
            else message = "* You have entered invalid voucher code!";
        }
        else message = "* Input field for voucher code is required!";
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("address", address);
        model.addAttribute("telephone", telephone);
        model.addAttribute("card", card);
        model.addAttribute("voucher", voucher);
        model.addAttribute("city", city);
        model.addAttribute("discount", discount);
        model.addAttribute("total", total);
        model.addAttribute("products", shoppingCart.getOrderedClothes());
        model.addAttribute("error", message);

        return "makeOrder";
    }


}