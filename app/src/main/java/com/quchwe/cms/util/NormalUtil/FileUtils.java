package com.quchwe.cms.util.NormalUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.quchwe.cms.util.compressor.Compressor;
import com.quchwe.cms.util.net.SchedulerProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by quchwe on 2017/4/11 0011.
 */

public class FileUtils {


    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static File createImageFile() throws IOException {
        // Create an cirecleImage file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    public static Observable<File[]> compressFiles(final Context mContext,final File... files) {

        Observable<File[]> stringObservable = Observable.defer(new Func0<Observable<File[]>>() {
            @Override
            public Observable<File[]> call() {
                return Observable.just(files);
            }
        });


        return stringObservable.subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .flatMap(new Func1<File[], Observable<File[]>>() {
                    @Override
                    public Observable<File[]> call(File[] files) {
                        File[] compressFiles = new File[files.length];
                        for (int i = 0; i < files.length; i++) {
                            compressFiles[i] = Compressor.getDefault(mContext)
                                    .compressToFile(files[i]);
                        }
                        return Observable.just(compressFiles);
                    }
                });

    }
}
