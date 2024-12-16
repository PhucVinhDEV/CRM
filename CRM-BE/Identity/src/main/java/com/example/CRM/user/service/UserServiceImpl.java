package com.example.CRM.user.service;

import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.user.mapper.UserMapper;
import com.example.CRM.user.model.User;
import com.example.CRM.user.model.record.UserRecord;
import com.example.CRM.user.model.reponsese.UserDTO;
import com.example.CRM.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final  PasswordEncoder passwordEncoder;

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public GenericMapper<UserRecord, User, UserDTO> getMapper() {
        return userMapper;
    }


    @Override
    public UserDTO save(UserRecord record) {
        User user = userMapper.maptoEntity(record);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.maptoDto(userRepository.save(user));
    }
}
