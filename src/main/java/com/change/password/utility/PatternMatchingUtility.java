package com.change.password.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PatternMatchingUtility {
    private static final Logger logger = LoggerFactory.getLogger(PatternMatchingUtility.class);

    /* Created by suditi on 2021-07-30 */

    public static final String SPECIAL_CHARACTER ="[!@#$&*]";
    public static final String NUMERIC ="[0-9]";
    public static final String LOWER_CASE ="[a-z]";
    public static final String UPPER_CASE ="[A-Z]";
    public static final String ALL_POSSIBLE_CHARS ="[A-Z][a-z][0-9][!@#$&*]";

    public static int numOfMatchesInPwd(String patternFor,String password){
        Matcher countOverlapMatcher = Pattern.compile(patternFor).matcher(password);
        int count = 0;
        while (countOverlapMatcher.find()) {
            count++;
        }
        return count;
    }
    public boolean duplicateCharCount(String password,int maxDuplicate)
    {
        int count;
        char charArray[] = password.toCharArray();

        logger.debug("Looking for max duplicate characters in pwd ");
        for(int i = 0; i <charArray.length; i++) {
            count = 1;
            for(int j = i+1; j <charArray.length; j++) {
                if(charArray[i] == charArray[j] && charArray[i] != ' ') {
                    count++;
                }
            }
            if(count > maxDuplicate)
                return true;

        }
        return false;
    }

    public static int comparePasswordMatchPercentage(String oldPass, String newPass) {
        int per = 0;

        int actualMAtch = 0;
        int exactMatch = 0;
        actualMAtch += matchTwoString(oldPass, newPass);
        exactMatch += matchTwoString(oldPass, oldPass);

        per = (int) Math.round(actualMAtch / (exactMatch / 100.0));
        System.out.println("New Password match % : "+per);
        return per;
    }

    private static int matchTwoString(String oldPass, String newPass) {
        char[] oChars = oldPass.toCharArray();
        char[] nChars = newPass.toCharArray();
        int matched = 0;
        for (int i = 0; i < oChars.length; i++) {
            char c = oChars[i];
            if (i < nChars.length) {
                if (nChars[i] == c) {
                    matched++;
                }
            }
        }
        return matched;
    }
}
