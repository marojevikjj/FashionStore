package onlineshopapp.fashionstore.web.controller;


import onlineshopapp.fashionstore.model.*;
import onlineshopapp.fashionstore.service.*;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.service.ClothesService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.persistence.GeneratedValue;
import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.events.Comment;
import java.util.*;


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

    @GetMapping
    public String showProducts(Model model) {
         return listByPage( null, 1, model, "name", "asc");
    }

    @GetMapping("page/{pageNumber}")
    public String listByPage(@RequestParam(required = false) String error,
                             @PathVariable("pageNumber") int currentPage, Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir){

            Page<Clothes> page = this.clothesService.findAll(currentPage, sortField, sortDir);

            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            List<Clothes> listProducts = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("products", listProducts);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);

        if(error != null)
            model.addAttribute("error", error);
        return "products";

    }

    @GetMapping("/searchProducts")
    public String searchProducts(@RequestParam(required = false) String nameSearch, Model model){

        int currentPage = 1;
        String sortField = "name";
        String sortDir = "asc";
        int totalPages = 1;
        List<Clothes> listProducts = this.clothesService.listProductsByName(nameSearch);
        int totalItems = listProducts.size();

        model.addAttribute("totalItems", totalItems);
        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return "products";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model, HttpServletRequest req) {

        String username = req.getRemoteUser();
        Clothes clothes = this.clothesService.findById(id);
        User user = (User) this.userService.loadUserByUsername(username);

        model.addAttribute("comments", this.clothesCommentService.findCommentsByProduct(id));
        if(this.clothesGradeService.findByUserAndClothes(user, clothes) != null)
            model.addAttribute("grade", this.clothesGradeService.findByUserAndClothes(user, clothes).getGrade());
        else
            model.addAttribute("grade", null);

        model.addAttribute("product", this.clothesService.findById(id));

        List<Clothes> prod = this.clothesService.listAll();
        List<Clothes> produkti = this.getClothes(prod, 4);
        Collections.shuffle(prod);

        model.addAttribute("produkti", produkti);
        return "product-details";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String showAdd() {
        return "form";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("product", this.clothesService.findById(id));
        return "form";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public String create(@RequestParam String name, @RequestParam String description, @RequestParam String image, @RequestParam String image1, @RequestParam String image2,
                         @RequestParam String image3, @RequestParam Double price, @RequestParam Double grade, @RequestParam Integer quantitySizeS,
                         @RequestParam Integer quantitySizeM, @RequestParam Integer quantitySizeL, @RequestParam Integer quantitySizeXL) {
        this.clothesService.create(name, description, image, image1, image2, image3, price, grade, quantitySizeS, quantitySizeM, quantitySizeL, quantitySizeXL);
        return "redirect:/products";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam String name, @RequestParam String description, @RequestParam String image, @RequestParam String image1, @RequestParam String image2,
                         @RequestParam String image3, @RequestParam Double price, @RequestParam Double grade, @RequestParam Integer quantitySizeS,
                         @RequestParam Integer quantitySizeM, @RequestParam Integer quantitySizeL, @RequestParam Integer quantitySizeXL) {
        this.clothesService.update(id, name, description, image, image1, image2, image3, price, grade, quantitySizeS, quantitySizeM, quantitySizeL, quantitySizeXL);
        return "redirect:/products";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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

        if(cg != null && (grade >= 0.0 && grade <= 5.0))
            this.clothesGradeService.updateGrade(cg, grade);
        else if(grade >= 0.0 && grade <= 5.0) {
            this.clothesGradeService.addGrade(user, clothes, grade);
            this.clothesService.updateFinalGrade(clothes, this.clothesGradeService.getGradesByClothes(clothes));
        }

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

        return "redirect:/products/" + clothesComment.getClothes().getId();
    }

    private List<Clothes> getClothes(List<Clothes> c, int n){
        int tmp = 0;
        List<Clothes> listOfClothes = new ArrayList<>();
        for(Clothes cl : c){
            listOfClothes.add(cl);
            if(tmp == n-1)
                break;
            tmp++;
        }
        return listOfClothes;
    }


    private List<ClothesComment> getLast(List<ClothesComment> comments){
        List<ClothesComment> latestComments = new ArrayList<>();
        Collections.reverse(comments);
        int temp = 0;
        for(ClothesComment c : comments){
            latestComments.add(c);
            if(temp == 2)
                break;
            temp++;
        }
        Collections.reverse(latestComments);
        return latestComments;
    }
}
