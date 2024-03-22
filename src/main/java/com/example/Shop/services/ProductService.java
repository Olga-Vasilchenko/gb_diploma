package com.example.Shop.services;

import com.example.Shop.models.User;
import com.example.Shop.repositories.ProductRepository;
import com.example.Shop.models.Image;
import com.example.Shop.models.Product;
import com.example.Shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


/**
 * ProductService - служебный класс, реализующий методы работы с репозиторием
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

//    private List<Product> products = new ArrayList<>(); // создаем список товаров
//    private long ID = 0;
//
//    {
//        products.add(new Product(++ID, "Samsung", "S5", 22500, "Москва", "Андрей Либерман"));
//        products.add(new Product(++ID, "Nokia", "NokiaA4", 28400, "Москва", "Сергей Андреев"));
//    }

    /**
     * listProducts - метод получения всех продуктов
     */
    public List<Product> listProducts(String title) {
        if(title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    /**
     * saveProduct - метод добавления продукта
     * @param product
     */
    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;

        // если фотография имеется, то image использует метод toImageEntity, преобразовывая из MultipartFile в фотографию
        if(file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }

        if(file2.getSize() != 0) {
            image2 = toImageEntity(file2);
//          image2.setPreviewImage(false);
            product.addImageToProduct(image2);
        }

        if(file3.getSize() != 0) {
            image3 = toImageEntity(file3);
//          image3.setPreviewImage(false);
            product.addImageToProduct(image3);
        }

        log.info("Сохранение нового продукта. Product: Title: {}; Author email: {}", product.getUser().getEmail());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    /**
     * метод toImageEntity
     * @param file - принимаем файл, назначаем имя из файла и т.д.
     * @return возвращаем фото
     * @throws IOException - ошибка
     */

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    /**
     * deleteProduct - метод удаления продукта по ID
     * @param id
     */

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
//        products.removeIf(product -> product.getId() == id); // products.removeIf(product -> product.getId().equals(id));

    }

    /**
     * getProductByID - метод получения товара по ID
     * @param id
     * @return product - если нашли товар, null - если не нашли товар с таким ID
     */
    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}