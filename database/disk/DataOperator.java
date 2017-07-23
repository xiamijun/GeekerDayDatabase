package com.cc.database.disk;

import javafx.scene.chart.PieChart;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by deadoggy on 17-7-22.
 */
public class DataOperator implements Operator {

    public static String DATA_FILE_NAME = "database";
    public static String LINE_RECORD_NAME = "line_record";
    private static DataOperator instance = null;
    private static Object lock = new Object();

    private byte[] emptyLine;
    private List<long[]> freeTable;
    private RandomAccessFile dataAccessor = null;
    private RandomAccessFile lineRecAccessor = null;


    private DataOperator() throws Exception{
        this.dataAccessor = new RandomAccessFile(DATA_FILE_NAME, "rw");
        this.lineRecAccessor = new RandomAccessFile(LINE_RECORD_NAME, "rw");
        this.freeTable = new ArrayList<>();
        this.emptyLine = new byte[LINE_REC_WIDTH];
        for(int i=0; i<LINE_REC_WIDTH; i++){
            this.emptyLine[i] = 0;
        }
    }

    public static DataOperator getInstance() throws Exception{
        if(null != instance){
            return instance;
        }
        synchronized (lock){
            if(null == instance){
                instance = new DataOperator();
            }
            return instance;
        }

    }

    public Model get(long lineNumber) {
        try{
            long[] recPos = searchLineInfo(lineNumber);
            byte[] record = new byte[(int)recPos[2]];

            long l = dataAccessor.length();

            dataAccessor.seek(recPos[1]);
            dataAccessor.read(record,0 , (int)recPos[2]);
            String val = new String(record);
            return StringToModel(val);

        }catch(Exception e){
            return null;
        }

    }

    public List<Model> get(List<Long> lineNumbers){
        List<Model> retList = new ArrayList<Model>();
        for(Long line: lineNumbers){
            retList.add(get(line));
        }
        return retList;
    }

    public long set(Model model){
        try{
            StringBuilder strModel = new StringBuilder();
            strModel.append(model.Name).append("|-|")
                    .append(model.Phone).append("|-|")
                    .append(model.Email).append("|-|")
                    .append(model.Company).append("|-|")
                    .append(model.Department).append("|-|")
                    .append(model.Position);
            byte[] content = strModel.toString().getBytes();


            boolean recycle = false;
            int freeIndex = -1;
            //从freeTable里面找有没有空余的空间
            for(int i=0; i<freeTable.size(); i++){
                if(freeTable.get(i)[1] >= content.length){
                    freeIndex = i;
                    break;
                }
            }

            long newLineNum = lineRecAccessor.length() / LINE_REC_WIDTH; //新的行号
            if(-1 != freeIndex){ // 找到了空余
                long[] freeItem = freeTable.get(freeIndex);
                //写入data 文件
                dataAccessor.seek(freeItem[0]);
                dataAccessor.write(content);
                //写入line_record 文件

                lineRecAccessor.seek(lineRecAccessor.length());
                StringBuilder newLineRec = new StringBuilder();
                newLineRec.append(newLineNum).append("|")
                        .append(freeItem[0]).append("|")
                        .append(content.length).append("\n");
                lineRecAccessor.write(StringToByte(newLineRec.toString(),LINE_REC_WIDTH));
                //更新freeTable
                if(content.length == freeItem[1]){
                    freeTable.remove(freeIndex);
                }else{
                    freeTable.get(freeIndex)[0] = freeItem[0] + content.length;
                    freeTable.get(freeIndex)[1] = freeItem[1] - content.length;
                }
            }else{ //没有找到空余
                //写入data 文件
                dataAccessor.seek(dataAccessor.length());
                dataAccessor.write(content);
                //写入line_record 文件
                lineRecAccessor.seek(lineRecAccessor.length());
                StringBuilder newLineRec = new StringBuilder();
                newLineRec.append(newLineNum).append("|")
                        .append(dataAccessor.length() - content.length).append("|")
                        .append(content.length).append("\n");
                lineRecAccessor.write(StringToByte(newLineRec.toString(),LINE_REC_WIDTH));
            }
            return newLineNum;
        }catch(Exception e){
            return -1;
        }

    }

    public long update(long lineNumber, Model model){
        delete(lineNumber);
        return set(model);
    }

    public boolean delete(long lineNumber){
        try{
            long[] linePos = searchLineInfo(lineNumber);
            lineRecAccessor.seek(LINE_REC_WIDTH * linePos[3]);
            lineRecAccessor.write(emptyLine);
            long[] freeItem = new long[2];
            freeItem[0] = linePos[1];//起始
            freeItem[1] = linePos[2];//长度
            this.freeTable.add(freeItem);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    private Model StringToModel(String val){
        String[] attributes = val.split("\\|-\\|");
        Model ret = new Model();
        ret.Name = attributes[0];
        ret.Phone = attributes[1];
        ret.Email = attributes[2];
        ret.Company = attributes[3];
        ret.Department = attributes[4];
        ret.Position = attributes[5];
        return ret;
    }

    private long[] searchLineInfo(long lineNumber) throws Exception{
        byte[] lineRecBytes = new byte[LINE_REC_WIDTH];

        long realLineNumber = lineNumber;

        long total = lineRecAccessor.length() / 100;

        boolean end = false;
        int direct = 0; // -1 向上， 0 正好， 1 向下
        do{
            //读取数据
            lineRecAccessor.seek(realLineNumber * LINE_REC_WIDTH );
            lineRecAccessor.read(lineRecBytes, 0, LINE_REC_WIDTH);


            //判断是正好， 向上还是向下
            String record = new String(lineRecBytes);
            //判断是不是空行
            if(Arrays.equals(emptyLine, lineRecBytes)){
                if(direct >=0 ){
                    realLineNumber++;
                }else{
                    realLineNumber--;
                }
                continue;
            }

            String[] rs = record.split("\\|");
            Long recLine = Long.parseLong(rs[0]);
            if(recLine.compareTo(lineNumber)==0){ //找到了
                end = true;
            }else if(recLine < lineNumber){ //要向下走
                if(direct == -1){//之前向上
                    return null;
                }
                direct = 1;
                realLineNumber++;
            }else{//要向上走
                if(direct == 1){//之前向下走
                    return null;
                }
                direct = -1;
                realLineNumber--;
            }
        }while(!end);

        String[] retStr = new String(lineRecBytes).split("\\|");
        long[] ret = new long[4];
        ret[0] = Long.parseLong(retStr[0]);
        ret[1] = Long.parseLong(retStr[1]);
        ret[2] = Long.parseLong(retStr[2].substring(0, retStr[2].indexOf("\n")));
        ret[3] = realLineNumber;

        return ret;
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
        try{
            DataOperator op = DataOperator.getInstance();

            Model m = new Model();
            m.Name = "aaa";
            m.Phone ="12121";
            m.Email = "sdsdsd";
            m.Department = "sds";
            m.Position = "sdsd";
            m.Company = "sdsd";

            long index = op.set(m);

            Model mg = op.get(index);

            m.Name = "bbb";

            long newIndex =op.update(index,m);

            Model newM = op.get(newIndex);

            System.out.println(10);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}


