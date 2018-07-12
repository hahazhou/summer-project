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

public class CameraShotcut{

    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

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

    static int ex=0;

    public static void main(String[] args) throws Exception,InterruptedException{
        System.load("C:\\OpenCV\\opencv\\build\\java\\x64\\opencv_java320.dll");

        OpenCVFrameGrabber grabber=new OpenCVFrameGrabber(0);

        grabber.start();
        CanvasFrame canvasFrame=new CanvasFrame("Camera");
        canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvasFrame.setAlwaysOnTop(true);
        opencv_core.IplImage grabbedImage = converter.convert(grabber.grab());

        final org.bytedeco.javacv.Frame frame=grabber.grab();
        TimerAction timerAction = new TimerAction(canvasFrame);
        final Timer timer = new Timer(10,timerAction);
        timerAction.setTimer(timer);

        canvasFrame.getCanvas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.start();
                try{
                    opencv_core.Mat mat = converter.convertToMat(frame);


                    opencv_imgcodecs.imwrite("G:\\javacv-test\\image\\"+ex+".png",mat);
                    ex++;
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        while(true){

            if(!canvasFrame.isVisible()&& (grabbedImage=converter.convert(grabber.grab()))!=null){
                grabber.stop();
                System.exit(2);
                break;
            }
            canvasFrame.showImage(grabber.grab());

            Thread.sleep(200);
        }
    }
}
