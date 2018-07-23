package com.test.summerproject.function;

import java.io.*;

public class Reid {
    public static void cmd_emb_query() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python G:/SummerProject/reid/triplet-reid-250eb1/embed.py" +
                " --experiment_root G:/SummerProject/reid/experiments/my_experiment" +
                " --dataset G:/SummerProject/reid/query.csv" +
                " --checkpoint checkpoint-25000" +
                " --filename query_embeddings.h5"
        );
        process.waitFor();
    }

    public static void cmd_emb_test() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python G:/SummerProject/reid/triplet-reid-250eb1/embed.py" +
                " --experiment_root G:/SummerProject/reid/experiments/my_experiment" +
                " --dataset G:/SummerProject/reid/test.csv" +
                " --checkpoint checkpoint-25000" +
                " --filename test_embeddings.h5"
        );
        process.waitFor();
    }

    public static int cmd_eva() throws IOException, InterruptedException {
        int bool = 0;
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python G:/SummerProject/reid/triplet-reid-250eb1/evaluate1.py" +
                " --query_dataset G:/SummerProject/reid/query.csv" +
                " --query_embeddings G:/SummerProject/reid/experiments/my_experiment/query_embeddings.h5" +
                " --gallery_dataset G:/SummerProject/reid/test.csv" +
                " --gallery_embeddings G:/SummerProject/reid/experiments/my_experiment/test_embeddings.h5" +
                " --metric euclidean" +
                " --filename G:/SummerProject/reid/experiments/my_experiment/market1501_evaluation.json");
        process.waitFor();
        InputStream in = process.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        String message;
        StringBuffer sb = new StringBuffer();
        while ((message = br.readLine()) != null) {
            sb.append(message);
        }
        if (sb.toString().contains("top-1: 50.00%")) {
            bool = 1;
        }
        System.out.println(sb);
        return bool;
    }

    public static void writecsv(String imagesource, String targetimage) throws IOException, InterruptedException {
        cmd_emb_test();
        File success = new File("G:/SummerProject/reid/target.txt");
        File allimge = new File(imagesource);
        String[] filelist = allimge.list();
        String imagereserved = "0002,image_tested/4.png" + "\r\n";
        String list = "";
        for (int i = 0; i < (filelist.length ); i++) {
            File csv = new File("G:/SummerProject/reid/query.csv");
            csv.delete();
            csv = new File("G:/SummerProject/reid/query.csv");
            FileOutputStream querycsv = new FileOutputStream(csv);
            String csvwriter = "0002,image_tested/" + filelist[i] + "\r\n";
            querycsv.write(csvwriter.getBytes());
            csvwriter = imagereserved;
            querycsv.write(csvwriter.getBytes());
            querycsv.close();
            cmd_emb_query();
            if (!(cmd_eva() != 1)) {
                list = list + filelist[i] + "\r\n";
            }
            FileOutputStream match = new FileOutputStream(success);
            match.write(list.getBytes());
            match.close();
        }
    }

    
}