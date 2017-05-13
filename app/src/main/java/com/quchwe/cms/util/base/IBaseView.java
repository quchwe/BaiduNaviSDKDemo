package com.quchwe.cms.util.base;

/**
 * Created by quchwe on 2016/11/10 0010.
 */

public interface IBaseView<T extends IBasePresenter> {
   void setPresenter(T presenter);
}
