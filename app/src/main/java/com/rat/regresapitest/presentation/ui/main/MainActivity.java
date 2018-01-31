package com.rat.regresapitest.presentation.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.rat.regresapitest.Constants;
import com.rat.regresapitest.R;
import com.rat.regresapitest.presentation.mvp.main.MainPresenter;
import com.rat.regresapitest.presentation.mvp.main.MainView;
import com.rat.regresapitest.presentation.ui.global.BaseMvpActivity;
import com.rat.regresapitest.presentation.ui.shotslist.ShotsFragment;

public class MainActivity extends BaseMvpActivity implements MainView {

    private ViewPager shotsViewPager;
    private TabLayout tabLayout;

    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initViews();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.main);
        initOptionsMenu(toolbar.getMenu());
        return toolbar;
    }

    public void initOptionsMenu(Menu menu) {
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
    }

    private void initDrawer(Toolbar toolbar) {
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .build();
    }

    private void initViews() {
        shotsViewPager = (ViewPager) findViewById(R.id.shots_pager);
        setupViewPager(shotsViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(shotsViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ShotsPagerAdapter shotsPagerAdapter = new ShotsPagerAdapter(getSupportFragmentManager());
        shotsPagerAdapter.addFragment(ShotsFragment.newInstance(Constants.SHOTS_SORT_POPULAR), getResources().getString(R.string.popular));
        shotsPagerAdapter.addFragment(ShotsFragment.newInstance(Constants.SHOTS_SORT_RECENT), getResources().getString(R.string.recently));
        viewPager.setAdapter(shotsPagerAdapter);
    }

}