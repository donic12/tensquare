package com.tensquare.user.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class T {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("start:"+simpleDateFormat.format(new Date()));
        Set set=new HashSet();
       // HashMap map=new HashMap();
        for (int i=0;i<10000000;i++){
            set.add(i);
        }
        System.out.println("stop:"+simpleDateFormat.format(new Date()));

        //System.out.println(x(65));
//        String path = "D:/";
//        test(path);
    }

    public static long x(int num) throws Exception {
        if (num <= 0) {
            throw new Exception("必须大于0");
        }
        if (num == 1) {
            return 1;
        } else {
            return num * x(num - 1);
        }
    }

    private static void test(String path) {

        File file = new File(path);

        File[] fs = file.listFiles();

        if (fs == null) {
            return;
        }
        for (File f : fs) {
            if (f == null) {
                continue;
            }
            if (f.isFile() && f.getName().endsWith(".java")) {
                System.out.println(f.getAbsolutePath());
            } else if (f.isDirectory()) {
                test(f.getAbsolutePath());
            }
        }
    }
}
