package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.model.*;
import onlineshopapp.fashionstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ClothesService clothesService;
    private final UserService userService;
    private final ClothesCommentService clothesCommentService;
    private final ClothesGradeService clothesGradeService;
    private final CommentUserLikeService commentUserLikeService;

    public ProductsController(ClothesService clothesService, UserService userService, ClothesCommentService clothesCommentService, ClothesGradeService clothesGradeService, CommentUserLikeService commentUserLikeService) {
        this.clothesService = clothesService;
        this.userService = userService;
        this.clothesCommentService = clothesCommentService;
        this.clothesGradeService = clothesGradeService;
        this.commentUserLikeService = commentUserLikeService;
    }

  /*  @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Clothes> products = this.clothesService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "products";
    }
*/

    @GetMapping
    public String showProducts(@RequestParam(required = false) String nameSearch, @RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) String error, Model model) {
        if (nameSearch == null && categoryId == null) {
            model.addAttribute("products", this.clothesService.listAllClothes());
        } /* else {
            model.addAttribute("products", this.service.listProductsByNameAndCategory(nameSearch, categoryId));
        } */
        if(error != null)
            model.addAttribute("error", error);
        return "products";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model, HttpServletRequest req) {

        String username = req.getRemoteUser();
        Clothes clothes = this.clothesService.findById(id);
        User user = (User) this.userService.loadUserByUsername(username);

        model.addAttribute("product", this.clothesService.findById(id));
        model.addAttribute("comments", this.clothesCommentService.findCommentsByProduct(id));
        if(this.clothesGradeService.findByUserAndClothes(user, clothes) != null)
            model.addAttribute("grade", this.clothesGradeService.findByUserAndClothes(user, clothes).getGrade());
        else
            model.addAttribute("grade", null);

        return "product-details";
    }

    @GetMapping("/add")
    public String showAdd() {
        return "form";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("product", this.clothesService.findById(id));
        return "form";
    }

    @PostMapping
    public String create(@RequestParam String name, @RequestParam String description, @RequestParam String image, @RequestParam String image1, @RequestParam String image2,
                         @RequestParam String image3, @RequestParam Double price, @RequestParam Double grade, @RequestParam Integer quantitySizeS,
                         @RequestParam Integer quantitySizeM, @RequestParam Integer quantitySizeL, @RequestParam Integer quantitySizeXL) {
        this.clothesService.create(name, description, image, image1, image2, image3, price, grade, quantitySizeS, quantitySizeM, quantitySizeL, quantitySizeXL);
        return "redirect:/products";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam String name, @RequestParam String description, @RequestParam String image, @RequestParam String image1, @RequestParam String image2,
                         @RequestParam String image3, @RequestParam Double price, @RequestParam Double grade, @RequestParam Integer quantitySizeS,
                         @RequestParam Integer quantitySizeM, @RequestParam Integer quantitySizeL, @RequestParam Integer quantitySizeXL) {
        this.clothesService.update(id, name, description, image, image1, image2, image3, price, grade, quantitySizeS, quantitySizeM, quantitySizeL, quantitySizeXL);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.clothesService.delete(id);
        return "redirect:/products";
    }

    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable Long id, @RequestParam String comment, Model model, HttpServletRequest req){

        String username = req.getRemoteUser();
        Clothes clothes = this.clothesService.findById(id);
        User user = (User) this.userService.loadUserByUsername(username);
        this.clothesCommentService.addNewComment(user, clothes, comment);

        return "redirect:/products/" + id;
    }

    @PostMapping("/{id}/grade")
    public String gradeProduct(@PathVariable Long id, @RequestParam double grade, Model model, HttpServletRequest req){

        String username = req.getRemoteUser();
        Clothes clothes = this.clothesService.findById(id);
        User user = (User) this.userService.loadUserByUsername(username);
        ClothesGrade cg = this.clothesGradeService.findByUserAndClothes(user, clothes);

        if(cg != null)
            this.clothesGradeService.updateGrade(cg, grade);
        else
            this.clothesGradeService.addGrade(user, clothes, grade);
        this.clothesService.updateFinalGrade(clothes, this.clothesGradeService.getGradesByClothes(clothes));

        return "redirect:/products/" + id;

    }

    @PostMapping("/{id}/commentLike")
    public String likeComment(@PathVariable Long id, HttpServletRequest req){

        String username = req.getRemoteUser();
        User user = (User) this.userService.loadUserByUsername(username);
        ClothesComment clothesComment = this.clothesCommentService.findById(id);
        Optional<CommentUserLike> commentUserLike =  this.commentUserLikeService.findByUerAndComment(user, clothesComment);
        this.commentUserLikeService.createOrUpdate(1, 0, user, clothesComment, commentUserLike);
        this.clothesCommentService.updateLikes(this.commentUserLikeService.getTotalLikesFromComment(clothesComment),
                this.commentUserLikeService.getTotalDislikesFromComment(clothesComment), clothesComment);
        System.out.println("TUKAAAAAAAAAAAAAAA");

        return "redirect:/products/" + clothesComment.getClothes().getId();
    }

    @PostMapping("/{id}/commentDislike")
    public String dislikeComment(@PathVariable Long id, HttpServletRequest req){

        String username = req.getRemoteUser();
        User user = (User) this.userService.loadUserByUsername(username);
        ClothesComment clothesComment = this.clothesCommentService.findById(id);
        Optional<CommentUserLike> commentUserLike =  this.commentUserLikeService.findByUerAndComment(user, clothesComment);
        this.commentUserLikeService.createOrUpdate(0, 1, user, clothesComment, commentUserLike);
        this.clothesCommentService.updateLikes(this.commentUserLikeService.getTotalLikesFromComment(clothesComment),
                this.commentUserLikeService.getTotalDislikesFromComment(clothesComment), clothesComment);
        System.out.println("TUKAAAAAAAAAAAAAAA");

        return "redirect:/products/" + clothesComment.getClothes().getId();
    }
}
