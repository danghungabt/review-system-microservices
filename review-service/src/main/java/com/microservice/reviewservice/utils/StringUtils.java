package com.microservice.reviewservice.utils;

public class StringUtils {
    public static String customSubstring(String oldString, int numberOfWords){
        int wordCount = 0;
        int index = 0;

        while (wordCount < numberOfWords && index < oldString.length()) {
            if (oldString.charAt(index) == ' ') {
                wordCount++;
            }
            index++;
        }

        return oldString.substring(0, index);
    }
}
