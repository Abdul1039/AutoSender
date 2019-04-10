package com.blazeminds.autosender;

import android.Manifest;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Abdul on 4/9/2019.
 */
public class MainActivity extends AppCompatActivity {
	
	private Toolbar toolbar;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
		}
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		setupViewPager(viewPager);
		
		tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);
	}
	
	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new FragmentOne(), "Add Sender");
		adapter.addFragment(new FragmentTwo(), "List");
		/*adapter.addFragment(new TwoFragment(), "TWO");
		adapter.addFragment(new ThreeFragment(), "THREE");*/
		viewPager.setAdapter(adapter);
	}
	
	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();
		
		public ViewPagerAdapter(FragmentManager manager) {
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
		
		public void addFragment(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}
	}
}
