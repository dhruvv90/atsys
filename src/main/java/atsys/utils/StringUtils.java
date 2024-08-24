package atsys.utils;

public class StringUtils {

    private StringUtils(){}

    public static String getDirectionText(boolean isBuy){
        return isBuy? "buy":"sell";
    }
}
