package com.quchwe.cms.util.select_image.AllPhotoActivity;



import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quchwe on 2016/8/6 0006.
 */
public interface SeletPhotoContract {
    interface Presenter extends IBasePresenter {


        void setAdapter();

        void getAllImageItems();



    }
    interface View extends IBaseView<Presenter> {

        void setPictureSelectCount(int count);

        void toPreview();
        void setRecylerView(List<String> paths);
        void currentSelected(String picturePath);
        void setSlectPicturePath(ArrayList<String> paths);
    }
}
