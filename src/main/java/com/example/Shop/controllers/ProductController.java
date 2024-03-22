package com.example.Shop.controllers;

import com.example.Shop.models.Product;
import com.example.Shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * @GetMapping("/products") - обращение на главную страницу сайта
     * @param model - передаем список всех товаров в шаблонизатор
     * @return - возвращаем весь список товаров
     */

    // required = false - данный параметр не обязателен
    // @RequestParam(name = "title") - организовываем поиск по параметру
    @GetMapping("/products")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model) {
        model.addAttribute("products", productService.listProducts(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }


    //метод для просмотра подробной информации о каждом товаре

    @GetMapping("product-info/{id}")
    public String infoProduct(@PathVariable Long id, Model model){
        Product product = productService.getProductByID(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }

    /**
     * @PostMapping("/product/create") - метод добавления продукта
     * @param product
     * @return
     */

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/products"; // обновляем страницу с добавлением нового товара
    }

    /**
     * @PostMapping("/product/delete/{id}") - метод удаления продукта по ID
     * @param id
     * @return
     */

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}