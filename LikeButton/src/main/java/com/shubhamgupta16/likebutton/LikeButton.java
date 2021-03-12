package com.shubhamgupta16.likebutton;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import androidx.annotation.Nullable;

public class LikeButton extends androidx.appcompat.widget.AppCompatImageView {

    private boolean isLiked = false;
    private OnLikeChangeListener onLikeChangeListener;
    private int duration = 250;

    public void setOnLikeChangeListener(OnLikeChangeListener onLikeChangeListener) {
        this.onLikeChangeListener = onLikeChangeListener;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LikeButton(Context context) {
        super(context);
        init();
    }

    public LikeButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLike(boolean isLike){
        setLike(isLike, false);
    }

    public void setLike(boolean isLike, boolean animate){
        if (isLike)
            liked(animate);
        else
            unLiked();
    }



    private void init() {
        unLiked();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLiked)
                    unLiked();
                else
                    liked(true);
                if (onLikeChangeListener != null)
                    onLikeChangeListener.onChange(isLiked);
            }
        });
    }

    private void liked(boolean animate){
        isLiked = true;
        setImageResource(R.drawable._favorite_active_24);
        setColorFilter(Color.RED);
        if (animate)
        scaleView(this);
    }

    public void scaleView(final View v) {
        final ScaleAnimation fade_out = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_out.setDuration(duration * 2 / 5);
        fade_out.setFillAfter(true);
        fade_out.setInterpolator(new AccelerateInterpolator());

        ScaleAnimation fade_in = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(duration * 3 / 5);
        fade_in.setFillAfter(true);
        fade_in.setInterpolator(new DecelerateInterpolator());
        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.startAnimation(fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(fade_in);
    }

    private void unLiked(){
        isLiked = false;
        setImageResource(R.drawable._favorite_non_active_24);
        setColorFilter(Color.BLACK);
    }

    public interface OnLikeChangeListener {
        void onChange(boolean liked);
    }
}
