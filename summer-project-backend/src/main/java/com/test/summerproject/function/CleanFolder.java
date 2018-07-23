package com.test.summerproject.function;

import java.io.File;

public class CleanFolder {
    public static boolean delDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("dir not exist");
            return false;
        }

        String[] contents = file.list();

        for (String name : contents) {
            if (name.equals("disturb.png")) {
                continue;
            }
            File temp = new File(path, name);
            if (temp.isDirectory()) {
                delDir(temp.getAbsolutePath());
            } else {
                if (!temp.delete()) {
                    System.out.println("fail to delete " + name);
                }
            }

        }
        return true;
    }



}
