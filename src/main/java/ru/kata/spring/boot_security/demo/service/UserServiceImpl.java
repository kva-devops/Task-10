package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderById();
    }

    @Transactional
    @Override
    public void createOrUpdateUser(User user) {
        if (user.getId() == null) {
            createUser(user);
        } else {
            updateUser(user);
        }
    }

    private void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private void updateUser(User user) {
        Optional<User> userFromDB = userRepository.findById(user.getId());
        if (user.getAuthorities() == null) {
            user.setRoleSet(userFromDB.get().getRoleSet());
        }
        if (user.getPassword().equals("")) {
            user.setPassword(userFromDB.get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setEnabled(user.isEnabled());
        userRepository.save(user);
    }
}
