package com.test.summerproject.function;


import java.io.IOException;

public class SSD_dectect {

    static String ssd_detect = "G:\\caffe-vs2013\\caffe-ssd-microsoft\\Build\\x64\\Release\\ssd_detect.exe ";
    static String deploy_prototxt = "G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\deploy.prototxt ";
    static String caffemodel = "G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\VGG_VOC0712_SSD_300x300_iter_120000.caffemodel ";

    static String outputfile = "G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\test.txt";

    static String outputfolder = "G:\\javacv-test\\image";

    //createSSDCmd(G:/SummerProject/snapshot/snapshot.txt,G:/SummerProject/snapshot,type)


    public static void runSSD(String txtfile, String savepath, String type) throws IOException {
        String command = ssd_detect + deploy_prototxt + caffemodel + txtfile + " " + savepath + " " + type;
        ExecuteCmd.executeCmd(command);
    }

}
