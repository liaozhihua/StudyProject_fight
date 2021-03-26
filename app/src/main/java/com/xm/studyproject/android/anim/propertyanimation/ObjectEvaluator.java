package com.xm.studyproject.android.anim.propertyanimation;

import android.animation.TypeEvaluator;

public class ObjectEvaluator implements TypeEvaluator {
    @Override
    public Ponit evaluate(float fraction, Object startValue, Object endValue) {
        float startX = ((Ponit) startValue).getPointX();
        float startY = ((Ponit) startValue).getPointY();
        float endX = ((Ponit) endValue).getPointX();
        float endY = ((Ponit) endValue).getPointY();
        float x =  startX+fraction*(endX-startX);
        float y = startY + fraction*(endY-startY);
        Ponit ponit = new Ponit(x, y);
        return ponit;
    }
}
