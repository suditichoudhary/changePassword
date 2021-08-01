package com.change.password.controller;

import com.change.password.entity.Response;
import com.change.password.service.PasswordChangeService;
import com.change.password.service.PasswordVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    /* Created by suditi on 2021-07-30 */
    private final PasswordVerificationService passwordVerificationService;
    private final PasswordChangeService passwordChangeService;

    @Autowired
    public PasswordController(PasswordVerificationService passwordVerificationService,
                              PasswordChangeService passwordChangeService) {
        this.passwordVerificationService = passwordVerificationService;
        this.passwordChangeService = passwordChangeService;
    }

    @GetMapping("/v1/verify-password")
    public ResponseEntity<?> verifyNewPassword(@RequestParam(defaultValue = "") String password) {
        Response response = passwordVerificationService.validateIncomingPassword(password);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/v1/get-password")
    public ResponseEntity<?> getExistingPassword(@RequestParam(defaultValue = "") String emailAddress) {
        Response response = passwordChangeService.getExistingPassword(emailAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/v1/change-password")
    public ResponseEntity<?> updateExistingPassword(@RequestParam(defaultValue = "") String emailAddress,
                                                    @RequestParam(defaultValue = "") String oldPassword,
                                                    @RequestParam(defaultValue = "") String newPassword) {
        Response response = passwordChangeService.changePassword(emailAddress,oldPassword,newPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
