package com.xm.studyproject.android.anim;

import android.animation.TypeEvaluator;

/**
 * 估值器
 */
public class ObjectEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX()+fraction*(endPoint.getX()-startPoint.getX());
        float y = startPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
