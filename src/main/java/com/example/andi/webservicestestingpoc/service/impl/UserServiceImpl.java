package com.example.andi.webservicestestingpoc.service.impl;

import com.example.andi.webservicestestingpoc.exceptions.UserServiceException;
import com.example.andi.webservicestestingpoc.io.entity.UserEntity;
import com.example.andi.webservicestestingpoc.io.repository.UserRepository;
import com.example.andi.webservicestestingpoc.service.UserService;
import com.example.andi.webservicestestingpoc.shared.Utils;
import com.example.andi.webservicestestingpoc.shared.dto.AddressDto;
import com.example.andi.webservicestestingpoc.shared.dto.UserDto;
import com.example.andi.webservicestestingpoc.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        for (int i = 0; i < user.getAddresses().size(); i++) {
            AddressDto address = user.getAddresses().get(i);
            address.setUserDetails(user);
            address.setAddressId(utils.generateUUID());
            user.getAddresses().set(i, address);
        }

        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(utils.generateUUID());

        UserEntity storedUser = userRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(storedUser, UserDto.class);

        return returnValue;
    }

    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity.getEmail() == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity =  userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    public UserDto getUserByUserId(String userId){

        if(userRepository.findByUserId(userId) == null) throw new UsernameNotFoundException(userId);

        UserEntity userEntity = userRepository.findByUserId(userId);
        UserDto returnValue = new UserDto();

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {

        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());


        UserEntity updatedUser = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity =  userRepository.findByUserId(userId);
        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {

        if(page > 0) page -=1;

        List<UserDto> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage= userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for(UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }
}
