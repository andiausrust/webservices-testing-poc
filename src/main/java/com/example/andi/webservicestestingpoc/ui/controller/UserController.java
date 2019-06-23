package com.example.andi.webservicestestingpoc.ui.controller;

import com.example.andi.webservicestestingpoc.service.AddressService;
import com.example.andi.webservicestestingpoc.service.UserService;
import com.example.andi.webservicestestingpoc.shared.dto.AddressDto;
import com.example.andi.webservicestestingpoc.shared.dto.UserDto;
import com.example.andi.webservicestestingpoc.ui.model.request.UserDetailsRequestModel;
import com.example.andi.webservicestestingpoc.ui.model.response.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @ApiOperation(value = "Get User Details Web Service Endpoint",
                    notes = "This Webservice Endpoint returns User Details. User public user id in URL Path. For example:/users/öaslkdfj8s9d")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")
    })
    @GetMapping(path = "/{id}",
                produces = {
                        MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE
                })
    public UserRest getUser(@PathVariable String id) {

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        UserRest returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

    @PutMapping(path = "/{userId}",
                produces = {
                        MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE
                },
                consumes = {
                        MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE
                    })
    public UserRest updateUser(@PathVariable String userId,
                             @RequestBody UserDetailsRequestModel requestModel) {

        UserDto updatedUser = new UserDto();
        BeanUtils.copyProperties(requestModel, updatedUser);

        UserDto userDto = userService.updateUser(userId, updatedUser);

        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")
    })
    @DeleteMapping(path = "/{userId}",
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE
                    })
    public OperationStatusModel deleteUser(@PathVariable String userId) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        userService.deleteUser(userId);

        return returnValue;
    }

    @ApiImplicitParams({
           @ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")
    })
    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
                    }
                )
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit){
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);

        for(UserDto userDto : users){
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }

        return returnValue;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")
    })
    @GetMapping(path = "/{userId}/addresses",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public List<AddressesRest> getAddresses(@PathVariable String userId) {
            List<AddressesRest> returnValue = new ArrayList<>();

            List<AddressDto> addressDtos = addressService.getAddresses(userId);

            if(addressDtos != null && !addressDtos.isEmpty()) {

                Type listType = new TypeToken<List<AddressesRest>>() {
                }.getType();
                returnValue = new ModelMapper().map(addressDtos, listType);
            }
            return returnValue;

    }

}
