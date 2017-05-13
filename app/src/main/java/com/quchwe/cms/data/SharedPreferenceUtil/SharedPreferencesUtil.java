package com.quchwe.cms.data.SharedPreferenceUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.quchwe.cms.data.beans.SysUser;

/**
 * Created by quchwe on 2016/12/2 0002.
 */

public class SharedPreferencesUtil {
    //存储的sharedpreferences文件名

    private static final String FILE_NAME = "save_file_name";
    public static final String PASSWORD_EDIT = "passwordEdit";

    public static final String NICKNAME = "nickname";
    public static final String SEX = "sex";
    public static final String HEADIMGURL = "headimgurl";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";

    public static final String DH_ADDRESS = "address";
    public static final String DRIVING_ID = "drivingId";
    public static final String USER_NAME = "userName";
    public static final String SIGNATURE = "signature";
    public static final String AGE = "age";
    public static final String USER_TOKEN = "user_token";


    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String key, Object data) {

        if (data == null) {
            return;
        }

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        String type = data.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }

        editor.commit();
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (FILE_NAME, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }

    public static void clearData(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void saveUserInfo(Context mContext, SysUser user) {
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.SIGNATURE, user.getSignature());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.SEX, user.getSex());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.AGE, user.getAge());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.DH_ADDRESS, user.getAddress());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.NICKNAME, user.getUserName());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.MOBILE, user.getPhoneNumber());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.PASSWORD_EDIT, user.getPassword());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.USER_NAME, user.getUserName());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.DRIVING_ID, user.getDrivingLicenseId());
        saveData(mContext,USER_TOKEN,user.getUserToken());
        SharedPreferencesUtil.saveData(mContext, SharedPreferencesUtil.HEADIMGURL, user.getHeadImage() == null ? "" : user.getHeadImage());
    }

    public static SysUser getUserInfo(Context mContext) {
        SysUser user = new SysUser();
        user.setHeadImage((String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.HEADIMGURL, ""));
        user.setUserName((String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.USER_NAME, ""));
        user.setPhoneNumber((String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.MOBILE, ""));
        user.setAddress((String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.DH_ADDRESS, ""));
        user.setSex((String) SharedPreferencesUtil.getData(mContext, SharedPreferencesUtil.SEX, ""));
        user.setSignature((String) getData(mContext, SIGNATURE, ""));
        user.setAge((int) getData(mContext, AGE, 0));
        user.setDrivingLicenseId((String) getData(mContext, DRIVING_ID, ""));
        user.setUserToken((String)getData(mContext,USER_TOKEN,""));

        return user;

    }
}
