package org.shop.com.service;

import lombok.RequiredArgsConstructor;
import org.shop.com.converter.UserDtoConverter;
import org.shop.com.dto.UserCreateDto;
import org.shop.com.dto.UserDto;
import org.shop.com.entity.UserEntity;
import org.shop.com.exceptions.UserNotFoundException;
import org.shop.com.mapper.UserMapper;
import org.shop.com.repository.UserJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository repository;
//    private final UserDtoConverter converter;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserJpaRepository repository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity create(UserCreateDto createDto) {
        String hashedPassword = passwordEncoder.encode(createDto.getPasswordHash());
        UserDto userDto = new UserDto();
        userDto.setName(createDto.getName());
        userDto.setEmail(createDto.getEmail());
        userDto.setPhoneNumber(createDto.getPhoneNumber());
        userDto.setPasswordHash(hashedPassword);
        userDto.setRole(createDto.getRole());
        UserEntity savedUserEntity = repository.save(userMapper.toEntity(userDto));
        return savedUserEntity;
    }

    @Override
    public UserEntity findById(long id) {
        return repository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("There is no users with id "
                        + id));
    }

    @Override
    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public UserEntity editUser(long id, UserCreateDto userDto) {
        return repository.findById(id).map(user -> {
            user.setName(userDto.getName());
            user.setPhoneNumber(userDto.getPhoneNumber());
            return repository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void delete(long id) {
        if (findById(id) == null){
            throw new UserNotFoundException("There is no users with id "
                    + id);
        }
       repository.deleteById(id);
    }

}