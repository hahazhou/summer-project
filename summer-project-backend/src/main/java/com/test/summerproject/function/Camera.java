package com.test.summerproject.function;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Camera {
    public static boolean opencamera(int devicenumber) throws Exception {
        VideoCapture videoCapture = new VideoCapture(devicenumber);
        if (!videoCapture.isOpened()) {
            return false;
        }
        videoCapture.release();
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(devicenumber);

        grabber.start();
        CanvasFrame canvasFrame = new CanvasFrame("Carema: " + devicenumber);
        canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvasFrame.setAlwaysOnTop(true);

        JPanel contentPane = new JPanel();
        Container contentPane2 = canvasFrame.getContentPane();
        JButton exit = new JButton("Exit");


        //exit button
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                canvasFrame.setVisible(false);
            }
        });


        contentPane.add(exit, BorderLayout.SOUTH);

        contentPane2.add(contentPane, BorderLayout.SOUTH);


        while (canvasFrame.isVisible()) {
            canvasFrame.showImage(grabber.grab());
            Thread.sleep(200);
        }

        canvasFrame.dispose();
        grabber.stop();
        return true;
    }
}
