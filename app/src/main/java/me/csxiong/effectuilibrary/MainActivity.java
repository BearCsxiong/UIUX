package me.csxiong.effectuilibrary;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.csxiong.effectuilibrary.transition.TransitionBeginDelayActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toBeginDelayTransition(View view) {
        Intent intent = new Intent();
        intent.setClass(this, TransitionBeginDelayActivity.class);
        startActivity(intent);
    }
}
