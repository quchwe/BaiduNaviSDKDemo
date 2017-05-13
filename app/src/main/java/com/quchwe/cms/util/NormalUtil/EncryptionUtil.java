package com.quchwe.cms.util.NormalUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by quchaowei
 * 32位md5
 *
 */
public class EncryptionUtil {


    public static String md5Encryption(String plainText) {
        String re_md5 = new String();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(plainText.getBytes());
        byte b[] = md.digest();

        int i;

        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        re_md5 = buf.toString();

        return re_md5;
    }

    public static String shaEncryption(String plainText){

        if (plainText == null || plainText.length() == 0) {
            return null ;
        }
        char hexDigits [] = { '0' , '1' , '2' , '3' , '4', '5' , '6' , '7' , '8' , '9' ,
                'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update( plainText.getBytes( "UTF-8")); byte[] md = mdTemp .digest();
            int j = md .length ; char buf [] = new char[ j * 2];
            int k = 0;
            for (int i = 0; i < j ; i ++)   {
                byte byte0 = md [i ];
                buf[ k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[ k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf );
        } catch (Exception e ) {
            return null ;
        }
    }

    }




