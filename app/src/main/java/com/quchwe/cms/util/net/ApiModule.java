package com.quchwe.cms.util.net;

import retrofit2.Retrofit;

/**
 * Created by quchwe on 2016/11/20 0020.
 */

public class ApiModule {

   private static final Retrofit retrofit = RetrofitUtil.retrofit();



    public static Api.Login login(){
        return retrofit.create(Api.Login.class);
    }

    public static Api.Register register(){
        return retrofit.create(Api.Register.class);
    }

    public static Api.Upload upload(){
        return retrofit.create((Api.Upload.class));
    }

    public static Api.UpdateUserInfo updateUserInfo(){
        return retrofit.create(Api.UpdateUserInfo.class);
    }

    public static Api.GetCars getCars(){
        return retrofit.create(Api.GetCars.class);
    }

    public static Api.UpdateCars updateCars(){
        return retrofit.create(Api.UpdateCars.class);
    }

    public static Api.GetRepair getRepair(){
        return retrofit.create(Api.GetRepair.class);
    }
}
