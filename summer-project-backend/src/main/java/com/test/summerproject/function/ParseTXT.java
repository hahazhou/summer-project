package com.test.summerproject.function;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ParseTXT {
    static Map<String, String> type = new HashMap<>();

    public static Map<String, String> parseTxt(String filename) {

        File file = new File(filename);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s;

            while ((s = bufferedReader.readLine()) != null) {
                //System.out.println(s);

                String[] temp = s.split(" ");
                //System.out.println(temp[1]);

                type.put(temp[0], temp[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return type;
    }


}
