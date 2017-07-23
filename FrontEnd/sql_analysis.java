import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.util.*;

public class sql_analysis {
    private String sql;
    private String state;
    private String result;
    public sql_analysis(){
    }

    public boolean execute(String newsql){
        sql=newsql;

        if(newsql.startsWith("select")){
            doSelect();
            return true;
        }
        else if (newsql.startsWith("insert")){
            doInsert();
            return true;
        }
        else if (newsql.startsWith("delete")){
            doDelete();
            return true;
        }
        else if (newsql.startsWith("update")){
            doUpdate();
            return true;
        }
        else if (newsql.startsWith("count")){
            doCount();
            return true;
        }
        else if (newsql.startsWith("like")){
            doLike();
            return true;
        }
        else{
            return false;
        }
    }

    private void doSelect(){
        System.out.println(sql);
    }
    private void doInsert(){

    }
    private void doDelete(){

    }
    private void doUpdate(){

    }
    private void doCount(){

    }
    private void doLike(){

    }
    public String getState(){
        return state;
    }
    public String getResult(){
        return "zhang\t13333333333<script>alert(1);</script>00000000000\tzhang@gmail.com\tchinaSun\tshop\tmanager\nli\t13333333333\tzhang@gmail.com\tchinaSun\tshop\tmanager\nwang\t13333333333\tzhang@gmail.com\tchinaSun\tshop\tmanager";
    }

}
