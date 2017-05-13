package com.quchwe.cms.data.remote;

import android.content.Context;
import android.os.Environment;

import com.baidu.navi.sdkdemo.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.quchwe.cms.data.SharedPreferenceUtil.SharedPreferencesUtil;
import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.data.beans.RepairInfo;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.data.beans.BaseRequest;
import com.quchwe.cms.data.local.LocalSource;
import com.quchwe.cms.myinfo.IInfoList;
import com.quchwe.cms.util.NormalUtil.NormalUtil;
import com.quchwe.cms.util.NormalUtil.StringUtils;
import com.quchwe.cms.util.net.ApiModule;
import com.quchwe.cms.util.net.SchedulerProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by quchwe on 2017/4/5 0005.
 */

public class RemoteSource {
    private static Gson gson;
    private final Context mContext;
    private final LocalSource mLocalSource;
    private static RemoteSource mRemoteSource;

    private RemoteSource(Context context) {
        this.mContext = context;
        mLocalSource = LocalSource.instance(context);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        gson = builder.create();


    }

    public static synchronized RemoteSource instance(Context context) {
        if (mRemoteSource == null) {
            mRemoteSource = new RemoteSource(context);
        }
        return mRemoteSource;
    }

    public Observable<BaseBean<SysUser>> login(SysUser user) {
        BaseRequest<SysUser> bean = new BaseRequest<>();
        bean.setRequestBean(user);
        bean = NormalUtil.md5(bean);
        return ApiModule.login().login(bean).
                subscribeOn(Schedulers.io()).
                observeOn(SchedulerProvider.ui()).
                flatMap(new Func1<ResponseBody, Observable<BaseBean<SysUser>>>() {
                            @Override
                            public Observable<BaseBean<SysUser>> call(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    System.out.println(s);
                                    BaseBean<SysUser> baseBean =
                                            gson.fromJson(s, new TypeToken<BaseBean<SysUser>>() {
                                            }.getType());
                                    return Observable.just(baseBean);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }
                );
    }

    public Observable<BaseBean<SysUser>> register(SysUser user) {
        BaseRequest<SysUser> bean = new BaseRequest<>();
        bean.setRequestBean(user);
        bean = NormalUtil.md5(bean);
        return ApiModule.register().register(bean).
                subscribeOn(Schedulers.io()).
                observeOn(SchedulerProvider.ui()).
                flatMap(new Func1<ResponseBody, Observable<BaseBean<SysUser>>>() {
                            @Override
                            public Observable<BaseBean<SysUser>> call(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    System.out.println(s);
                                    BaseBean<SysUser> baseBean =
                                            gson.fromJson(s, new TypeToken<BaseBean<SysUser>>() {
                                            }.getType());
                                    return Observable.just(baseBean);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }
                );
    }

    public Observable<BaseBean<String>> createRepair(String phoneNumber, String type, String description, String drivId, File... files) {

        Map<String, RequestBody> photos = new HashMap<>();

        for (int i = 0; i < files.length; i++) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/jpg"), files[i]);
            photos.put("file" + i + "\"; filename=\"" + files[i].getName(), photo);
        }


        RequestBody phoneBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), phoneNumber);
        RequestBody typeBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), type);
        RequestBody descBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), description);
        RequestBody drivingBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), drivId);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("phoneNumber", phoneBody);
        map.put("accidentType", typeBody);
        map.put("description", descBody);
        map.put("drivingId", drivingBody);

        return ApiModule.upload().createRepair(map, photos).subscribeOn(SchedulerProvider.io())

                .observeOn(SchedulerProvider.ui())
                .flatMap(new Func1<ResponseBody, Observable<BaseBean<String>>>() {
                    @Override
                    public Observable<BaseBean<String>> call(ResponseBody responseBody) {
                        String s = null;
                        try {
                            s = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(s);
                        BaseBean<String> baseBean =
                                gson.fromJson(s, new TypeToken<BaseBean<String>>() {
                                }.getType());
                        return Observable.just(baseBean);
                    }
                });


    }

    public Observable<BaseBean<SysUser>> updateUserInfo(SysUser user, File headImage) {


        RequestBody phoneBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), user.getPhoneNumber());
        HashMap<String, RequestBody> map = new HashMap<>();
        if (user.getCarType() != null) {
            RequestBody typeBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getCarType());
            map.put("carType", typeBody);
        }
        RequestBody ageBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), user.getAge() + "");
        if (user.getDrivingLicenseId() != null) {
            RequestBody drivingBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getDrivingLicenseId());
            map.put("drivingId", drivingBody);
        }
        if (user.getSex() != null) {
            RequestBody sexBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getSex());
            map.put("sex", sexBody);
        }
        if (user.getEmail() != null) {
            RequestBody emailBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getEmail());
            map.put("email", emailBody);
        }
        if (user.getPurchaseDate() != null) {
            RequestBody purChaseDateBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getPurchaseDate().toString());
            map.put("purchaseDate", purChaseDateBody);
        }
        if (user.getAddress() != null) {
            RequestBody addressBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getAddress());
            map.put("address", addressBody);
        }
        if (user.getUserName() != null) {
            RequestBody nameBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getUserName());
            map.put("nickName", nameBody);
        }
        if (user.getSignature() != null) {
            RequestBody signatureBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"), user.getSignature());
            map.put("signature", signatureBody);
        }
        map.put("phoneNumber", phoneBody);


        map.put("age", ageBody);

        Map<String, RequestBody> photos = new HashMap<>();
        if (headImage != null) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/jpg"), headImage);
            photos.put("headImage\"; filename=\"" + headImage.getName(), photo);
        }
        try {
            return ApiModule.updateUserInfo().update(map, photos).subscribeOn(SchedulerProvider.io())

                    .observeOn(SchedulerProvider.ui())
                    .flatMap(new Func1<ResponseBody, Observable<BaseBean<SysUser>>>() {
                        @Override
                        public Observable<BaseBean<SysUser>> call(ResponseBody responseBody) {
                            String s = null;

                            try {
                                s = responseBody.string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(s);
                            BaseBean<SysUser> baseBean =
                                    gson.fromJson(s, new TypeToken<BaseBean<SysUser>>() {
                                    }.getType());
                            return Observable.just(baseBean);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Observable<BaseBean<List<IInfoList>>> getMyCars(Context mContext,String carId,int i){

        String userToken = (String)SharedPreferencesUtil.getData(mContext,SharedPreferencesUtil.USER_TOKEN,"");
        String phoneNumber = (String)SharedPreferencesUtil.getData(mContext,SharedPreferencesUtil.MOBILE,"");

        if (i==0){
            phoneNumber = null;
        }

        if (StringUtils.isEmptyString(userToken)){
            return null;
        }
        return ApiModule.getCars().getMyCars(phoneNumber,userToken,carId).subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .flatMap(new Func1<ResponseBody, Observable<BaseBean<List<IInfoList>>>>() {
                    @Override
                    public Observable<BaseBean<List<IInfoList>>> call(ResponseBody responseBody) {
                        String s = null;

                        try {
                            s = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(s);
                        BaseBean<List<IInfoList>> baseBean =
                                gson.fromJson(s, new TypeToken<BaseBean<List<Car>>>() {
                                }.getType());
                        return Observable.just(baseBean);
                    }
                });
    }

    public Observable<BaseBean<String>> updateCar(Context mContext,Car car){

        BaseRequest<Car> request = new BaseRequest<>();
        String userToken = (String)SharedPreferencesUtil.getData(mContext,SharedPreferencesUtil.USER_TOKEN,"");
        request.setUserToken(userToken);
        request.setPhoneNumber(car.getPhoneNumber());
        request.setRequestBean(car);

        return ApiModule.updateCars().updateCar(request).
                subscribeOn(Schedulers.io()).
                observeOn(SchedulerProvider.ui()).
                flatMap(new Func1<ResponseBody, Observable<BaseBean<String>>>() {
                            @Override
                            public Observable<BaseBean<String>> call(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    System.out.println(s);
                                    BaseBean<String> baseBean =
                                            gson.fromJson(s, new TypeToken<BaseBean<String>>() {
                                            }.getType());
                                    return Observable.just(baseBean);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }
                );

    }

    public Observable<BaseBean<List<IInfoList>>> getRepair(Context mContext){
        String userToken = (String)SharedPreferencesUtil.getData(mContext,SharedPreferencesUtil.USER_TOKEN,"");
        String phoneNumber = (String)SharedPreferencesUtil.getData(mContext,SharedPreferencesUtil.MOBILE,"");

        if (StringUtils.isEmptyString(userToken)||StringUtils.isEmptyString(phoneNumber)){
            return null;
        }

        return ApiModule.getRepair().getRepair(phoneNumber,userToken).subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .flatMap(new Func1<ResponseBody, Observable<BaseBean<List<IInfoList>>>>() {
                    @Override
                    public Observable<BaseBean<List<IInfoList>>> call(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            System.out.println(s);
                            BaseBean<List<IInfoList>> baseBean =
                                    gson.fromJson(s, new TypeToken<BaseBean<List<RepairInfo>>>() {
                                    }.getType());
                            return Observable.just(baseBean);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

}
