package com.krieger.digital.userservice.service;

import com.krieger.digital.userservice.enums.Active;
import com.krieger.digital.userservice.enums.Role;
import com.krieger.digital.userservice.exc.NotFoundException;
import com.krieger.digital.userservice.model.User;
import com.krieger.digital.userservice.model.UserDetails;
import com.krieger.digital.userservice.repository.UserRepository;
import com.krieger.digital.userservice.request.RegisterRequest;
import com.krieger.digital.userservice.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public User saveUser(RegisterRequest request) {
        User toSave = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .active(Active.ACTIVE).build();
        return userRepository.save(toSave);
    }

    public List<User> getAll() {
        return userRepository.findAllByActive(Active.ACTIVE);
    }

    public User getUserById(String id) {
        return findUserById(id);
    }

    public User getUserByEmail(String email) {
        return findUserByEmail(email);
    }

    public User getUserByUsername(String username) {
        return findUserByUsername(username);
    }

    public User updateUserById(UserUpdateRequest request) {
        User toUpdate = findUserById(request.getId());

        request.setUserDetails(updateUserDetails(toUpdate.getUserDetails(), request.getUserDetails()));
        modelMapper.map(request, toUpdate);

        return userRepository.save(toUpdate);
    }

    public void deleteUserById(String id) {
        User toDelete = findUserById(id);
        toDelete.setActive(Active.INACTIVE);
        userRepository.save(toDelete);
    }

    protected User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private UserDetails updateUserDetails(UserDetails toUpdate, UserDetails request) {
        toUpdate = toUpdate == null ? new UserDetails() : toUpdate;
        modelMapper.map(request, toUpdate);

        return toUpdate;
    }
}