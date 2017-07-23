package com.cc.database.main;

import com.cc.database.sql.Initializer;
import com.cc.database.sql.sql_analysis;

import java.util.Scanner;

/**
 * Created by wangpeiyu on 2017/7/22.
 */
public class Main {

    public static  void main(String args[]) throws Exception {

        sql_analysis  analysis = new sql_analysis();

        Initializer initializer = new Initializer(analysis.getFilter());

        System.out.println("Data is initing ....");

        initializer.processData();

        Scanner scanner = new Scanner(System.in);

        if(scanner.hasNextLine())
        {
            String exce = scanner.nextLine();
            long start = System.currentTimeMillis();
            analysis.execute(exce);
            long end=System.currentTimeMillis();
            System.out.println(end-start);
        }

    }
}
