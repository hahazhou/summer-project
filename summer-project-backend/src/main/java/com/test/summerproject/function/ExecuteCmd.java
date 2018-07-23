package com.test.summerproject.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecuteCmd {
    public static void executeCmd(String cmd) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd.exe /c start " + cmd);

        String output;
        InputStream pin = process.getInputStream();
        InputStreamReader cin = new InputStreamReader(pin);
        BufferedReader in = new BufferedReader(cin);

        try {
            while ((output = in.readLine()) != null) ;
            in.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
