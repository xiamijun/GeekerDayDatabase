package com.cc.database.hash;

/**
 * Created by deadoggy on 17-7-22.
 */
public interface IHasher {


    int FIRSTNAME = 0;
    int SECONDNAME = 1;
    int PHONE = 2;
    int EMAIL = 3;
    int COMPANY = 4;
    int DEPARTMENT = 5;
    int POSITION = 6;


    String hash(String val, int field);



}
