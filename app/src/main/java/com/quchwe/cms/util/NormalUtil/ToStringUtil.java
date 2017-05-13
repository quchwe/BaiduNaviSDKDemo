package com.quchwe.cms.util.NormalUtil;

import java.lang.reflect.Field;

/**
 * Created by quchwe on 2016/12/3 0003.
 */

public class ToStringUtil {

    /**
     * 设置签名toString方法，
     * example
     * public class user{
     *     private String uid;
     *     private String  pwd;
     * }
     *
     * user u = new user("123","123");
     * ToStringutil.toString(u);
     *
     * uid=123,pwd=123
     * @param o
     * @return
     */
    public static String toString(Object o){
        Class  c = o.getClass();
        //获取所有的属性?
        Field[] fs = c.getDeclaredFields();
        //定义可变长的字符串，用来存储属性
        StringBuffer sb = new StringBuffer();
        //通过追加的方法，将每个属性拼接到此字符串中
        //里边的每一个属性
        for(Field field:fs){
            System.out.println(field.getName());
            field.setAccessible(true);//获得private值
            // 属性的名字
            if (field.getName().equals("signature")){
                continue;
            }
            sb.append(field.getName()+"=");
            try {
                sb.append(field.get(o));//属性值
                sb.append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        return sb.toString();
    }
}
