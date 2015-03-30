package com.taobao.startupanim.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.taobao.startupanim.R;
import com.taobao.startupanim.adapter.GuidePageAdapter;
import com.taobao.startupanim.ui.view.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener
{
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    private ViewGroup mGuideContainer;

    private ImageView mGuideBackground1,mGuideBackground2,mGuideBackground3,mGuideBackground4;

    private View mGuideEarthBackground;

    private ImageView mGuidePerson1,mGuidePerson2,mGuidePerson3,mGuidePerson4;

    private static final int[] GUIDE_TEXT_ARRAY = {R.mipmap.ic_guide_title_one, R.mipmap.ic_guide_title_two, R.mipmap.ic_guide_title_three, R.mipmap.ic_guide_ballute};
    private static final int[] GUIDE_COLOR_ARRAY = {0xFF52D3FF,0xFF394D94,0xFFFF8684,0xFFF79AFF};

    private ImageView[] GUIDE_BACKGROUND_ARRAY;
    private ImageView[] GUIDE_PERSON_ARRAY;

    private int mCurrentPosition = 0;

    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewPager();
        initAnim();
    }

    private void initView()
    {
        mViewPager = (ViewPager) findViewById(R.id.view_pager_main);

        mGuideBackground1 = (ImageView) findViewById(R.id.iv_guide_bg_1);
        mGuideBackground2 = (ImageView) findViewById(R.id.iv_guide_bg_2);
        mGuideBackground3 = (ImageView) findViewById(R.id.iv_guide_bg_3);
        mGuideBackground4 = (ImageView) findViewById(R.id.iv_guide_bg_4);

        mGuideContainer = (ViewGroup) findViewById(R.id.fl_guide_container);

        mGuidePerson1 = (ImageView) findViewById(R.id.iv_guide_person_1);
        mGuidePerson2 = (ImageView) findViewById(R.id.iv_guide_person_2);
        mGuidePerson3 = (ImageView) findViewById(R.id.iv_guide_person_3);
        mGuidePerson4 = (ImageView) findViewById(R.id.iv_guide_person_4);
        //TODO
        mGuideEarthBackground = findViewById(R.id.v_earth_bg);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        GUIDE_PERSON_ARRAY = new ImageView[]{mGuidePerson1,mGuidePerson2,mGuidePerson3,mGuidePerson4};
        GUIDE_BACKGROUND_ARRAY = new ImageView[]{mGuideBackground1,mGuideBackground2,mGuideBackground3,mGuideBackground4};

    }

    private void initViewPager()
    {
        List<View> pageViews = new ArrayList<>();
        int len = GUIDE_TEXT_ARRAY.length;
        for(int i = 0; i < len; i++)
        {
            LinearLayout container = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,50,0,0);
            ImageView itemView = new ImageView(this);

            container.setGravity(Gravity.CENTER_HORIZONTAL);
            itemView.setBackgroundResource(GUIDE_TEXT_ARRAY[i]);
            itemView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            itemView.setPadding(10,10,15,10);
            container.addView(itemView,params);

            pageViews.add(container);
        }

        GuidePageAdapter adapter = new GuidePageAdapter(pageViews);
        mViewPager.setAdapter(adapter);

        //绑定指示器
        mIndicator.setViewPager(mViewPager);
        //绑定回调事件
        mIndicator.setOnPageChangeListener(this);
    }

    private void initAnim()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int x = metrics.widthPixels;
        int y = metrics.heightPixels;

        int len = GUIDE_PERSON_ARRAY.length;
        for(int i = 0; i < len; i++)
        {
            GUIDE_PERSON_ARRAY[i].setPivotX(0.5f*x);
            GUIDE_PERSON_ARRAY[i].setPivotY(1.0f*y+0.5f*x);

            GUIDE_BACKGROUND_ARRAY[i].setPivotX(0.5f*x);
            GUIDE_BACKGROUND_ARRAY[i].setPivotY(1.0f*y+0.5f*x);

            GUIDE_PERSON_ARRAY[i].setRotation(90.0f*i);
            GUIDE_BACKGROUND_ARRAY[i].setRotation(90.0f*i);
        }
    }

    /* viewpager callbacks */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
//        Log.d(TAG,"offset:"+positionOffset+",pos:"+position+",pixal:"+positionOffsetPixels);//0---1-->0      pos:0---1
        if(position == mCurrentPosition)
        {

            if(position != GUIDE_TEXT_ARRAY.length-1)
            {
                GUIDE_PERSON_ARRAY[position].setRotation(-positionOffset*90.0f);
                GUIDE_PERSON_ARRAY[position+1].setRotation((1-positionOffset)*90.0f);

                GUIDE_BACKGROUND_ARRAY[position].setRotation(-positionOffset*90.0f);
                GUIDE_BACKGROUND_ARRAY[position+1].setRotation((1-positionOffset)*90.0f);
            }
        }else
        {
            mCurrentPosition = position;
        }
    }

    @Override
    public void onPageSelected(int position)
    {
        //mcurrentpos--->position
        Log.d(TAG,"cur:"+mCurrentPosition+",po:"+position);
        ObjectAnimator animator;
        if(mCurrentPosition == position)//向左
        {
             animator = ObjectAnimator.ofInt(mGuideContainer,"backgroundColor",GUIDE_COLOR_ARRAY[mCurrentPosition+1],GUIDE_COLOR_ARRAY[mCurrentPosition]);
        }else//向右
        {
            animator = ObjectAnimator.ofInt(mGuideContainer,"backgroundColor",GUIDE_COLOR_ARRAY[mCurrentPosition],GUIDE_COLOR_ARRAY[mCurrentPosition+1]);
        }

        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(900);
        animator.start();
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }
}





















