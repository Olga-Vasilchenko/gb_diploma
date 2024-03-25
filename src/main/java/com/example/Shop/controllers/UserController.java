package com.example.Shop.controllers;

import com.example.Shop.models.User;
import com.example.Shop.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Аутентификация пользователя (процесс проверки подлинности пользователя,
     * чтобы убедиться, что он является тем, за кого себя выдает - логин и пароль)
     *
     * @return представление аутентификации
     */
    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    /**
     * Регистрация пользователя (неразделима без авторизации, то есть
     * регистрация — это способ получить возможность войти на сайт)
     *
     * @return представление регистрации
     */

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    /**
     * Добавление нового пользователя

     * @param user - новый пользователь
     * @return "registration" - возвращаем снова на регистрацию
     * @return "redirect:/login" - добавляем пользователя
     */


    @PostMapping("/registration")
    public String createUser(User user, Model model) {
//        log.info("createUser");
//        userService.createUser(user);
//        return "redirect:/login";
        if (!userService.createUser(user)) {
            model.addAttribute("error",
                    "Пользователь с таким email: " +
                            user.getEmail() + "уже существует, попробуйте еще раз");
            return "registration";
        }
        return "redirect:/login";
    }

    /**
     * подробная информация о пользователе
     * @param user - передаем пользователя
     * @param model
     * @return
     */
    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal){
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getProducts());
        return "user-info";

    }
}
//
//    /**
//     * /welcome - основная веб-страница, которую посетитель будет попадать после регистрации
//     * при переходе на веб-сайт с помощью поисковой системы
//     * @return основная веб-страница
//     */
//    @GetMapping("/welcome")
//    public String securityUrl(){
//        return "welcome";
//    }

