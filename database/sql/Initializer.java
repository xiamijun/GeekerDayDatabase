package com.cc.database.sql;

import com.cc.database.hash.HasherImpl;
import com.cc.database.hash.IHasher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by deadoggy on 17-7-22.
 */
public class Initializer {

    private Map<Integer, Map<String,List<Long>>>filter;

    IHasher hasher = new HasherImpl();

    public Initializer(Map<Integer, Map<String,List<Long>>> filter){
        this.filter = filter;
    }

    public void processData(){
        File  originalData = new File("/Users/wangpeiyu/Documents/javaworkspace/CCGeeker/src/card_person.data");

        try{
            //创建读写类
            BufferedReader reader = new BufferedReader(new FileReader(originalData));
            RandomAccessFile targetWriter = new RandomAccessFile("database", "rw");
            RandomAccessFile lineRecordWriter = new RandomAccessFile("line_record","rw");


            //从原始文件读取每行然后进行处理
            String line = null;
            long lineNumber = -1;
            while((line=reader.readLine()) != null){
                if(lineNumber%1000 == 0){
                    System.out.println(lineNumber);
                }
                String columns[] = line.split("\t");
                if(columns.length < 6){
                    //TODO： 异常数据
                    continue;
                }


                lineNumber++;
                processMap(columns[0],lineNumber,0);
                processMap(columns[0],lineNumber,1);
//                processMap(columns[1],lineNumber,2);
//                processMap(columns[2],lineNumber,3);
//                processMap(columns[3],lineNumber,4);
//                processMap(columns[4],lineNumber,5);
//                processMap(columns[5],lineNumber,6);

                //输出
                byte[] outBytes = StringArrayToByte(columns);
                long pos = targetWriter.length();

                StringBuilder lineRecord = new StringBuilder();

                lineRecord.append(lineNumber).append("|").append(pos).append("|").append(outBytes.length).append("\n");

                targetWriter.seek(pos);
                targetWriter.write(outBytes);
                lineRecordWriter.write(StringToByte(lineRecord.toString(), 100));

                if(lineNumber>5000000){
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void processMap(String val,long number,int mapPos) {
        Map<String,List<Long>> map = filter.get(mapPos);
        String hashcode = hasher.hash(val,mapPos);
        List<Long> longs = map.get(hashcode);
        if(longs==null){
            longs = new ArrayList<Long>();
            map.put(hashcode,longs);
        }
        longs.add(number);
    }


    private byte[]  StringToByte(String[] columns){

        StringBuilder retStrBuilder = new StringBuilder();

        for(int i=0; i<columns.length; i++){
            retStrBuilder.append(columns[i]);
            if(i <= 4){
                retStrBuilder.append("|-|");
            }
        }
        return retStrBuilder.toString().getBytes();
    }

    private byte[]  StringArrayToByte(String[] columns){

        StringBuilder retStrBuilder = new StringBuilder();

        for(int i=0; i<columns.length; i++){
            retStrBuilder.append(columns[i]);
            if(i <= 4){
                retStrBuilder.append("|-|");
            }
        }
        return retStrBuilder.toString().getBytes();
    }

    private byte[] StringToByte(String val, int MAX_WIDTH){
        byte[] retBytes = new byte[MAX_WIDTH];
        byte[] originalBytes = val.getBytes();

        for(int i=0; i<MAX_WIDTH; i++){
            retBytes[i] = i<originalBytes.length? originalBytes[i] : 0;
        }

        return retBytes;
    }

//    public static void main(String[] argv){
//        Initializer dataWasher = new Initializer();
//        dataWasher.processData();
//    }
}
