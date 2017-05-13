package com.quchwe.cms.util.select_image.AllPhotoActivity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.baidu.navi.sdkdemo.R;
import com.bumptech.glide.Glide;
import com.quchwe.cms.util.NormalUtil.ToastUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/8/6 0006.
 */
public class ImageSelectAdapter extends RecyclerView.Adapter<ImageSelectAdapter.ImageSelectViewHoler> {

    List<String> paths;
    private final Context mContext;
    private final int limitCount;

    private ArrayList<String> mSelectedImage = new ArrayList<>();
    private int pos = -1;
    private String currentSelectPath = null;
    ArrayList<String> selectedImages = new ArrayList<>();

    public ImageSelectAdapter(Context context, List<String> paths, ArrayList<String> selectedList, int limitCount) {
        this.mContext = context;
        this.paths = paths;
        this.selectedImages = selectedList;
        this.limitCount = limitCount;

    }

    @Override
    public ImageSelectViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageSelectViewHoler viewHoler = new ImageSelectViewHoler(LayoutInflater.from(mContext).
                inflate(R.layout.item_all_images, parent, false));
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(final ImageSelectViewHoler holder, final int position) {
        final String currentPath = paths.get(position);
        //System.out.println(currentPath);
        Glide.with(mContext)
                .load(currentPath)
                .into(holder.image);
        if (selectedImages != null) {
            mSelectedImage = selectedImages;
        }
        if (selectedImages != null && selectedImages.contains(currentPath)) {
            holder.image.setColorFilter(Color.parseColor("#77000000"));
            holder.selectBox.setChecked(true);
        } else {
            holder.image.setColorFilter(null);
            holder.selectBox.setChecked(false);
        }
        final AllPhotosActivity activity = (AllPhotosActivity) mContext;

        holder.selectBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.selectBox.isChecked()) {
                    // 已经选择过该图片
                    if (mSelectedImage.contains(currentPath)) {
                        mSelectedImage.remove(currentPath);
                        holder.selectBox.setChecked(false);
                        holder.image.setColorFilter(null);
                    } else
                    // 未选择该图片
                    {
                        if (mSelectedImage.size() == limitCount) {
                            String sFormat = mContext.getString(R.string.max_image);
                            String maxImage = String.format(sFormat, limitCount);
                            ToastUtil.showToast(mContext, maxImage);
                            holder.selectBox.setChecked(false);
                            return;
                        }
                        mSelectedImage.add(currentPath);
                        holder.selectBox.setChecked(true);
                        holder.image.setColorFilter(Color.parseColor("#77000000"));
                    }
                    activity.setPictureSelectCount(mSelectedImage.size());
                    currentSelectPath = currentPath;
                    pos = position;

                    activity.setSelectedPos(pos, mSelectedImage.size() - 1);
                    activity.setSlectPicturePath(mSelectedImage);

                }
                selectedImages = mSelectedImage;
            }
        });
        //设置ImageView的点击事件
        // holder.cirecleImage.setFocusable(false);
        holder.image.setOnClickListener(new View.OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {


                activity.toPreview();
            }
        });

    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public List<String> getmSelectedImage() {
        //  Log.d("adapter mSelectedImage",mSelectedImage.size()+"");
        return this.mSelectedImage;
    }

    public String getCurrentSelect() {
        return this.currentSelectPath;
    }

    class ImageSelectViewHoler extends RecyclerView.ViewHolder {
        public ImageView image;
        public CheckBox selectBox;

        public ImageSelectViewHoler(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_image);
            selectBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

    public int getPosition() {
        return pos;
    }

    public void setSelectedImages(List<String> images) {
        selectedImages = (ArrayList) images;
    }
}
