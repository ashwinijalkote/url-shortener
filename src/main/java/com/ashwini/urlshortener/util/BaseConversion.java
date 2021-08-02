package com.ashwini.urlshortener.util;

public class BaseConversion {

    private static final String base62AllowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static  final int BASE_62_INT = 62;

    public static String idToShortString(Long id) {
        return base10ToBase62(id);
    }
    
    public static Long shortStringToId(String shortString) {
        return base62ToBase10(shortString);
    }

    private static Long base62ToBase10(String shortString) {
        var inputChars = shortString.toCharArray();
        var len = inputChars.length;
        var output = 0L;
        for(int i=0; i< len; i++) {
            output += base62AllowedChars.indexOf(inputChars[i]) * Math.pow(BASE_62_INT, len -(i+1));
        }
        return output;
    }

    private static String base10ToBase62(Long id) {
        var output = new StringBuilder();

        while(id > 0) {
            output.append(base62AllowedChars.charAt((int)(id% BASE_62_INT)));
            id = id/62;
        }
        return output.reverse().toString();
    }
}
