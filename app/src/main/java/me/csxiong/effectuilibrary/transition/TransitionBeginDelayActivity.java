package me.csxiong.effectuilibrary.transition;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import me.csxiong.effectuilibrary.R;

public class TransitionBeginDelayActivity extends AppCompatActivity {

    ViewGroup mRoot;

    ViewGroup mGlGroup;

    ImageView mIv1;
    ImageView mIv2;
    ImageView mIv3;
    ImageView mIv4;
    ImageView mIv5;
    ImageView mIv6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_begin_delay);

        mRoot = findViewById(R.id.root);

        mGlGroup = findViewById(R.id.gl_group);
        mIv1 = findViewById(R.id.iv_1);
        mIv2 = findViewById(R.id.iv_2);
        mIv3 = findViewById(R.id.iv_3);
        mIv4 = findViewById(R.id.iv_4);
        mIv5 = findViewById(R.id.iv_5);
        mIv6 = findViewById(R.id.iv_6);

        findViewById(android.R.id.content).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                return false;
            }
        });
    }

    private boolean isSmallWindow = false;

    public void exChange(View view) {
        isSmallWindow = !isSmallWindow;
        executeToolWindow();
        executeTransition(mIv1, mIv2, mIv3, mIv4, mIv5, mIv6);
    }

    public void changeSize(View... views) {
        for (View view : views) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (isSmallWindow) {
                lp.height = 40;
                lp.width = 40;
            } else {
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            view.setLayoutParams(lp);
        }
    }

    public void executeTransition(View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(mGlGroup, TransitionInflater.from(this).inflateTransition(R.transition.explode_slide));
//            TransitionManager.beginDelayedTransition(mGlGroup,new AutoTransition());
        }
        changeSize(views);
        for (View view : views) {
            view.setVisibility(isSmallWindow ? View.INVISIBLE : View.VISIBLE);
        }

        mIv2.setVisibility(View.VISIBLE);
        mIv1.setVisibility(View.VISIBLE);
    }

    public void executeToolWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(mRoot, TransitionInflater.from(this).inflateTransition(android.R.transition.explode));
//            TransitionManager.beginDelayedTransition(mRoot,new AutoTransition());
        }
        ViewGroup.LayoutParams lp = mGlGroup.getLayoutParams();
        if (isSmallWindow) {
            lp.width = 200;
            lp.height = 100;
        } else {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        mGlGroup.setLayoutParams(lp);
    }

}
