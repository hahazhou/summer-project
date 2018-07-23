package com.test.summerproject.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MovePhoto {

    public enum moveType {PHOTO, FOLDER}

    public static void movePhoto(moveType type, String fromFolder, String toFolder, String filename) {

        if (type == moveType.PHOTO) {
            File srcFile = new File(fromFolder + "\\" + filename);
            File targetFile = new File(toFolder + "\\" + filename);
            try {
                FileInputStream fileInputStream = new FileInputStream(srcFile);
                FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                byte[] bytes = new byte[1024];
                int len = -1;

                while ((len = fileInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                }
                fileOutputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type == moveType.FOLDER) {
            //ArrayList<String> files = new ArrayList<>();
            File file = new File(fromFolder);
            File[] templist = file.listFiles();
            for (int i = 0; i < templist.length; i++) {
                if (templist[i].isFile()) {
                    String name = templist[i].toString();
                    String[] temp = name.split("\\\\");
                    movePhoto(moveType.PHOTO, fromFolder, toFolder, temp[temp.length - 1]);
                }
            }

        }
    }

}
