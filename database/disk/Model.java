package com.cc.database.disk;
import java.util.*;
/**
 * Created by deadoggy on 17-7-22.
 */
public class Model {

    public String Name;

    public String Phone;

    public String Email;

    public String Company;

    public String Department;

    public String Position;

    public static Model buildModel(String Name,String phone,String email,String company,String department,String position){
        Model m=new Model();
        m.Name=Name;
        m.Phone=phone;
        m.Email=email;
        m.Company=company;
        m.Department=department;
        m.Position=position;
        return m;
    }

    public Map<String,String> toMap(){
        Map<String,String> m=new HashMap<String,String>();
        m.put("name",Name);
        m.put("phone",Phone);
        m.put("email",Email);
        m.put("company",Company);
        m.put("department",Department);
        m.put("position",Position);
        return m;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("名字："+Name+"\t");
        sb.append("电话："+Phone+"\t");
        sb.append("邮箱："+Email+"\t");
        sb.append("公司："+Company+"\t");
        sb.append("部门："+Department+"\t");
        sb.append("职位："+Position+"\t");
        sb.append("\n");
        return sb.toString();
    }
}
