import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;

import java.io.FileWriter;
import java.io.IOException;


public class VideoCaptureTest {

    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    public static void VideoCapture(String videopath, String outputfolder, String outputfile, int interval) throws Exception {
        //对于较大的视频，处理速度不高 20分钟左右的动漫 处理速度为3min左右  162292

        //interval根据视频不同，可能会生成不出图片

        long i=System.currentTimeMillis();
        Frame frame = null;

        FileWriter fileWriter=new FileWriter(outputfile);
        System.load("C:\\OpenCV\\opencv\\build\\java\\x64\\opencv_java320.dll");

        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videopath);

        try {
            fFmpegFrameGrabber.start();
            int fps = (int) fFmpegFrameGrabber.getFrameRate();
            System.out.println("fps " + fps);
            int count = 1;

            int rootCount;
            while (true) {

                frame = fFmpegFrameGrabber.grabFrame();
                opencv_core.Mat mat;
                rootCount = interval;
                while (rootCount > 0) {
                    frame = fFmpegFrameGrabber.grabFrame();

                    rootCount--;
                }
                if (frame == null) {

                    break;
                }
                mat = converter.convertToMat(frame);
                if (mat != null) {
                    opencv_imgcodecs.imwrite(outputfolder + "\\" + count + ".png", mat);
                    fileWriter.write(outputfolder + "\\" + count + ".png\r\n");
                    count++;
                }


            }
            fFmpegFrameGrabber.stop();
            fileWriter.close();
            System.out.println(System.currentTimeMillis()-i);
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        VideoCapture("G:\\javacv-test\\video\\test02.avi", "G:\\javacv-test\\video-cut", "G:\\javacv-test\\video-cut\\output.txt",50);
    }
}
