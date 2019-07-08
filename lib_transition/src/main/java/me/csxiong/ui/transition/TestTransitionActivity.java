package me.csxiong.ui.transition;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.csxiong.ui.transition.transitionFragment.TransitionFragment;

/**
 * Desc : 测试TestTransition
 * 1.主要测试RootView的作用
 * -OK
 * 2.不同层次内部子view是否可以Transition
 * -OK
 * 3.设想Fragment内部view也可以Transition
 * -OK
 * Author : csxiong - 2019/7/2
 */
public class TestTransitionActivity extends AppCompatActivity {

    ViewGroup mRoot;

    ViewGroup mContainer;

    TransitionFragment mFg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_transition);

        mRoot = findViewById(R.id.root);

        mContainer = findViewById(R.id.rl_container);
        mFg = new TransitionFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rl_container, mFg)
                .commitNow();
    }

    boolean isSmallSize;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onExchange(View view) {
        isSmallSize = !isSmallSize;
        TransitionManager.beginDelayedTransition(mRoot, new AutoTransition());
        mFg.change(isSmallSize);
    }

}
