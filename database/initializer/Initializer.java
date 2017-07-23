package com.cc.database.initializer;

import java.io.*;

/**
 * Created by deadoggy on 17-7-22.
 */
public class Initializer {

    public Initializer(){
    }

    public void processData(){
        File  originalData = new File("card_person.data");

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

                //输出
                byte[] outBytes = StringArrayToByte(columns);
                long pos = targetWriter.length();

                StringBuilder lineRecord = new StringBuilder();

                lineRecord.append(lineNumber).append("|").append(pos).append("|").append(outBytes.length).append("\n");

                targetWriter.seek(pos);
                targetWriter.write(outBytes);
                lineRecordWriter.write(StringToByte(lineRecord.toString(), 100));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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


    public static void main(String[] argv){
        Initializer dataWasher = new Initializer();
        dataWasher.processData();
    }
}
