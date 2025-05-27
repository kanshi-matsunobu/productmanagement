package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Admin;
import com.example.demo.entity.CategoryLarge;
import com.example.demo.entity.CategoryMiddle;
import com.example.demo.entity.CategorySmall;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOrder;
import com.example.demo.service.AdminService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductOrderService;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductOrderService productOrderService;
    
    @Autowired
    private AdminService adminService;

    @GetMapping
    public String listProducts(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "largeCategoryId", required = false) Integer largeCategoryId,
            @RequestParam(name = "middleCategoryId", required = false) Integer middleCategoryId,
            @RequestParam(name = "smallCategoryId", required = false) Integer smallCategoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model
    ) {
        // ページサイズ固定（1ページに5件）
        int pageSize = 5;

        // 検索結果ページ取得
        Page<Product> productPage = productService.searchProducts(
                name, largeCategoryId, middleCategoryId, smallCategoryId, PageRequest.of(page, pageSize)
        );

        // カテゴリリスト取得（セレクトボックス用）
        List<CategoryLarge> largeCategories = categoryService.getAllLargeCategories();
        List<CategoryMiddle> middleCategories = categoryService.getAllMiddleCategories();
        List<CategorySmall> smallCategories = categoryService.getAllSmallCategories();

        // 画面に渡す値をセット
        model.addAttribute("productPage", productPage);
        model.addAttribute("name", name);
        model.addAttribute("largeCategoryId", largeCategoryId);
        model.addAttribute("middleCategoryId", middleCategoryId);
        model.addAttribute("smallCategoryId", smallCategoryId);
        model.addAttribute("largeCategories", largeCategories);
        model.addAttribute("middleCategories", middleCategories);
        model.addAttribute("smallCategories", smallCategories);

        return "product_list";
    }
    
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product_detail";
    }
    
    //発注画面表示
    @GetMapping("/order/{id}")
    public String showOrderForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "指定された商品が見つかりません。");
            return "redirect:/product";
        }
        model.addAttribute("product", product);
        return "order_form"; // order画面のHTML名に合わせて
    }
    //発注処理

    @PostMapping("/order/{id}")
    public String processOrder(
            @PathVariable("id") Integer id,
            @RequestParam("quantity") Integer quantity,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        // ログイン中のメールアドレスを取得
        String email = principal.getName(); // これでOK

        Admin admin = adminService.findByEmail(email);
        Product product = productService.findById(id);

        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "商品が存在しません。");
            return "redirect:/product";
        }

        // 保存処理
        productOrderService.saveOrder(product, quantity, admin.getId(), admin.getStore().getId());

        redirectAttributes.addFlashAttribute("successMessage", "商品「" + product.getName() + "」を " + quantity + " 件発注しました。");
        return "redirect:/product/detail/" + id;
    }
    
    //発注履歴表示
    @GetMapping("/order/history")
    public String showOrderHistory(Model model,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            RedirectAttributes redirectAttributes) {

        // ★ ログイン状態を確認（null 対策）
        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/admin/login";
        }

        String email = userDetails.getUsername();
        Admin admin = adminService.findByEmail(email);
        List<ProductOrder> orderList = productOrderService.getOrdersByAdminId(admin.getId());

        model.addAttribute("orders", orderList);
        return "order_history";
    }
}