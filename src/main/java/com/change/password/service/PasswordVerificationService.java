package com.change.password.service;

import com.change.password.entity.Response;
import com.change.password.utility.PatternMatchingUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PasswordVerificationService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordVerificationService.class);

    /* Created by suditi on 2021-07-30 */

    PatternMatchingUtility patternMatchingUtility;

    @Autowired
    public PasswordVerificationService(PatternMatchingUtility patternMatchingUtility) {
        this.patternMatchingUtility = patternMatchingUtility;
    }


    public Response validateIncomingPassword(String password){

        Response response = new Response();
        if(password!=null && !password.isEmpty()){
            // check if 18 digit and has atleast 1 special character

            int specialCharCount = patternMatchingUtility.numOfMatchesInPwd(patternMatchingUtility.SPECIAL_CHARACTER,password);
            int numberCount = patternMatchingUtility.numOfMatchesInPwd(patternMatchingUtility.NUMERIC,password);


            if(password.length()<18){
                logger.debug("Password should be atleast 18 characters");
                response.setMessage("Password should be atleast 18 characters");
            }

            else if(!password.matches(patternMatchingUtility.ALL_POSSIBLE_CHARS)){
                logger.debug("Password can only contain : Alphabets/Number/Special Char !@#$&*");
                response.setMessage("Password can only contain : Alphabets/Numbers/Special Chars !@#$&*");
            }

            // check if atleast 1 special char
            else if(specialCharCount<1){
                logger.debug("Password must contain atleast 1 Special Char !@#$&*");
                response.setMessage("Password must contain atleast 1 Special Char !@#$&*");
            }

            else if((patternMatchingUtility.numOfMatchesInPwd(patternMatchingUtility.UPPER_CASE,password)<1)){
                logger.debug("Password must contain atleast 1 Upper Case");
                response.setMessage("Password must contain atleast 1 Upper Case");
            }

            else if((patternMatchingUtility.numOfMatchesInPwd(patternMatchingUtility.LOWER_CASE,password)<1)){
                logger.debug("Password must contain atleast 1 Lower Case");
                response.setMessage("Password must contain atleast 1 Lower Case");
            }

            else if(numberCount<1){
                logger.debug("Password must contain atleast 1 Number");
                response.setMessage("Password must contain atleast 1 Number");
            }

            else if(specialCharCount>4){
                logger.debug("Password CANNOT contain > 4 Special Char !@#$&*");
                response.setMessage("Password CANNOT contain > 4 Special Char !@#$&*");
            }

            else if(numberCount>password.length()/2){
                logger.debug("Password CANNOT contain > 50% Numbers");
                response.setMessage("Password CANNOT contain > 50% Numbers");
            }

            else if(patternMatchingUtility.duplicateCharCount(password,4)){
                logger.debug("Password CANNOT contain > 4 Duplicate Char");
                response.setMessage("Password CANNOT contain > 4 Duplicate Char");
            }

            else{
                response.setMessage("success");
            }

        }else{
            logger.debug("Password can't be empty!!!");
        }
        response.setCode(HttpStatus.OK.value());
        return response;

    }

}
