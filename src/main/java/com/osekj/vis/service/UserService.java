package com.osekj.vis.service;

import com.osekj.vis.dto.UserCreateDto;
import com.osekj.vis.dto.UserEditDto;
import com.osekj.vis.dto.UserResponseDto;
import com.osekj.vis.model.entity.User;
import com.osekj.vis.model.repository.RoleRepository;
import com.osekj.vis.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user.getUserId(), user.getUsername(), user.getRole().getRoleName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto addUser(UserCreateDto userDto) {
        checkIfUsernameExists(userDto.getUsername());
        User savedUsed =
                userRepository.save(new User(userDto.getUsername(),passwordEncoder.encode(userDto.getPassword()),
                        roleRepository.getOne(userDto.getRoleId())));
        return new UserResponseDto(savedUsed.getUserId(), savedUsed.getUsername(), savedUsed.getRole().getRoleName());
    }

    @Transactional
    public UserResponseDto editUser(Long id, UserEditDto userEditDto) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        checkIfUsernameExists(userEditDto.getUsername());
        user.setUsername(userEditDto.getUsername());
        user.setRole(roleRepository.getOne(userEditDto.getRoleId()));
        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getRole().getRoleName());
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private void checkIfUsernameExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException();
        }
    }
}
