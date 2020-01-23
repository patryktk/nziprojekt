package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    //to klasa serwisowa która zarządza encją user w bazie danych, tj tworzy nowe, pobiera, usuwa itd. to ona właśnie wywołuje wysłanie emaila itp.
    // starałem się robić tak żeby metody zwracały tylko true lub false
    //tak zeby w controllerach bylo jak najmniej kodu, a wszystkie operacje logiczne byly wlasnie w serwisach

    final private String PATTERN = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#!$%^&+=])\\S{6,}\\z";

    private UserRepo userRepo;
    private EmailServiceImpl emailService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, EmailServiceImpl emailService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public boolean saveUser(User newUser) {
        if (newUser.getPassword().matches(PATTERN) && userRepo.findByEmail(newUser.getEmail()) == null && userRepo.findByUsername(newUser.getUsername()) == null) {
            newUser.setConfirmationToken(UUID.randomUUID().toString());
            emailService.sendConfirmationEmail(newUser.getEmail(), newUser.getConfirmationToken());
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepo.save(newUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long id, String password) {
        if (!userRepo.findById(id).get().getRole().equals("ROLE_ADMIN") && userRepo.findById(id).isPresent() && userRepo.findById(id).get().getPassword().equals(passwordEncoder.encode(password))) {
            userRepo.deleteById(id);
            return true;
        } else
            return false;
    }

    @Override
    public boolean checkConfirmation(String token) {
        User user = userRepo.findByConfirmationToken(token);
        if (user != null && !user.isEnabled()) {
            user.setEnabled(true);
            userRepo.save(user);
            return true;
        } else
            return false;
    }

    //ta metoda mozna zmienic role usera z usera na admina jakby sie mialo kiedys przydac
    @Override
    public boolean changeRole(Long id) {
        User user = userRepo.findById(id).get();
        if (userRepo.findById(id).isPresent() && user.getRole().equals("ROLE_USER")) {
            user.setRole("ROLE_ADMIN");
            userRepo.save(user);
            return true;
        } else
            return false;
    }

    //nie testowałem ale powinno działać, jak nie działa to pewnie zagmatwałem coś w logice i można szybko poprawić
    @Override
    public boolean changePassword(Long id, String newPassword, String newPasswordConfirm, String oldPassword) {
        User user = userRepo.getOne(id);
        if (newPassword.matches(PATTERN) && user.getPassword().equals(passwordEncoder.encode(oldPassword)) && newPassword.equals(newPasswordConfirm)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
            return true;
        } else
            return false;
    }

    //nie testowałem ale powinno działać, jak nie działa to pewnie zagmatwałem coś w logice i można szybko poprawić
    @Override
    public boolean changeEmail(Long id, String newEmail, String newEmailConfirm, String password) {
        User user = userRepo.getOne(id);
        if (user.getPassword().equals(passwordEncoder.encode(password)) && newEmail.equals(newEmailConfirm)) {
            user.setEmail(newEmail);
            userRepo.save(user);
            return true;
        } else
            return false;
    }
}
