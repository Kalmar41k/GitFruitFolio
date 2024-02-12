package org.example.serverfruitfolio.Controller;

import org.example.serverfruitfolio.Service.UserService;
import org.example.serverfruitfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/byId/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        String password = user.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String salted = Base64.getEncoder().encodeToString(salt);
        user.setSalt(salted);

        String saltedPassword = password + salted;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedPassword.getBytes());
            String hashedPasword = Base64.getEncoder().encodeToString(hash);
            user.setPassword(hashedPasword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        return userService.createUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }
}
