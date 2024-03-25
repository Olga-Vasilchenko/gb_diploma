package com.example.Shop.services;

import com.example.Shop.dto.Registration;
import com.example.Shop.models.enums.Role;

import com.example.Shop.repositories.UserRepository;
import com.example.Shop.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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
//        String user1 = user.getEmail();
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null)
            return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // шифруем пароль
        user.getRoles().add(Role.ROLE_USER); // даем базовую роль пользователя
        log.info("Сохранение нового пользователя по email: {}", email);
        userRepository.save(user); // сохраняем пользователя
        return true;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

//    public void saveUser(Registration registration){
//        User user = new User();
//        user.setName(registration.getName());
//        user.setEmail(registration.getEmail());
//        user.setPassword(passwordEncoder.encode(registration.getPassword()));
//        userRepository.save(user);
//    }

//    public User findByEmail(String email){
//        return userRepository.findByEmail(email);
//    }
//
//    public User findByUsername(String username){
//        return userRepository.findByUsername(username);
//    }
    // находим пользователя, если он активен
    public void banUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            if (user.isActive()){
                user.setActive(false);
                log.info("Заблокировать пользователя с ID = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Разблокировать пользователя с ID = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }
    //

    public void changeUserRoles(User user, Map<String, String> form){
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
    public User getUserByPrincipal(Principal principal){
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }
}
