package me.csxiong.effectuilibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.csxiong.ui.transition.TestTransitionActivity;
import me.csxiong.ui.transition.TransitionBeginDelayActivity;

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

    public void toTestTransition(View view) {
        Intent intent = new Intent();
        intent.setClass(this, TestTransitionActivity.class);
        startActivity(intent);
    }
}
