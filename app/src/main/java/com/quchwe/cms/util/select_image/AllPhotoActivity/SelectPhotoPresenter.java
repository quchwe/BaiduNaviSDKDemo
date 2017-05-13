package com.quchwe.cms.util.select_image.AllPhotoActivity;

import android.content.Context;
import android.util.Log;


import com.quchwe.cms.util.album.AlbumHelper;
import com.quchwe.cms.util.album.ImageBucket;
import com.quchwe.cms.util.album.ImageItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by quchwe on 2016/8/6 0006.
 */
public class SelectPhotoPresenter implements SeletPhotoContract.Presenter {

    private final Context mContext;
    private final SeletPhotoContract.View mView;

    private List<ImageItem> imageItemList = new ArrayList<>();
    public SelectPhotoPresenter(Context context, SeletPhotoContract.View view){
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
        setAdapter();
    }

    @Override
    public void setAdapter() {
        getAllImageItems();
       List<String> allPaths = new ArrayList<>();
        for (ImageItem item:imageItemList){
            allPaths.add(item.getImagePath());
        }
        mView.setRecylerView(allPaths);
    }

    @Override
    public void getAllImageItems() {
        AlbumHelper helper = new AlbumHelper();
        helper.init(mContext);
        List<ImageBucket> imageBuckets = helper.getImagesBucketList(false);
        for (ImageBucket bucket:imageBuckets){
            imageItemList.addAll(bucket.imageList);
        }
        Log.d(TAG, "getAllImageItems: "+imageItemList.size());
        Collections.sort(imageItemList,new ImageComparator());
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    /**
     * 将所有获取的图片依照修改时间排序
     */
    private class ImageComparator implements Comparator<ImageItem> {

        @Override
        public int compare(ImageItem lhs, ImageItem rhs) {
            if (lhs.getModifyTime() < rhs.getModifyTime()) {
                return 1;//最后修改的照片在前
            } else {
                return -1;
            }
        }
    }
}
