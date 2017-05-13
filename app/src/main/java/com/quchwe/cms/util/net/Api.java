package com.quchwe.cms.util.net;


import com.quchwe.cms.data.beans.BaseRequest;
import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.data.beans.SysUser;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by quchwe on 2016/11/19 0019.
 */

public interface Api {


    interface Upload{
        @POST("repair/create_repair")
        @Multipart
        Observable<ResponseBody> createRepair(@PartMap Map<String, RequestBody> partMap,
                                              @PartMap Map<String, RequestBody> params);
    }
    interface UpdateUserInfo{
        @POST("user/updateUserInfo")
        @Multipart
        Observable<ResponseBody> update(@PartMap Map<String, RequestBody> partMap,
                                              @PartMap Map<String, RequestBody> params);
    }


    interface Login{
        @POST("user/login")
        Observable<ResponseBody> login(@Body BaseRequest<SysUser> user);
    }
    interface Register{
        @POST("user/register")
        Observable<ResponseBody> register(@Body BaseRequest<SysUser> user);
    }

    interface GetCars{
        @POST("car/get")
        @FormUrlEncoded
        Observable<ResponseBody> getMyCars(@Field("phoneNumber")String phoneNumber,@Field("userToken")String userToken,@Field("carId")String carId);
    }

    interface UpdateCars{
        @POST("car/update")
        Observable<ResponseBody> updateCar(@Body BaseRequest<Car> car);
    }

    interface GetRepair{
        @POST("repair/get")
        @FormUrlEncoded
        Observable<ResponseBody> getRepair(@Field("phoneNumber")String phoneNumber,@Field("userToken")String userToken);

    }


}
