package com.example.andi.webservicestestingpoc.ui.controller;

import com.example.andi.webservicestestingpoc.ui.model.request.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeAuthenticationController {

    @ApiOperation(value = "${authenticationController.theFakeLogin.Value}",
                    notes = "${authenticationController.theFakeLogin.Notes}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                            message = "Response Headers",
                            responseHeaders = {
                                    @ResponseHeader(name = "authorization",
                                            description = "Bearer <JWT value here>",
                                            response = String.class),
                                    @ResponseHeader(name = "userId",
                                            description = "<Public User Id value here>",
                                            response = String.class),
                            })
    })
    @PostMapping("/login")
    public void theFakeLogin(@RequestBody LoginRequestModel loginRequest){

        throw new IllegalStateException("This method should not be called. This method should be implemented by Spring Security");
    }

}
