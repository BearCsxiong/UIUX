package me.csxiong.ui.transition.transitionFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.csxiong.ui.transition.R;

/**
 * Desc : 对于Fragment包裹的内部View能否在外部调用方法执行动画
 * Author : csxiong - 2019/7/2
 */
public class TransitionFragment extends Fragment {

    ImageView mIvContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transition, null, true);
        mIvContent = view.findViewById(R.id.iv_content);
        return view;
    }

    public void change(boolean isSmallSize) {
        ViewGroup.LayoutParams lp = mIvContent.getLayoutParams();
        lp.height = isSmallSize ? 200 : 400;
        lp.width = isSmallSize ? 200 : 400;
        mIvContent.setLayoutParams(lp);
    }
}
