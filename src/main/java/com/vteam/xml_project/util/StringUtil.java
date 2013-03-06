package com.vteam.xml_project.util;

import java.io.IOException;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.jackson.map.ObjectMapper;

public class StringUtil {

    public static <T> T jsonToObject(String json, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        T obj = null;
        if (validString(json)) {
            try {
                obj = mapper.readValue(json, classType);
            } catch (Exception a) {
                a.printStackTrace();
                return null;
            }
        }
        return obj;
    }

    public static <T> String objectToJSON(T obj) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, obj);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }

        return writer.toString();
    }

    public static boolean validString(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    public static String findNonDigitChars(String str) {
        if (str == null) {
            return null;
        }

        final Pattern p = Pattern.compile("[^0-9]");
        final Matcher m = p.matcher(str);

        boolean found = m.find();
        if (!found) {
            return null;
        } else {
            return m.group();
        }
    }

    public static boolean containsOnlyNumbers(String st) {

        if (st == null || st.length() == 0) {
            return false;
        }

        String str = (st.startsWith("+")) ? st.substring(1) : st;
        for (int i = 0; i < str.length(); i++) {

            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return (Map<String, Object>) new ObjectMapper().readValue(
                    jsonStr, HashMap.class);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }

    }

    public static String[] splitPhonNumber(String numbers) {
        String[] listNumber = numbers.split(",");
        Set<String> phoneNumbers = new HashSet<String>();
        for (String numb : listNumber) {
            if (!PhoneNumberUtil.validatePhoneNumber(numb, true)) {
                return null;
            }
            phoneNumbers.add(numb);
        }
        String[] returnNumbs = new String[phoneNumbers.size()];
        phoneNumbers.toArray(returnNumbs);
        return returnNumbs;
    }

    public static String joinPhoneNumbersToString(String[] phoneNumbers) {
        StringBuilder returnString = new StringBuilder("");
        for (String numb : phoneNumbers) {
            returnString.append(numb).append(',');
        }
        returnString.deleteCharAt(returnString.length() - 1);
        return returnString.toString();
    }

    public static String getFormattedDate(Date day) throws IOException {
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        prop.load(classLoader.getResourceAsStream("/configuration.properties"));
        SimpleDateFormat format = new SimpleDateFormat(prop.getProperty("app.dateTimeFormat"));
        String returnDate = format.format(day).toString();
        return returnDate;
    }

    public static String createPasswordForDB(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte shaDigest[] = md.digest();
        StringBuffer newPassword = new StringBuffer() ;
        for (byte b : shaDigest) {
            newPassword.append(String.format("%02x", b));
        }
        return newPassword.toString();
    }
}
