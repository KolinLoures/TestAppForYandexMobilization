package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.FavoriteOrHistoryDeleteAllContract;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.FavoriteOrHistoryFrg;
import com.sbilsky.yandexacademytestapplicationtranslator.navigation_activity.adapters.TabPagerAdapter;
import com.sbilsky.yandexacademytestapplicationtranslator.utils.ExtendedFragmentLifeCycle;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteAndHistoryMainFrg extends Fragment implements ExtendedFragmentLifeCycle {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FavoriteOrHistoryFrg.NoDataForDeletingCallBack noDataForDeletingCallBack;
    private FavoriteOrHistoryDeleteAllContract fragmentFavoriteListener, fragmentHistoryListener;
    private FavoriteOrHistoryFrg fragmentHistory, fragmentFavorite;
    private FavoriteOrHistoryFrg.OnFragmentCreatedCallBack onFragmentCreatedCallBack;
    private final String FRAGMENT_HISTORY_TAG = "FragmentHistory";
    private final String FRAGMENT_FAVORITE_TAG = "FragmentFavorite";

    public static FavoriteAndHistoryMainFrg newInstance() {
        return new FavoriteAndHistoryMainFrg();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite_and_history_main_fragment, container, false);
        initCallBack();
        setupTabLayout(rootView);
        setupViewPager(rootView, savedInstanceState);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.favorite_and_history_main_frg_toolbar);
        toolbar.inflateMenu(R.menu.favorite_and_history_menu);
        if (toolbar.getMenu() != null && toolbar.getMenu().getItem(0) != null)
            toolbar.getMenu().getItem(0).setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.favorite_and_history_menu_clear_all:
                        if (viewPager.getCurrentItem() == 0) {
                            if (fragmentHistoryListener != null)
                                fragmentHistoryListener.onDeleteAllButtonPressed();
                        } else if (viewPager.getCurrentItem() == 1) {
                            if (fragmentFavoriteListener != null)
                                fragmentFavoriteListener.onDeleteAllButtonPressed();
                        }
                        break;
                }
                return false;
            });
    }

    private void setupTabLayout(View rootView) {
        tabLayout = (TabLayout) rootView.findViewById(R.id.favorite_and_history_main_frg_tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initCallBack() {
        noDataForDeletingCallBack = new FavoriteOrHistoryFrg.NoDataForDeletingCallBack() {
            @Override
            public void noDataForDeleting() {
                if (toolbar.getMenu() != null && toolbar.getMenu().getItem(0) != null)
                    toolbar.getMenu().getItem(0).setVisible(false);
            }

            @Override
            public void haveDataForDeleting() {
                if (toolbar.getMenu() != null && toolbar.getMenu().getItem(0) != null)
                    toolbar.getMenu().getItem(0).setVisible(true);
            }
        };
        onFragmentCreatedCallBack = () -> fragmentHistory.onShowFragment();
    }

    private void setupViewPager(View rootView, Bundle savedInstanceState) {
        viewPager = (ViewPager) rootView.findViewById(R.id.favorite_and_history_main_frg_viewpager);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        if (savedInstanceState == null) {
            fragmentHistory = FavoriteOrHistoryFrg.newInstance(FragmentType.HISTORY);
            fragmentFavorite = FavoriteOrHistoryFrg.newInstance(FragmentType.FAVORITE);
        } else {
            fragmentHistory = (FavoriteOrHistoryFrg) getChildFragmentManager().getFragment(savedInstanceState, FRAGMENT_HISTORY_TAG);
            fragmentFavorite = (FavoriteOrHistoryFrg) getChildFragmentManager().getFragment(savedInstanceState, FRAGMENT_FAVORITE_TAG);
        }
        fragmentHistoryListener = fragmentHistory;
        fragmentHistory.registerNoDataForDeletingCallBack(noDataForDeletingCallBack);
        fragmentHistory.registerOnFragmentCreatedCallBack(onFragmentCreatedCallBack);
        fragmentFavoriteListener = fragmentFavorite;
        fragmentFavorite.registerNoDataForDeletingCallBack(noDataForDeletingCallBack);
        tabPagerAdapter.addFragment(fragmentHistory, getString(R.string.fragment_history_title));
        tabPagerAdapter.addFragment(fragmentFavorite, getString(R.string.fragment_favorite_title));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentFragmentPosition = 0;

            @Override
            public void onPageSelected(int newPosition) {

                ExtendedFragmentLifeCycle fragmentToShow = (ExtendedFragmentLifeCycle) ((TabPagerAdapter) viewPager.getAdapter()).getItem(newPosition);
                fragmentToShow.onShowFragment();
                ExtendedFragmentLifeCycle fragmentToHide = (ExtendedFragmentLifeCycle) ((TabPagerAdapter) viewPager.getAdapter()).getItem(currentFragmentPosition);
                fragmentToHide.onHideFragment();
                currentFragmentPosition = newPosition;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void updateCurrentFragmentInViewPager() {
        if (viewPager.getCurrentItem() == 0) {
            if (fragmentHistory != null)
                fragmentHistory.onShowFragment();
        } else if (viewPager.getCurrentItem() == 1) {
            if (fragmentFavorite != null)
                fragmentFavorite.onShowFragment();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getChildFragmentManager().putFragment(outState, FRAGMENT_HISTORY_TAG, fragmentHistory);
        getChildFragmentManager().putFragment(outState, FRAGMENT_FAVORITE_TAG, fragmentFavorite);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPager = null;
        noDataForDeletingCallBack = null;
        fragmentFavoriteListener = null;
        fragmentHistoryListener = null;
        fragmentFavorite = null;
        fragmentHistory = null;
    }

    @Override
    public void onHideFragment() {
        // ignore
    }

    @Override
    public void onShowFragment() {
        updateCurrentFragmentInViewPager();
    }
}
