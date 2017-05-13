package com.quchwe.cms.data.local;

import android.content.Context;

/**
 * Created by quchwe on 2017/4/5 0005.
 */

public class LocalSource {
    private final Context mContext;
    private static  LocalSource mLocalSource;
    private LocalSource(Context context){
        this.mContext = context;
    }

    public static LocalSource instance(Context context){
        if (mLocalSource==null){
            mLocalSource = new  LocalSource(context);
        }
        return mLocalSource;
    }
}
