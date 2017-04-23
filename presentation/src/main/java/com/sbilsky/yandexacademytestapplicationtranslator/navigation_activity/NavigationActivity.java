package com.sbilsky.yandexacademytestapplicationtranslator.navigation_activity;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.sbilsky.customviewslibrary.views.NoScrollableViewPager;
import com.sbilsky.domain.rx_usecases.favorite_and_history.FavoriteAndHistoryRx;
import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FavoriteAndHistoryMainFrg;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FragmentType;
import com.sbilsky.yandexacademytestapplicationtranslator.navigation_activity.adapters.TabPagerAdapter;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.AboutAndSettingsFrg;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.TranslatorFrg;
import com.sbilsky.yandexacademytestapplicationtranslator.utils.ExtendedFragmentLifeCycle;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NavigationActivity extends AppCompatActivity implements NavigationActivityContract {
    private TabLayout tabLayout;
    private TranslatorFrg translatorFrg;
    private FavoriteAndHistoryMainFrg favoriteAndHistoryMainFrg;
    private AboutAndSettingsFrg aboutAndSettingsFrg;
    private final String TRANSLATOR_FRG_BUNDLE_TAG = "translatorFrg";
    private final String FAVORITE_AND_HISTORY_MAIN_FRG_BUNDLE_TAG = "favoriteAndHistoryMainFrg";
    private final String ABOUT_AND_SETTINGS_BUNDLE_TAG = "AboutAndSettingsFrg";
    private NoScrollableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
        setupTabLayout();
        setupViewPager(savedInstanceState);
        setupTabIcon();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void setupTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.main_activity_tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void setupViewPager(Bundle savedInstanceState) {
        viewPager = (NoScrollableViewPager) findViewById(R.id.main_activity_viewpager);
        viewPager.setPagingEnabled(false);
        if (savedInstanceState == null) {
            translatorFrg = TranslatorFrg.newInstance();
            favoriteAndHistoryMainFrg = FavoriteAndHistoryMainFrg.newInstance();
            aboutAndSettingsFrg = AboutAndSettingsFrg.newInstance();
        } else {
            translatorFrg = (TranslatorFrg) getSupportFragmentManager().getFragment(savedInstanceState, TRANSLATOR_FRG_BUNDLE_TAG);
            favoriteAndHistoryMainFrg = (FavoriteAndHistoryMainFrg) getSupportFragmentManager().getFragment(savedInstanceState, FAVORITE_AND_HISTORY_MAIN_FRG_BUNDLE_TAG);
            aboutAndSettingsFrg = (AboutAndSettingsFrg) getSupportFragmentManager().getFragment(savedInstanceState, ABOUT_AND_SETTINGS_BUNDLE_TAG);
        }
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tabPagerAdapter.addFragment(translatorFrg);
        tabPagerAdapter.addFragment(favoriteAndHistoryMainFrg);
        tabPagerAdapter.addFragment(aboutAndSettingsFrg);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentFragmentPosition = 0;

            @Override
            public void onPageSelected(int newPosition) {
                if (((TabPagerAdapter) viewPager.getAdapter()).getItem(newPosition) instanceof ExtendedFragmentLifeCycle) {
                    ExtendedFragmentLifeCycle fragmentToShow = (ExtendedFragmentLifeCycle) ((TabPagerAdapter) viewPager.getAdapter()).getItem(newPosition);
                    fragmentToShow.onShowFragment();
                }
                if (((TabPagerAdapter) viewPager.getAdapter()).getItem(currentFragmentPosition) instanceof ExtendedFragmentLifeCycle) {
                    ExtendedFragmentLifeCycle fragmentToHide = (ExtendedFragmentLifeCycle) ((TabPagerAdapter) viewPager.getAdapter()).getItem(currentFragmentPosition);
                    fragmentToHide.onHideFragment();
                }
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

    private void setupTabIcon() {
        int[] iconMassive = {R.drawable.ic_translate_black_24dp, R.drawable.ic_bookmark_black_24dp, R.drawable.ic_settings_black_24dp};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(iconMassive[i]);
                if ((i == 1 || i == 2) && tab.getIcon() != null) {
                    tab.getIcon().setAlpha(128);
                }
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getIcon() != null)
                    tab.getIcon().setAlpha(255);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon() != null)
                    tab.getIcon().setAlpha(128);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, TRANSLATOR_FRG_BUNDLE_TAG, translatorFrg);
        getSupportFragmentManager().putFragment(outState, FAVORITE_AND_HISTORY_MAIN_FRG_BUNDLE_TAG, favoriteAndHistoryMainFrg);
        getSupportFragmentManager().putFragment(outState, ABOUT_AND_SETTINGS_BUNDLE_TAG, aboutAndSettingsFrg);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        translatorFrg = null;
        favoriteAndHistoryMainFrg = null;
        aboutAndSettingsFrg = null;
        clearDataNoFavoriteAndNoHistory();
    }

    @Override
    public void showTranslateInTranslatorFragment(int position, int id, FragmentType fragmentTypeFrom) {
        viewPager.setCurrentItem(0);
        if (translatorFrg != null) {
            translatorFrg.getTranslateByTypeAndPosition(position, id, fragmentTypeFrom);
        }
    }

    private void clearDataNoFavoriteAndNoHistory() {
        if (isFinishing()) {
            FavoriteAndHistoryRx.clearDataNoFavoriteAndNoHistory()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

}
