package com.xm.studyproject.android.anim;

import android.animation.TimeInterpolator;

/**
 * 先减速再加速的差值器
 */
public class DecelerateAccelerateInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        //前部分采用正弦
        // 使用正弦函数来实现先减速后加速的功能，逻辑如下：
        // 因为正弦函数初始弧度变化值非常大，刚好和余弦函数是相反的
        // 随着弧度的增加，正弦函数的变化值也会逐渐变小，这样也就实现了减速的效果。
        // 当弧度大于π/2之后，整个过程相反了过来，现在正弦函数的弧度变化值非常小，渐渐随着弧度继续增加，变化值越来越大，弧度到π时结束，这样从0过度到π，也就实现了先减速后加速的效果
        float result;
        if (input <= 0.5f) {
            result = (float) (Math.sin(input * Math.PI) / 2);
        } else {
            result = (float) ((2 + Math.sin((input + 1) * Math.PI)) / 2);
        }
        return result;
    }
}
