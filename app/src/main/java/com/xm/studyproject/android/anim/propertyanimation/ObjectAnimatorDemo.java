package com.xm.studyproject.android.anim.propertyanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

public class ObjectAnimatorDemo {
    public void ofFloat(Object object,String property,float ... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(object, property, values);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }
}
