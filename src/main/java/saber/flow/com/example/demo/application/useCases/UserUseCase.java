package saber.flow.com.example.demo.application.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.User;
import saber.flow.com.example.demo.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        String password = user.getPassword();
        String encodedPassword = password != null && password.startsWith("$2")
                ? password
                : passwordEncoder.encode(password);

        User userToSave = new User(user.getId(), user.getName(), user.getEmail(), encodedPassword);
        return userRepository.save(userToSave);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}