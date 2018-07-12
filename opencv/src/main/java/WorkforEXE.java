import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class WorkforEXE {

    public static void main(String[] args){
        try{
            String command="G:\\caffe-vs2013\\caffe-ssd-microsoft\\Build\\x64\\Release\\ssd_detect.exe G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\deploy.prototxt ^G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\VGG_VOC0712_SSD_300x300_iter_120000.caffemodel G:\\caffe-vs2013\\caffe-ssd-microsoft\\models\\VGGNet\\VOC0712\\SSD_300x300\\test.txt -file_type=image -confidence_threshold=0.5 pause";
            Runtime runtime=Runtime.getRuntime();
            Process process=runtime.exec("cmd.exe /c start "+command);

            String output;
            InputStream pin=process.getInputStream();
            InputStreamReader cin=new InputStreamReader(pin);
            BufferedReader in=new BufferedReader(cin);
            try{
                output =in.readLine();
                while(output!=null){
                    System.out.println(output);
                    output=in.readLine();
                }
                in.close();
                process.waitFor();
            }catch (Exception e){
                System.out.println("error");
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
