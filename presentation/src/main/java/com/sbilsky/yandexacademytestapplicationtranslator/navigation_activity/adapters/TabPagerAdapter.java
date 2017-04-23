package com.sbilsky.yandexacademytestapplicationtranslator.navigation_activity.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mTittleList = new ArrayList<>();

    public TabPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mTittleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTittleList.size() != 0) {
            return mTittleList.get(position);
        } else {
            return null;
        }
    }
}
