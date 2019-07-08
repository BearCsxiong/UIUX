package me.csxiong.ui.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.nfc.Tag;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Desc : 测试调用顺序和回调内容
 * 结论TransitionFramework的工作原理
 * 1.采集开始信息
 * 2.采集结束信息
 * 3.在过渡期间执行Transition,创建执行动画
 * {@link android.transition.Fade}
 * OR
 * {@link android.transition.ChangeBounds} TODO 可继续查看ChangeBounds具体变换和创建Animator的操作,并解释其过程
 * OR
 * {@link android.transition.Explode}
 * and so on
 * <p>
 * 一些方法具体使用和回调可以查看{@link android.transition.TransitionManager#beginDelayedTransition(ViewGroup, Transition)}
 * <p>
 * 解析Transition执行流程和TransitionFramework的工作原理
 * {@link android.transition.TransitionManager#beginDelayedTransition(ViewGroup, Transition)}
 * 中代码的执行分析
 * 1.获取当前Root内部正在执行的Transition{@link android.transition.TransitionManager#sceneChangeSetup} 这个private method，并且暂停所有的正在执行的Transition
 * Author : csxiong - 2019/7/2
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class TestTransition extends Transition {

    public final String TAG = "TestTransition";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.toString();
        Log.e(TAG, "captureStartValues" + transitionValues.view.getClass().getSimpleName());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.toString();
        Log.e(TAG, "captureEndValues" + transitionValues.view.getClass().getSimpleName());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
