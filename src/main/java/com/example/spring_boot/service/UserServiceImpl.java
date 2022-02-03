package com.example.spring_boot.service;
import com.example.spring_boot.dao.UserRepository;
import com.example.spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        return userRepository.getById(id);
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        User userDB = userRepository.findById(id).get();
        if(Objects.nonNull(updatedUser.getUsername())) {
            userDB.setName(updatedUser.getName());
        }
        if(Objects.nonNull(updatedUser.getLastName())) {
            userDB.setLastName(updatedUser.getLastName());
        }
        userDB.setAge(updatedUser.getAge());
        userDB.setLastName(updatedUser.getLastName());
        userDB.setPassword(updatedUser.getPassword());
        userDB.setRoles(updatedUser.getRoles());
        userRepository.save(updatedUser);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByName(String name) {
       return userRepository.findByName(name);
    }
}
