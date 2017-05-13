package com.quchwe.cms.util.NormalUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;

import java.util.ArrayList;
import java.util.List;


public class DialogUtils {


    /**
     * 屏幕下方弹出菜单
     */
    public static class DialogMenu extends Dialog {
        private Button mCancel;
        private ListView mMenuItems;
        private ArrayAdapter<String> mAdapter;

        private View mRootView;
        private Animation mShowAnim;
        private Animation mDismissAnim;
        private boolean isDismissing;
        private MenuListener mMenuListener;


        public DialogMenu(Context context) {
            super(context, R.style.ActionSheetDialog);
            getWindow().setGravity(Gravity.BOTTOM);
            initView(context);
            //    initAnim(context);
            //取消按钮的事件
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });
            // 菜单的事件
            mMenuItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mMenuListener != null) {
                        mMenuListener.onItemSelected(position, mAdapter.getItem(position));
                        dismiss();
                    }
                }
            });
            // 对话框取消的回调
            setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mMenuListener != null) {
                        mMenuListener.onCancel();
                    }
                }
            });
        }

        private void initView(Context context) {
            mRootView = View.inflate(context, R.layout.dialog_action_sheet, null);
            mCancel = (Button) mRootView.findViewById(R.id.menu_cancel);
            mMenuItems = (ListView) mRootView.findViewById(R.id.menu_items);
            mAdapter = new ArrayAdapter<String>(context, R.layout.item_textview_center) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView view = (TextView) super.getView(position, convertView, parent);
//                    LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.textview_144,null);
                    setBackground(position, view);
                    return view;
                }

                private void setBackground(int position, View view) {
                    // int count = getCount();
                    view.setBackgroundResource(R.drawable.menu_item_middle);
                    /*if (count == 1) {
                        view.setBackgroundResource(R.drawable.menu_item_single);
                    } else if (position == 0) {
                        view.setBackgroundResource(R.drawable.menu_item_top);
                    } else if (position == count - 1) {
                        view.setBackgroundResource(R.drawable.menu_item_bottom);
                    } else {
                        view.setBackgroundResource(R.drawable.menu_item_middle);
                    }*/
                }
            };
            mMenuItems.setAdapter(mAdapter);
            initAnim(context);
            this.setContentView(mRootView);

        }

        private void initAnim(Context context) {
            mShowAnim = AnimationUtils.loadAnimation(context, R.anim.translate_up);
            mDismissAnim = AnimationUtils.loadAnimation(context, R.anim.translate_down);
            mDismissAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    dismissMe();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }


        public DialogMenu addMenuItem(String items) {
            mAdapter.add(items);
            return this;
        }


        public void toggle() {
            if (isShowing()) {
                dismiss();
            } else {
                show();
            }
        }

        @Override
        public void show() {
            super.show();

//            mRootView.startAnimation(mShowAnim);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void dismiss() {
            if (isDismissing) {
                return;
            }
            isDismissing = true;
            mRootView.startAnimation(mDismissAnim);
        }

        private void dismissMe() {
            super.dismiss();
            isDismissing = false;
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                dismiss();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

        public interface MenuListener {
            void onItemSelected(int position, String item);

            void onCancel();
        }

        public MenuListener getMenuListener() {
            return mMenuListener;
        }

        public void setMenuListener(MenuListener menuListener) {
            mMenuListener = menuListener;
        }


    }

    public static class DialogMenu2 extends Dialog {
        private Button mCancel;
        private ListView mMenuItems;
        private ArrayAdapter<String> mAdapter;

        private View mRootView;
        private Animation mShowAnim;
        private Animation mDismissAnim;
        private boolean isDismissing;
        private MenuListener mMenuListener;

        private List<String> list;
        private Context context;

        public DialogMenu2(Context context) {
            super(context, R.style.ActionSheetDialog);
            this.context = context;
            getWindow().setGravity(Gravity.BOTTOM);
            list = new ArrayList<String>();
            initView();
            //    initAnim(context);
            //取消按钮的事件

        }

        public void initView() {
            mRootView = View.inflate(context, R.layout.dialog_action_sheet, null);
            mCancel = (Button) mRootView.findViewById(R.id.menu_cancel);
            mMenuItems = (ListView) mRootView.findViewById(R.id.menu_items);
            mAdapter = new ArrayAdapter<String>(context, R.layout.item_textview_center, list) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
//                    TextView view = (TextView) super.getView(position, convertView, parent);
                    LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.textview_144, null);
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    textView.setText(list.get(position));
                    setBackground(position, view);
                    if (list.get(position).equals("删除")) {
                        textView.setTextColor(Color.RED);
                    }
                    if (list.get(position).equals("清空所有消息")) {
                        textView.setTextColor(Color.RED);
                    }
                    return view;
                }

                private void setBackground(int position, View view) {
                    // int count = getCount();
                    view.setBackgroundResource(R.drawable.menu_item_middle);
                    /*if (count == 1) {
                        view.setBackgroundResource(R.drawable.menu_item_single);
                    } else if (position == 0) {
                        view.setBackgroundResource(R.drawable.menu_item_top);
                    } else if (position == count - 1) {
                        view.setBackgroundResource(R.drawable.menu_item_bottom);
                    } else {
                        view.setBackgroundResource(R.drawable.menu_item_middle);
                    }*/
                }
            };
            mMenuItems.setAdapter(mAdapter);
            initAnim(context);
            this.setContentView(mRootView);

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });
            // 菜单的事件
            mMenuItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mMenuListener != null) {
                        mMenuListener.onItemSelected(position, mAdapter.getItem(position));
                        dismiss();
                    }
                }
            });
            // 对话框取消的回调
            setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mMenuListener != null) {
                        mMenuListener.onCancel();
                    }
                }
            });

        }

        private void initAnim(Context context) {
            mShowAnim = AnimationUtils.loadAnimation(context, R.anim.translate_up);
            mDismissAnim = AnimationUtils.loadAnimation(context, R.anim.translate_down);
            mDismissAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    dismissMe();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }


        public DialogMenu2 addMenuItem(String items) {
            list.add(items);
            return this;
        }


        public void toggle() {
            if (isShowing()) {
                dismiss();
            } else {
                show();
            }
        }

        @Override
        public void show() {
            super.show();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void dismiss() {
            if (isDismissing) {
                return;
            }
            isDismissing = true;
            mRootView.startAnimation(mDismissAnim);
        }

        private void dismissMe() {
            super.dismiss();
            isDismissing = false;
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                dismiss();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

        public interface MenuListener {
            void onItemSelected(int position, String item);

            void onCancel();
        }

        public MenuListener getMenuListener() {
            return mMenuListener;
        }

        public void setMenuListener(MenuListener menuListener) {
            mMenuListener = menuListener;
        }


    }

    public static class DialogTextCenter {
        private final Context mContext;
        private final String text;
        private final String buttonString;
        private final OnDialogClickLister mListener;

        public DialogTextCenter(Context mContext, String text, String buttonString, OnDialogClickLister mListener) {
            this.mContext = mContext;
            this.text = text;
            this.buttonString = buttonString;
            this.mListener = mListener;
            showNoOwnerCircleDialog();
        }

        private void showNoOwnerCircleDialog() {
            final AlertDialog dialog = new AlertDialog.Builder(mContext).create();

            dialog.show();
            Window window = dialog.getWindow();

            window.setContentView(R.layout.dialog_owner_circle);
            TextView t = (TextView) window.findViewById(R.id.tv_home_dialog);
            t.setText(text);

            final Button confirm = (Button) window.findViewById(R.id.confirm);
            confirm.setText(buttonString);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (mListener!=null){
                        mListener.onClick();
                    }
                }
            });

        }

      public  interface OnDialogClickLister {
            void onClick();
        }
    }


}


