package com.blastic.lostandfound.adapters;

import com.blastic.lostandfound.DetailImagesFragment;
import com.blastic.lostandfound.DetailMapFragment;
import com.blastic.lostandfound.DetailMessagesFragment;
import com.blastic.lostandfound.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailPagerAdapter extends FragmentPagerAdapter{

	private Context context;
	
	public DetailPagerAdapter(Context context,FragmentManager fm) {
		super(fm);
		this.context=context;
	}

	@Override
	public Fragment getItem(int position) {
		
		Fragment fragment=null;
		
		switch (position) {
		case 0:
			fragment=new DetailImagesFragment();
			break;
		case 1:
			fragment=new DetailMessagesFragment();
			break;
		case 2:
			fragment=new DetailMapFragment();
			break;
		}
		
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return context.getString(R.string.title_section1);
		case 1:
			return context.getString(R.string.title_section2);
		case 2:
			return context.getString(R.string.title_section3);
		}
		return "";
	}

}
