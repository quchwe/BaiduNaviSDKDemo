package com.quchwe.cms.util.NormalUtil;

import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by quchwe on 2016/12/15 0015.
 */

public class ImageBase64Util {

    public static String GetImageStr(File imgFile){
        BufferedInputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try{
            in = new BufferedInputStream(new FileInputStream(imgFile));
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
        return Base64.encodeToString(data,Base64.DEFAULT);//返回Base64编码过的字节数组字符串
    }
}
