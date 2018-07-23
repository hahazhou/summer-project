package haha.reid_module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

public class App 
{
	public static void cmd_emb_query() throws IOException, InterruptedException
	{
		Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python3 C:/Users/lenovo/Downloads/triplet-reid-250eb1/embed.py" + 
        		" --experiment_root E:/experiments/my_experiment" + 
        		" --dataset E:/csv/query.csv" + 
        		" --checkpoint checkpoint-25000" + 
        		" --filename query_embeddings.h5" 
        		);
        process.waitFor();
        System.out.print("ok");
    }	
	public static void cmd_emb_test() throws IOException, InterruptedException
	{
		Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python3 C:/Users/lenovo/Downloads/triplet-reid-250eb1/embed.py" + 
        		" --experiment_root E:/experiments/my_experiment" + 
        		" --dataset E:/csv/test.csv" + 
        		" --checkpoint checkpoint-25000" + 
        		" --filename test_embeddings.h5"
        		);
        process.waitFor();
        System.out.print("12");
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
        process.waitFor();
        System.out.print("345");
        InputStream in = process.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        String message;
        StringBuffer sb = new StringBuffer();
        while((message = br.readLine()) != null) {
            sb.append(message);
        }
        int rate = Integer.parseInt(sb.toString());
        if(rate>0) {
        	bool = 1;
        }
        System.out.println(rate);
        return bool;
	}
	public static void binary(List<String>fList,String imagereserved) throws IOException, InterruptedException {
		if(fList.size()>1) {
			System.out.print(fList.size());
		    List<String>filelist1 = fList.subList(0, fList.size()/2);
		    System.out.print(filelist1.size());
            List<String>filelist2 = fList.subList(fList.size()/2, fList.size());
            System.out.print(filelist2.size());
            if(filelist1.size() == 1) {
            	File csv = new File("E:/csv/query.csv");
                csv.delete();
                csv = new File("E:/csv/query.csv");
                FileOutputStream querycsv = new FileOutputStream(csv);
                for(int i = 0;i < filelist1.size();i++) {               
                    String csvwriter = "0002,query(2)/"+filelist1.get(i)+"\n";
                    querycsv.write(csvwriter.getBytes());
                }
                querycsv.write(imagereserved.getBytes());
                querycsv.close();
                cmd_emb_query();
                if(cmd_eva() != 1){
                  System.out.print(cmd_eva());
              	  for(int index = 0;index < filelist1.size() ;index++) {
              		  String item = filelist1.get(index);
              		  Iterator<String> iterator = fList.iterator();
            		  while(iterator.hasNext()){
                        String filename = iterator.next();
                        if(filename.equals(item))
                            iterator.remove();
                        break;
                    } 
              	  }
                }
            }
            else {
            File csv = new File("E:/csv/query.csv");
            csv.delete();
            csv = new File("E:/csv/query.csv");
            FileOutputStream querycsv = new FileOutputStream(csv);
            for(int i = 0;i < filelist1.size();i++) {               
                String csvwriter = "0002,query(2)/"+filelist1.get(i)+"\n";
                querycsv.write(csvwriter.getBytes());
            }
            querycsv.write(imagereserved.getBytes());
            querycsv.close();
            cmd_emb_query();
            if(cmd_eva() != 1){
              System.out.print(cmd_eva());
          	  for(int index = 0;index < filelist1.size() ;index++) {
          		  String item = filelist1.get(index);
          		  Iterator<String> iterator = fList.iterator();
        		  while(iterator.hasNext()){
                    String filename = iterator.next();
                    if(filename.equals(item))
                        iterator.remove();
                    break;
                } 
          	  }
            }
            else {
            	binary(filelist1,imagereserved);
            }}
            if(filelist2.size() == 1) {
            	File csv2 = new File("E:/csv/query.csv");
                csv2.delete();
                csv2 = new File("E:/csv/query.csv");
                FileOutputStream querycsv2 = new FileOutputStream(csv2);
                for(int i = 0;i < filelist1.size();i++) {               
                    String csvwriter2 = "0002,query(2)/"+filelist2.get(i)+"\n";
                    querycsv2.write(csvwriter2.getBytes());
                }
                querycsv2.write(imagereserved.getBytes());
                querycsv2.close();
                cmd_emb_query();
                if(cmd_eva() != 1){
                  System.out.print(cmd_eva());
              	  for(int index2 = 0;index2 < filelist2.size() ;index2++) {
              		  String item = filelist2.get(index2);
              		  Iterator<String> iterator = fList.iterator();
            		  while(iterator.hasNext()){
                        String filename = iterator.next();
                        if(filename.equals(item))
                            iterator.remove();
                        break;
                    } 
              	  }
                }
            }
            else {
            File csv2 = new File("E:/csv/query.csv");
            csv2.delete();
            csv2 = new File("E:/csv/query.csv");
            FileOutputStream querycsv2 = new FileOutputStream(csv2);
            for(int i = 0;i < filelist2.size();i++) {               
                String csvwriter2 = "0002,query(2)/"+filelist2.get(i)+"\n";
                querycsv2.write(csvwriter2.getBytes());
            }
            querycsv2.write(imagereserved.getBytes());
            querycsv2.close();
            cmd_emb_query();
            if(cmd_eva() != 1){
              System.out.print(cmd_eva());
          	  for(int index2 = 0;index2 < filelist2.size() ;index2++) {
          		  String item2 = filelist2.get(index2);
          		  Iterator<String> iterator = fList.iterator();
          		  while(iterator.hasNext()){
                      String filename = iterator.next();
                      if(filename.equals(item2))
                          iterator.remove();
                      break;
                  }
          	  }
            }
            else {
            	binary(filelist2,imagereserved);
            } }
        }
		else {
			File csv = new File("E:/csv/query.csv");
            csv.delete();
            csv = new File("E:/csv/query.csv");
            FileOutputStream querycsv = new FileOutputStream(csv);
            for(int i = 0;i < fList.size();i++) {               
                String csvwriter = "0002,query(2)/"+fList.get(i)+"\n";
                querycsv.write(csvwriter.getBytes());
            }
            querycsv.write(imagereserved.getBytes());
            querycsv.close();
            cmd_emb_query();
            if(cmd_eva() != 1){
              System.out.print(cmd_eva());
          	  for(int index = 0;index < fList.size() ;index++) {
          		  String item = fList.get(index);
          		Iterator<String> iterator = fList.iterator();
        		  while(iterator.hasNext()){
                    String filename = iterator.next();
                    if(filename.equals(item))
                        iterator.remove();
                    break;
                } 
          	  }
            }
		}
	}
	public static void writetarget(String list) throws IOException {
    	File success = new File("E:/target.txt");
    	FileOutputStream match = new FileOutputStream(success);
  	    match.write(list.getBytes());
  	    match.close();
    }
    public static void writequerycsv(String imagesource,String targetimage) throws IOException, InterruptedException
    {   	
        File allimge = new File(imagesource);
        String[] filelist = allimge.list();
        List<String> fList=new ArrayList<String>();
        for(String s:filelist){
            fList.add(s);
        } 
        fList.remove("desktop.ini");
        String imagereserved = "0001,query(2)/"+targetimage+"\n";
        String list = "";
        if(fList.size() > 1) {
			binary(fList, imagereserved);
        } 
        for(int j = 0;j < fList.size();j++) {
            list = list + fList.get(j) + "\r\n";
        }
        writetarget(list);
    }    
    public static void main(String[] args) throws IOException, InterruptedException {
    	cmd_emb_test();
    	writequerycsv("E:/Market-1501-v15.09.15/query(2)", "1.jpg"); 
    }
}
