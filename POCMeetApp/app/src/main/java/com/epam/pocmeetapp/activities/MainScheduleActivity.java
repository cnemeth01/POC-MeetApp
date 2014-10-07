package com.epam.pocmeetapp.activities;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.epam.pocmeetapp.R;
import com.epam.pocmeetapp.fragments.ScheduleFragment;

/**
 * Created by Csaba_Bela_Nemeth on 10/2/2014.
 */
public class
        MainScheduleActivity extends BaseActivity implements ActionBar.TabListener {

    public static final String SCHEDULE_TYPE = "schedule";
    private MySectionsPagerAdapter mySectionsPagerAdapter;
    private ViewPager sectionsViewPager;
    private ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myschedule_activity);


        mySectionsPagerAdapter = new MySectionsPagerAdapter(getSupportFragmentManager());

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        sectionsViewPager = (ViewPager) findViewById(R.id.myschedule_pager);
        sectionsViewPager.setAdapter(mySectionsPagerAdapter);
        sectionsViewPager.setOffscreenPageLimit(TRIM_MEMORY_RUNNING_MODERATE);
        sectionsViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                  actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mySectionsPagerAdapter.getCount(); i++) {
            ActionBar.Tab actionTab = actionBar.newTab().setIcon(mySectionsPagerAdapter.getPageIcon(i)).setTabListener(this);
            actionTab.getIcon().setAlpha(80);
            actionBar.addTab(actionTab);
        }

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        sectionsViewPager.setCurrentItem(tab.getPosition());
        Drawable icon = actionBar.getSelectedTab().getIcon();
        if (icon != null) {
            icon.setAlpha(255);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        Drawable icon = actionBar.getSelectedTab().getIcon();
        if (icon != null) {
            icon.setAlpha(80);
        }


    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public static class MySectionsPagerAdapter extends
            FragmentStatePagerAdapter {

        public MySectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            Fragment fragment = new ScheduleFragment();
            Bundle args = new Bundle();
            args.putInt(SCHEDULE_TYPE, i);
            fragment.setArguments(args);
            return fragment;

        }

        @Override
        public int getCount() {
            return 4;
        }

        public int getPageIcon(int position) {
            switch (position) {
                case 0:
                    return R.drawable.next;
                case 1:
                    return R.drawable.android_icon;
                case 2:
                    return R.drawable.ios_icon;
                case 3:
                    return R.drawable.open;
                default:
                    return R.drawable.next;
            }
        }
    }

}
