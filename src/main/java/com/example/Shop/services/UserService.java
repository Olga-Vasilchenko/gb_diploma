package com.example.Shop.services;

import com.example.Shop.dto.Registration;
import com.example.Shop.models.enums.Role;
import com.example.Shop.repositories.UserRepository;
import com.example.Shop.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



// для регистрации пользователей в базу данных
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // инжекция UserRepository
    private final PasswordEncoder passwordEncoder;

    /**
     * createUser - метод для создания и сохранения нового пользователя
     *
     * @param user // если такой пользователь уже существует, то возвращаем false
     * @return
     */
    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null)
            return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // шифруем пароль
        user.getRoles().add(Role.ROLE_USER); // даем базовую роль пользователя
        userRepository.save(user);
        log.info("Сохранение нового пользователя по email: {}", email);
        return true;
    }
    // и сохранения нового
//    public void saveUser(Registration registration){
//        User user = new User();
//        user.setName(registration.getName());
//        user.setEmail(registration.getEmail());
//        user.setPassword(passwordEncoder.encode(registration.getPassword()));
//        userRepository.save(user);
//    }

}
