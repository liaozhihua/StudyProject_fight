package com.xm.studyproject.android.anim.propertyanimation;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

/**
 * 属性动画
 * ValueAnimator 类
 * 实现动画的原理：通过不断控制 值 的变化，再不断 手动 赋给对象的属性，从而实现动画效果
 */
public class ValueAnimatorDemo {
    public void ofInt(final Button view, int ... values) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(values);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setStartDelay(300);
        valueAnimator.setTarget(view);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                view.getLayoutParams().width = animatedValue;
                view.requestLayout();
            }
        });
        valueAnimator.start();
    }

    public void ofObject(final View view, Ponit ... values) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ObjectEvaluator(), values[0], values[1]);
        valueAnimator.setTarget(view);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Ponit animatedValue = (Ponit) animation.getAnimatedValue();
                view.getLayoutParams().width = (int) animatedValue.getPointX();
                view.requestLayout();
            }
        });
        valueAnimator.start();
    }
}
