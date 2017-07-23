package com.cc.database.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cc.database.disk.DataOperator;
import com.cc.database.disk.Model;
import com.cc.database.hash.HasherImpl;

public class sql_analysis {
    private String sql;
    private String result;
    private Map<Integer, Map<String,List<Long>> >filter;
    private HasherImpl stringTohash;
    private DataOperator DataFactory;
    public sql_analysis() throws Exception {
        stringTohash=new HasherImpl();
        DataFactory=DataOperator.getInstance();
        filter = new HashMap<Integer, Map<String,List<Long>> >();
        for(int i=0;i<7;i++){
            filter.put(i, new HashMap<String, List<Long>>());
        }
    }

    public Map<Integer, Map<String, List<Long>>> getFilter() {
        return filter;
    }

    public boolean execute(String newsql){
        sql=newsql;

        if(newsql.startsWith("select")){
            return doSelect();
        }
        else if (newsql.startsWith("insert")){
           return doInsert();
        }
        else if (newsql.startsWith("delete")){
           return doDelete();
        }
        else if (newsql.startsWith("update")){
            return doUpdate();
        }
        else if (newsql.startsWith("count")){
            return doCount();
        }
        else if (newsql.startsWith("like")){
            return doLike();
        }

        return false;
    }

    private boolean doSelect(){
        String[] slist = getSqlSelectStringArray(sql);
        List<Long> rowList=null;
        for(int i=0;i<6;i++){
            if(slist[i]!=null){
                int code=getColCode(getColumnNameByColumnNum(i),slist[i]);
                String hashcode=stringTohash.hash(slist[i],code);
                if(rowList==null)
                    rowList=filter.get(code).get(hashcode);
                else{
                    List<Long> rowList2=filter.get(code).get(hashcode);
                    if(rowList2!=null)
                    rowList.retainAll(rowList2);
                }
            }
        }
        if (rowList ==null){
            return false;
        }
        out:for(int i=0;i<rowList.size();i++){
           Model model =  DataFactory.get(rowList.get(i));
           for(int j=0;j<7;j++){
               if(slist[j]!=null){
                   switch (j){
                       case 0:
                           break;
                       case 1:
                           if(!slist[1].equals(model.Name)){
                               continue out;
                           }
                           break;
                       case 2:
                           if(!slist[2].equals(model.Phone)){
                               continue out;
                           }
                           break;
                       case 3:
                           if(!slist[3].equals(model.Email)){
                               continue out;
                           }
                           break;
                       case 4:
                           if(!slist[4].equals(model.Company)){
                               continue out;
                           }
                           break;
                       case 5:
                           if(!slist[5].equals(model.Department)){
                               continue out;
                           }
                           break ;
                       case 6:
                           if(!slist[6].equals(model.Position)){
                               continue out;
                           }
                           break;
                   }
               }
           }
            System.out.print(model.toString());
        }

        return true;
    }

    private String getColumnNameByColumnNum(int i){
        String columnName = "";
        switch (i)
        {
            case 0:
                columnName = "name";
                break;
            case 1:
                columnName = "name";
                break;
            case 2:
                columnName = "phone";
                break;
            case 3:
                columnName = "email";
                break;
            case 4:
                columnName = "compan";
                break;
            case 5:
                columnName = "depart";
                break;
            case 6:
                columnName = "posit";
                break;
        }
        return columnName;
    }

    private String[] getSqlSelectStringArray(String sql){
        String sqlArray[] = new String[7];
        String str=sql.substring(7);
        String[] slist=str.split(",");
        for(int i=0;i<slist.length;i++) {
            String[] ss = slist[i].split("=");
            String colname = ss[0].trim();
            String value = ss[1].trim();
            sqlArray[getColCode(colname,value)] = value;
        }
        return sqlArray;
    }


    private boolean doInsert(){
        return true;

    }
    private boolean doDelete(){
        return true;

    }
    private boolean doUpdate(){
        return true;

    }
    private boolean doCount(){
        return true;

    }
    private boolean doLike(){
        return true;

    }
    private int getColCode(String name,String value){
        if(name.startsWith("name") && value.endsWith("*")) return 0;
        else if(name.startsWith("name")) return 1;
        else if(name.startsWith("phone")) return 2;
        else if(name.startsWith("email")) return 3;
        else if(name.startsWith("compan")) return 4;
        else if(name.startsWith("depart")) return 5;
        else if(name.startsWith("posit"))  return 6;
        else return 7;
    }


    public String getResult(){
        return result;
    }

    public  static void main(String[] args) throws Exception {
        sql_analysis d=new sql_analysis();
        System.out.println( d.execute("select name='王冠淞', phone='123'"));
        System.out.println( d.execute("count"));
        System.out.println( d.execute("sdfsdf"));
    }
}
