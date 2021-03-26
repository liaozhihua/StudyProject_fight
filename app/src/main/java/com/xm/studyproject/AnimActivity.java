package com.xm.studyproject;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ListView;

import com.xm.studyproject.android.anim.DecelerateAccelerateInterpolator;
import com.xm.studyproject.android.anim.ObjectEvaluator;
import com.xm.studyproject.android.anim.Point;

import androidx.appcompat.app.AppCompatActivity;

public class AnimActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        button = findViewById(R.id.button);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setInterpolator(new AnticipateInterpolator());
        alphaAnimation.setDuration(5000);
        alphaAnimation.setFillAfter(true);
        //方式一 這種方式存在 沒有指示此视图的父类去清掉其视图缓存  也没调用自己去清掉自己缓存
//        button.setAnimation(alphaAnimation);
//        alphaAnimation.start();
        //方式二
//        button.startAnimation(alphaAnimation);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_test);
//        button.startAnimation(animation);

        ObjectAnimator animator  = ObjectAnimator.ofFloat(button, "translationX", 100, 500);
        animator.setDuration(5000);
        animator.setInterpolator(new DecelerateAccelerateInterpolator());
        animator.start();

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ObjectEvaluator(), new Point(200, 200), new Point(600, 600));
        ListView listview = findViewById(R.id.listview);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_animation);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        controller.setDelay(0.5f);
        listview.setLayoutAnimation(controller);

    }
}
