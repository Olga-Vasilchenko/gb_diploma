package com.example.Shop.controllers;

import com.example.Shop.models.User;
import com.example.Shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    @GetMapping("/log")
    public String log() {
        return "log";
    }

    /**
     * Регистрация пользователя (неразделима без авторизации, то есть
     * регистрация — это способ получить возможность войти на сайт)
     *
     * @return представление регистрации
     */

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * Принимаем нового пользователя и сохраняем его
     *
     * @param user - новый пользователь
     * @return "registration" - возвращаем снова на регистрацию
     * @return "redirect:/log" - добавляем пользователя
     */

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
//        userService.createUser(user);
//        return "redirect:/log";

        if (!userService.createUser(user)) {
            model.addAttribute("error",
                    "Пользователь с таким email: " +
                            user.getEmail() + "уже существует, попробуйте еще раз");
            return "registration";
        }
        return "redirect:/log";
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

