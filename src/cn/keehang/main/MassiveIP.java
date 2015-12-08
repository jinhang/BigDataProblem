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
        
        PrintWriter[] out=new PrintWriter[100];//����һ�ٸ�С�ļ�
        for(int i=0;i<100;i++)
            try {
                
                out[i]=new PrintWriter(fileName+i);//��С�ļ��������������
            } catch (FileNotFoundException e) {
               
                e.printStackTrace();
            }
        String IP = null;
        try {
            while((IP =reader.readLine())!= null ) {
                IP=reader.readLine();//BufferedReader ���밴���ж����ļ�
                int fileNum=IP.hashCode()%100; //���hash
                fileNum=(fileNum>=0?fileNum:fileNum+100);
                
                out[fileNum].println(IP);//�и��100���ļ�д��ȥ
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
            //��hashmapͳ��
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
        //�ҵ����
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
        //����100��С�ļ��������ǽ���ͳ�ƣ������map.entry����list����
        for(int i=0;i<100;i++)
            l.add(m.statitics(FileName+i));
        Map.Entry<String,Integer>maxEntry=l.get(0);
        //�ҵ�����
        for(int j=1;j<100;j++){
            if(l.get(j).getValue()>maxEntry.getValue())
                maxEntry=l.get(j);
        }
        System.out.println(maxEntry.getKey());
        System.out.println(maxEntry.getValue());
        long end = System.currentTimeMillis();
        System.out.println("��ʱ�䣺"+(end-start));
    }

}
