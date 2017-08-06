package thiandac.mywavetest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class WaveHelper {
    /** minimum value the bar can display */
    float mMinVal = 0f;

    /** maximum value the bar can display */
    float mMaxVal = 100f;
    ValueBarSelectionListener mSelectionListener;
    /** the value the bar currently displays */
    float mValue = 75f;
    private WaveView mWaveView;
    private AnimatorSet mAnimatorSet;
    float endY;
    float levelRatioB;
    float levelRatioA;
    float water;
    Callbacks activity;
    public WaveHelper(final WaveView waveView) {
        mWaveView = waveView;
//        initAnimation(0f, 1f, 1000, levelRatioB, levelRatioB, 1000, 0.0001f, 0.0001f, 5000);
        waveView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                water = mWaveView.getWaterLevelRatio();

                activity.updateClient(water,v);
                endY = mWaveView.getHeight() - event.getY();
                levelRatioA = endY / mWaveView.getHeight();
                if (levelRatioA <= 0f) {
                    levelRatioA = 0;
                } else if (levelRatioA >= 1) {
                    levelRatioA = 1;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        initAnimation(0f, 1f, 1000, levelRatioB, levelRatioA, 1000, 0.0001f, 0.025f, 5000);
                        //mSelectionListener.onSelectionUpdate(mValue, mMaxVal, mMinVal, waveView);
                        mAnimatorSet.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }//需要改動：如果動畫沒完又移動，動畫不要重新播放
                levelRatioB = levelRatioA;
                Log.d("what_helper", levelRatioA + "");
                Log.d("what_wave", water + "");
                return true;
            }
        });
    }

    public void start() {
        mWaveView.setShowWave(true);
        if (mAnimatorSet != null) {
            mAnimatorSet.start();
        }
    }

    public void registerClient(Activity activity, WaveView waveView){
        mWaveView = waveView;
        this.activity = (Callbacks)activity;
    }

    public void initAnimation(float waveMin, float waveMax, long waveTime, float levelMin, float levelMax, long levelTime, float ampMin, float ampMax, long ampTime) {
        List<Animator> animators = new ArrayList<>();

        // horizontal animation.
        // wave waves infinitely.
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(mWaveView, "waveShiftRatio", waveMin, waveMax);//default:0f,1f
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);//重複無限次播放
        waveShiftAnim.setDuration(waveTime);//幾秒內播放完畢；default:1000
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        // vertical animation.
        // water level increases from 0 to center of WaveView
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(mWaveView, "waterLevelRatio", levelMin, levelMax);//default:0f,0.5f
        waterLevelAnim.setDuration(levelTime);//default:10000
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());//慢慢減速
        animators.add(waterLevelAnim);

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(mWaveView, "amplitudeRatio", ampMin, ampMax);//default:0.0001f,0.05f
        amplitudeAnim.setRepeatCount(1);//default:ValueAnimator.INFINITE
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);//播放一次後再反向播放一次
        amplitudeAnim.setDuration(ampTime);//default:5000
        amplitudeAnim.setInterpolator(new LinearInterpolator());//均速播放
        animators.add(amplitudeAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }

    public void cancel() {
        if (mAnimatorSet != null) {
//            mAnimatorSet.cancel();
            mAnimatorSet.end();
        }
    }

}