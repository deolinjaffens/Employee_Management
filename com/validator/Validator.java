package com.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *Used to check wheather the details initialised are in
 *the expected form
 *
 *@author Deolin Jaffens
 */

public class Validator {

    /**
     *Checks wheater the number is a true phone number
     *
     *@param number - gets the number initialised
     */

    public static boolean isValidNumber(long number) {
        String numberToString = String.valueOf(number);
        Pattern pattern = Pattern.compile("[789]{1}[0-9]{9}");
        Matcher matcher = pattern.matcher(numberToString);
        return matcher.matches();
    }

   /**
    *Checks wheather the given name is true
    *
    *@param name - gets the name that is initialised
    */

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("[A-Z]{1}[a-z]+");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     *Checks wheather the given gender is true
     *
     *@param gender - get the gender that is initialized
     */

    public static boolean isValidGender(char gender) {
        String genderToString = String.valueOf(gender);
        Pattern pattern = Pattern.compile("[mfMF]");
        Matcher matcher = pattern.matcher(genderToString);
        return matcher.matches();
    }
}   