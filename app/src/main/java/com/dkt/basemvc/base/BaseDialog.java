package com.dkt.basemvc.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dkt.basemvc.R;
import com.dkt.basemvc.utils.DensityUtils;


/**
 * 自定义dialog基类
 */
public abstract class BaseDialog extends Dialog {

    private boolean alignBottom = false;    //是否位于底部
    private int margin = 15;                   //设置与边缘的间距
    private boolean isCorner = true;            //是否设置圆角边框
    private int mDialogColor = Color.WHITE;     //dialog的背景颜色

    public BaseDialog(Context context) {
        this(context, false);
    }

    public BaseDialog(Context context, boolean alignBottom) {
        super(context, R.style.BaseDialog);
        this.alignBottom = alignBottom;
        if (alignBottom) {
            WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
            layoutParams.x = 5;
            layoutParams.y = 5;
            onWindowAttributesChanged(layoutParams);
            getWindow().setWindowAnimations(R.style.DialogAnim);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(getContext());
        View view = contentView();
        if (alignBottom) {
            view.setMinimumWidth(getContext().getResources().getDisplayMetrics().widthPixels - 10);
        }
        layout.addView(view);
        /**
         *给它设置边距，不让它左右两边占满屏幕；如果是底部弹出来的话，就是占满整个屏幕
         */
        if (view instanceof ViewGroup) {
            if (!alignBottom) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
                int intendMargin = DensityUtils.dp2px(getContext(), margin);
                lp.leftMargin = intendMargin;
                lp.rightMargin = intendMargin;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(getDialogDrawable());
            } else {
                view.setBackgroundDrawable(getDialogDrawable());
            }
        }
        setContentView(layout);
    }

    /**
     * 创建一个背景
     */
    private Drawable getDialogDrawable() {
        GradientDrawable mGradinentDrawble = new GradientDrawable();
        mGradinentDrawble.setDither(true);
        mGradinentDrawble.setShape(GradientDrawable.RECTANGLE);
        mGradinentDrawble.setColor(mDialogColor);
        if (isCorner) {
            mGradinentDrawble.setCornerRadius(DensityUtils.dp2px(getContext(), 10f));
        }
        return mGradinentDrawble;
    }

    /**
     * 设置是否是圆角
     *
     * @return
     */
    public void setIsCorner(boolean isCorner) {
        this.isCorner = isCorner;
    }

    /**
     * @param margin 设置间距
     * @return
     **/
    public void setMargin(int margin) {
        this.margin = margin;
    }

    /**
     * @param mDialogColor 设置dialog的背景颜色
     */
    public void setmDialogColor(int mDialogColor) {
        this.mDialogColor = mDialogColor;
    }

    protected abstract View contentView();

}
