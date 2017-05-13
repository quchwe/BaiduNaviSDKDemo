package com.quchwe.cms.util.NormalUtil;



import com.quchwe.cms.data.beans.BaseRequest;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by quchwe on 2016/11/24 0024.
 * 常用工具类
 */

public class NormalUtil {

    public static final String AND = "&";
    public static final String GET = "GET";
    public static final String POST = "POST";
    /**
     * 判断是否为手机号
     *
     * @param tel 输入字符串
     * @return boolean
     */
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        //System.out.println(m.matches() + "---");
        return m.matches();
    }

    /**
     * 判断是否为车牌号
     * @param id
     * @return
     */
    public static boolean isDrivingId(String id){
        Pattern p = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");
        Matcher m = p.matcher(id);

        return m.matches();
    }
    /**
     * 生成随机字符串
     * @param length 生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "_abcdefghijklmnopqrstuvwxyz0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成一个6-16之间一个随机数
     * @return
     */
    public static int getRandomNumber(){
        return 6+(int)(Math.random()*10);

    }

    public static String signatureUtil(Object o,String requestType){
        List<String> strings = Arrays.asList(o.toString().split(","));
        for (String s:strings){
            System.out.println(s);
        }
        Collections.sort(strings);
        StringBuilder sb = new StringBuilder();
        sb.append("appSecret");
        sb.append(AND);
        sb.append(requestType);
        sb.append(AND);
        for (int i=0;i<strings.size();i++) {
            if (strings.get(i).contains("=null"))
                continue;
            sb.append(strings.get(i));
            if (i<strings.size()-1) {
                sb.append(AND);
            }
        }
        try {
            String s = new String(sb.toString().getBytes(),"utf-8");
            return EncryptionUtil.shaEncryption(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       return null;
    }

    public static Map<String,Integer> getCityId(){
        String[] citys = {"无锡市", "苏州市", "太原市","无锡","太原","苏州"};
        Map<String,Integer> map = new HashMap();
        map.put(citys[0],222);
        map.put(citys[1],221);
        map.put(citys[2],300);
        map.put(citys[3],222);
        map.put(citys[4],221);
        map.put(citys[5],300);
        return map;
    }


    public  static <T> BaseRequest<T> md5(BaseRequest<T> request){
        T bean = request.getRequestBean();
        request.setUserToken("android");
        request.setMd5(EncryptionUtil.md5Encryption(request.getUserToken()+request.getTimeStamp()+bean.toString()));
        return request;
    }
}
