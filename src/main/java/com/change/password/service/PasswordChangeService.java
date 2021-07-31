package com.change.password.service;

import com.change.password.entity.Response;
import com.change.password.entity.UserEntity;
import com.change.password.repository.UserDetailsRepository;
import com.change.password.utility.PatternMatchingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeService {

    /* Created by suditi on 2021-08-01 */
    public final UserDetailsRepository userDetailsRepository;
    public final PasswordVerificationService passwordVerificationService;
    public final PatternMatchingUtility patternMatchingUtility;

    @Autowired
    public PasswordChangeService(UserDetailsRepository userDetailsRepository,
                                 PasswordVerificationService passwordVerificationService,
                                 PatternMatchingUtility patternMatchingUtility) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordVerificationService = passwordVerificationService;
        this.patternMatchingUtility = patternMatchingUtility;
    }

    public Response getExistingPassword(String emailAddress){
        Response response = new Response();
        if(emailAddress!=null && !emailAddress.isEmpty()) {
            UserEntity userEntity =  userDetailsRepository.findByEmail(emailAddress);
            if(userEntity!=null && userEntity.getId()!=null) {
                response.setMessage("success");
                response.setData(userEntity.getPassword());
            }else{
                response.setMessage("Wrong Email address!!");
            }
        }else{
            response.setMessage("Email address is mandatory!!");
        }
        response.setCode(HttpStatus.OK.value());
        return response;
    }

    public Response changePassword(String emailAddress, String oldPassword, String newPassword){
        Response response = new Response();
        if(oldPassword!=null && !oldPassword.isEmpty()) {
            // fetch system password
            response = getExistingPassword(emailAddress);
            if(response.getMessage().equalsIgnoreCase("success")){

                // match old password with system
                if(oldPassword.equals(response.getData())) {

                    // validate new password
                    if (newPassword != null && !newPassword.isEmpty()) {

                        // check newpassword should be < 80% match with old
                        int perc = patternMatchingUtility.comparePasswordMatchPercentage(oldPassword,newPassword);
                        if(perc<80) {

                            // check password new strength
                            response = passwordVerificationService.validateIncomingPassword(newPassword);
                            if (response.getMessage().equalsIgnoreCase("success")) {

                                // update new password
                                int i = userDetailsRepository.updateUserPassword(newPassword, emailAddress);
                                if (i > 0) {
                                    response.setMessage("Password updated Successfuly :)");
                                } else {
                                    response.setMessage("Password NOT updated :(");
                                }
                            }
                        }else{
                            response.setMessage("New Password is more than 80% similar to the Old Password!");
                        }
                    } else {
                        response.setMessage("NEW Password is mandatory!!");
                    }
                }else {
                    response.setMessage("Old Password didn't match with system!!");
                }
            }
        }else{
            response.setMessage("Old Password is mandatory!!");
        }
        response.setCode(HttpStatus.OK.value());
        response.setData("");
        return response;
    }
}
