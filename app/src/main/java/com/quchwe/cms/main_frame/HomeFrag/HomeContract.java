package com.quchwe.cms.main_frame.HomeFrag;

import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

import java.io.File;

/**
 * Created by YSten on 2017/4/6.
 */

public interface HomeContract  {
    interface Presenter extends IBasePresenter{

        void upload(String type,String desc,File... files);
    }
    interface View extends IBaseView<Presenter>{

    }
}
