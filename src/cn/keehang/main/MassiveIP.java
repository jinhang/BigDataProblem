package cn.keehang.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
public class MassiveIP {
    
    public void generateIP(String fileName){
        PrintWriter out =null;
        try {
            out=new PrintWriter(fileName);
            
            String s;
            Random r=new Random();
            for(int i=0;i<100000000;i++){
                s="159.227.";
                s+=r.nextInt(256)+"."+r.nextInt(256);
                out.println(s);
            }


        } catch (IOException e) {
           
            e.printStackTrace();
        }
        finally{
            if (out != null)
                out.close( );
        }

    }
    
    public void FileSplit(String fileName){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader (fileName));
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
        }
        
        PrintWriter[] out=new PrintWriter[100];//生成一百个小文件
        for(int i=0;i<100;i++)
            try {
                
                out[i]=new PrintWriter(fileName+i);//将小文件对象放入数组中
            } catch (FileNotFoundException e) {
               
                e.printStackTrace();
            }
        String IP = null;
        try {
            while((IP =reader.readLine())!= null ) {
                IP=reader.readLine();//BufferedReader 读入按照行读入文件
                int fileNum=IP.hashCode()%100; //求出hash
                fileNum=(fileNum>=0?fileNum:fileNum+100);
                
                out[fileNum].println(IP);//切割成100个文件写进去
            }
            for(int i=0;i<100;i++)
                out[i].close();
                
            //}
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
           
            e.printStackTrace();
        }


    }
   
    public Map.Entry<String,Integer>  statitics(String fileName){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader (fileName));
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
        }
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        String IP = null;
        try {
            //用hashmap统计
        	while((IP =reader.readLine())!= null){
                
                if(map.containsKey(IP)){
                    map.put(IP, map.get(IP)+1);
                }
                else
                    map.put(IP,1);
            }
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Map.Entry<String,Integer>  maxEntry=null;
        //找到最大
        for (Map.Entry<String,Integer> entry : map.entrySet()){
            if (maxEntry == null || entry.getValue()>maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return maxEntry;
    }
    public static void main(String[] args){
        long start = System.currentTimeMillis();
    	MassiveIP m=new MassiveIP();
        String FileName="E://Data//test.txt";
        m.generateIP(FileName);
        m.FileSplit(FileName);
        List<Map.Entry<String,Integer>>l = new ArrayList<Map.Entry<String,Integer>>();
        //遍历100个小文件，对她们进行统计，将结果map.entry放入list保存
        for(int i=0;i<100;i++)
            l.add(m.statitics(FileName+i));
        Map.Entry<String,Integer>maxEntry=l.get(0);
        //找到最大的
        for(int j=1;j<100;j++){
            if(l.get(j).getValue()>maxEntry.getValue())
                maxEntry=l.get(j);
        }
        System.out.println(maxEntry.getKey());
        System.out.println(maxEntry.getValue());
        long end = System.currentTimeMillis();
        System.out.println("耗时间："+(end-start));
    }

}
