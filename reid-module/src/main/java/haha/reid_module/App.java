package haha.reid_module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class App 
{
	public static void cmd_emb_query() throws IOException
	{
		Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python3 C:/Users/lenovo/Downloads/triplet-reid-250eb1/embed.py" + 
        		" --experiment_root E:/experiments/my_experiment" + 
        		" --dataset E:/csv/query.csv" + 
        		" --checkpoint checkpoint-25000" + 
        		" --filename query_embeddings.h5" 
        		);
    }	
	public static void cmd_emb_test() throws IOException
	{
		Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python3 C:/Users/lenovo/Downloads/triplet-reid-250eb1/embed.py" + 
        		" --experiment_root E:/experiments/my_experiment" + 
        		" --dataset E:/csv/test.csv" + 
        		" --checkpoint checkpoint-25000" + 
        		" --filename test_embeddings.h5"
        		);
	}
	public static int cmd_eva() throws IOException, InterruptedException 
	{
		int bool = 0;
		Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python3 C:/Users/lenovo/Downloads/triplet-reid-250eb1/evaluate1.py" + 
        		" --query_dataset E:/csv/query.csv" + 
        		" --query_embeddings E:/experiments/my_experiment/query_embeddings.h5" + 
        		" --gallery_dataset E:/csv/test.csv" + 
        		" --gallery_embeddings E:/experiments/my_experiment/test_embeddings.h5" + 
        		" --metric euclidean" + 
        		" --filename E:/experiments/my_experiment/market1501_evaluation.json");
        InputStream in = process.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        String message;
        StringBuffer sb = new StringBuffer();
        while((message = br.readLine()) != null) {
            sb.append(message);
        }
        if(sb.toString().contains("top-1: 100.00%")) {
        	bool = 1;
        }
        System.out.println(sb);
        return bool;
	}
    public static void writecsv(String imagesource,String targetimage) throws IOException, InterruptedException
    {
    	cmd_emb_test();
    	File success = new File("E:/target.txt");
        File allimge = new File(imagesource);
        String[] filelist = allimge.list();
        String imagereserved = "0002,query(2)/"+targetimage+"\n";
        String list = "";
        for (int i = 1;i < (filelist.length-1);i++) {
          File csv = new File("E:/csv/query.csv");
          csv.delete();
          csv = new File("E:/csv/query.csv");
          FileOutputStream querycsv = new FileOutputStream(csv);
          String csvwriter = "0002,query(2)/"+filelist[i]+"\n";
          querycsv.write(csvwriter.getBytes());
          csvwriter = imagereserved;
          querycsv.write(csvwriter.getBytes());
          querycsv.close();
          cmd_emb_query();
          if(!(cmd_eva() != 1)){
        	  list = list + filelist[i] + "\r\n";
          }
          FileOutputStream match = new FileOutputStream(success);
    	  match.write(list.getBytes());
    	  match.close();
        }    
    }
    public static void main(String[] args) throws IOException, InterruptedException {
    	writecsv("E:/Market-1501-v15.09.15/query(2)", "1.jpg"); 
    }
}
