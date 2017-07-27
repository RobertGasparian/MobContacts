package com.example.user.mobcontacts.utilities;

/**
 * Created by User on 7/27/2017.
 */

public class ValidationUtility {
//    util




    public static boolean checkValidation(String name, String phone, int gender) {

        boolean result;
        if (name.isEmpty() || phone.isEmpty() || gender == 0) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
