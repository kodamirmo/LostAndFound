package com.blastic.lostandfound;

import com.blastic.lostandfound.R;
import com.blastic.lostandfound.adapters.DetailPagerAdapter;
import com.blastic.lostandfound.config.Config;
import com.blastic.lostandfound.views.ExpandAnimation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DetailsActivity extends ActionBarActivity implements OnClickListener{

	private DetailPagerAdapter pageAdapter;
	private ViewPager mViewPager;
	private ImageView arrowDown;
	private LinearLayout layoutDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
	
		initActionBar();
		initViews();
	}
	
	private void initActionBar(){
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(Config.actionBarColor)));
	}
	
	private void initViews(){
		pageAdapter=new DetailPagerAdapter(getBaseContext(), getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(pageAdapter);
		
		layoutDescription=(LinearLayout)findViewById(R.id.layoutDetailDescription);
		
		arrowDown = (ImageView)findViewById(R.id.imageArrowDownDetail);
		arrowDown.setOnClickListener(this);
		
		PagerTitleStrip titleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
		titleStrip.setTextColor(Color.WHITE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.report_details_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_alert:
			makeAlert();
			return true;
		case R.id.action_share:
			shareIntent();
			return true;
		case R.id.action_resolve:
			makeResolve();
			return true;
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void shareIntent() {
		Toast.makeText(this,
				"Esta opci��n a��n no est�� disponible en el demo",
				Toast.LENGTH_LONG).show();
		/*Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharingIntent.setType("text/html");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
				"Texto a compartir");
		startActivity(Intent.createChooser(sharingIntent, "Compartir con"));*/
	}

	private void openSettings() {
		Toast.makeText(this,
				"Esta opci��n a��n no est�� disponible en el demo",
				Toast.LENGTH_LONG).show();
		/*Intent openSet = new Intent(this, SettingsActivity.class);
        startActivity(openSet);*/
	}

	private void makeResolve() {
		Intent detailsIntent = new Intent(this, SimilarReportsScreen.class);
		startActivity(detailsIntent);
	}

	private void makeAlert() {
		Toast.makeText(this,
				"Esta opci��n a��n no est�� disponible en el demo",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		ExpandAnimation expandAni = new ExpandAnimation(layoutDescription, 500);
		layoutDescription.startAnimation(expandAni);
	}
	
}
