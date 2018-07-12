import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class ObjectDetection {
    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    //count
    static int ex = 0;

    //ActionLister
    static class TimerAction implements ActionListener {
        private Graphics2D g;
        private CanvasFrame canvasFrame;
        private int width, height;
        private int delta = 10;
        private int count = 0;

        private Timer timer;

        public void setTimer(Timer timer) {
            this.timer = timer;
        }

        public TimerAction(CanvasFrame canvasFrame) {
            this.g = (Graphics2D) canvasFrame.getCanvas().getGraphics();
            this.canvasFrame = canvasFrame;
            this.width = canvasFrame.getCanvas().getWidth();
            this.height = canvasFrame.getCanvas().getHeight();
        }

        public void actionPerformed(ActionEvent e) {
            int offset = delta * count;
            if (width - offset >= offset && height - offset >= offset) {
                g.drawRect(offset, offset, width - 2 * offset, height - 2 * offset);
                canvasFrame.repaint();
                count++;
            } else {
                timer.stop();
                count = 0;
            }
        }
    }

    public static void printScreen(int devicenumber) throws Exception {
        System.load("C:\\OpenCV\\opencv\\build\\java\\x64\\opencv_java320.dll");
        //write test.txt
        final FileWriter fileWriter = new FileWriter("G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\test.txt");

        //open camera
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(devicenumber);

        grabber.start();

        final CanvasFrame canvasFrame = new CanvasFrame("Camera: " + devicenumber);

        canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvasFrame.setAlwaysOnTop(true);
        opencv_core.IplImage grabbedImage = converter.convert(grabber.grab());

        final org.bytedeco.javacv.Frame frame = grabber.grab();
        TimerAction timerAction = new TimerAction(canvasFrame);
        final Timer timer = new Timer(10, timerAction);
        timerAction.setTimer(timer);

        JPanel contentPane = new JPanel();
        Container contentPane2 = canvasFrame.getContentPane();
        JButton exit = new JButton("Exit");


        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                canvasFrame.setVisible(false);
            }
        });

        //Mouse Click
        canvasFrame.getCanvas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.start();
                try {
                    opencv_core.Mat mat = converter.convertToMat(frame);

                    opencv_imgcodecs.imwrite("G:\\javacv-test\\image\\" + ex + ".png", mat);
                    fileWriter.write("G:\\javacv-test\\image\\" + ex + ".png\r\n");
                    ex++;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

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
        fileWriter.close();
        return;
    }

    public static void executeCmd(String cmd) throws IOException {
        //handle command

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd.exe /c start " + cmd);

        //wait for exe

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

    public static void main(String[] args) throws Exception {
        String cmd = "G:\\caffe-vs2013\\caffe-ssd-microsoft\\Build\\x64\\Release\\ssd_detect.exe " +
                "G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\deploy.prototxt " +
                "G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\VGG_VOC0712_SSD_300x300_iter_120000.caffemodel " +
                "G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\test.txt " +
                "G:\\\\javacv-test\\\\";
        printScreen(0);
        
        executeCmd(cmd);

        
    }
}
