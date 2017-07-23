package com.cc.database.hash;

import java.util.zip.CRC32;

/**
 * Created by wangpeiyu on 2017/7/22.
 */
public class HasherImpl implements IHasher {


    public String hash(String val, int field) {
        String code = "";
        switch (field){
            case IHasher.FIRSTNAME:
                code = getFirstNameHash(val);
                break;
            case IHasher.SECONDNAME:
                code = getSecondNameHash(val);
                break;
            case IHasher.PHONE:
                code = getPhoneHash(val);
                break;
            case IHasher.EMAIL:
                code = getEmailHash(val);
                break;
            case IHasher.COMPANY:
                code = getCompanyHash(val);
                break;
            case IHasher.DEPARTMENT:
                code = getDepartmentHash(val);
                break;
            case IHasher.POSITION:
                code = getPositionHash(val);
                break;
                default:
                    break;
        }
        return code;
    }


    private String getDepartmentHash(String val) {
        return val;
    }


    private String getPositionHash(String val) {
        return val;
    }

    private String getCompanyHash(String val) {
        return val;
    }

    private String getEmailHash(String val) {
        String hash=val;
        if(val.contains("@")){
            String[] splits = val.split("@");
             hash=splits[1];
            hash = hash + splits[0].substring(0,splits[0].length()/4*3);
        }
        return hash;
    }

    /**
     * 手机号码 hash
     * @param val
     * @return
     */
    private String getPhoneHash(String val) {
        return  val.substring(0,val.length()/11*8)+"";
    }

    /**
     *
     * @param val
     * @return
     */
    private String getSecondNameHash(String val) {
//        byte[] bytes = val.getBytes();
//        byte[] newbytes = new byte[bytes.length-2];
//        for(int i=2;i<bytes.length;i++){
//            newbytes[i-2] = bytes[i];
//        }
        return val;
    }


    /**
     *返回姓名中姓的hashcode
     * 确保每一个姓只有唯一确定的hashcode
     * @param name
     * @return
     */
   private  String getFirstNameHash(String name){
        String hashcode = "";
        char[] array = name.toCharArray();
        for(int i=0;i<array.length;i++){
            String strBinary = Integer.toBinaryString(array[i]);
            if(strBinary.length() == 7) {
                strBinary = 0 + strBinary;
            }
            if(strBinary.length() == 15){
                strBinary = 0 + strBinary;
            }
            hashcode = strBinary + hashcode;
            if(hashcode.length()==16){
                break;
            }
        }
        return hashcode;
    }

    /*****************************************以下是常用的hash函数******************************************************/
    /**
     * DJB 哈希函数
     *
     * @param str
     * @return
     */
    public static long DJBHash(String str) {
        long hash = 5381;
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        return hash;
    }

    /**
     * 原本String hashcode
     * @param value
     * @return
     */
    private String hashCode(char[] value) {
        long h = 0;
        if (value.length > 0) {
            char val[] = value;
            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
        }
        return h+"";
    }


    /**
     * .DEK Knuth
     *
     * @param str
     * @return
     */
    private   String DEKHash(String str) {
        long hash = str.length();
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
        }
        return hash+"";
    }

    private String BKDRHash(String str) {
        long seed = 131; // 31 131 1313 13131 131313 etc..
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }
        return hash+"";
    }

    private   String SDBMHash(String str) {
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash+"";
    }

    private  String PJWHash(String str) {
        long BitsInUnsignedInt = (long) (4 * 8);
        long ThreeQuarters = (long) ((BitsInUnsignedInt * 3) / 4);
        long OneEighth = (long) (BitsInUnsignedInt / 8);
        long HighBits = (long) (0xFFFFFFFF) << (BitsInUnsignedInt - OneEighth);
        long hash = 0;
        long test = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << OneEighth) + str.charAt(i);
            if ((test = hash & HighBits) != 0) {
                hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
            }
        }
        return hash+"";
    }

    /**
     * ELF 和PJW很相似，在Unix系统中使用的较多。
     *
     * @param str
     * @return
     */
    private String ELFHash(String str) {
        long hash = 0;
        long x = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = hash & 0xF0000000L) != 0) {
                hash ^= (x >> 24);
            }
            hash &= ~x;
        }
        return hash+"";
    }


    /**
     * 从Robert Sedgwicks的 Algorithms in C一书中得到了
     *
     * @param str
     * @return
     */
    private String RSHash(String str) {
        int b = 378551;
        int a = 63689;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a = a * b;
        }
        return hash+"";
    }

    /**
     * Justin Sobel写的一个位操作的哈希函数。
     *
     * @param str
     * @return
     */
    private String JSHash(String str) {
        long hash = 1315423911;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }
        return hash+"";
    }
}
