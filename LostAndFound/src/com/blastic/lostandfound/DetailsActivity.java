package com.blastic.lostandfound;

import java.util.Locale;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.blastic.lostandfound.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DetailsActivity extends FragmentActivity {

	static Context context;
	private static GoogleMap map;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	Fragment mapFragment;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		//setContentView(R.layout.prueba);
		
		mapFragment = getSupportFragmentManager().findFragmentByTag(
				"detailReportMap");

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
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
				"Esta opción aún no está disponible en el demo",
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
				"Esta opción aún no está disponible en el demo",
				Toast.LENGTH_LONG).show();
		/*Intent openSet = new Intent(this, SettingsActivity.class);
        startActivity(openSet);*/
	}

	private void makeResolve() {
		Intent detailsIntent = new Intent(this, ListReportsActivity.class);
		startActivity(detailsIntent);
	}

	private void makeAlert() {
		Toast.makeText(this,
				"Esta opción aún no está disponible en el demo",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}

	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static int sec;
		public static View rootView;

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			context = inflater.getContext();
			sec = getArguments().getInt(ARG_SECTION_NUMBER);

			try {
				switch (sec) {
				case 1:
					rootView = inflater.inflate(R.layout.activity_detail_1,
							container, false);
					fill1();
					return rootView;
				case 2: 
					rootView = inflater.inflate(R.layout.activity_detail_2,
							container, false);
					fill2();
					return rootView;
				case 3:
					rootView = inflater.inflate(R.layout.activity_detail_3,
							container, false);
					fill3();
					return rootView;
				}
			} catch (InflateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		private void fill1() {
			
			/*ImageView pic1 = (ImageView) rootView
					.findViewById(R.id.image1Detail1);
			ImageView pic2 = (ImageView) rootView
					.findViewById(R.id.image1Detail1);
			ImageView pic3 = (ImageView) rootView
					.findViewById(R.id.image1Detail1);
			
			pic1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
					Toast toast2 = Toast.makeText(getActivity(),
							"Entra", Toast.LENGTH_SHORT);
					toast2.show();
					Intent intent = new Intent(context, FullImage.class);
					intent.putExtra("imageUri", R.drawable.finding_home);
				}
				
			});*/
			

			ImageView arrowDown = (ImageView) rootView
					.findViewById(R.id.imageArrowDownDetail1);
			arrowDown.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final Dialog dialog = new Dialog(getActivity());
					dialog.setTitle("Opciones");
					dialog.setContentView(R.layout.dialog_view);

					final LinearLayout opt1 = (LinearLayout) dialog
							.findViewById(R.id.optsDialogSimilary);
					LinearLayout opt2 = (LinearLayout) dialog
							.findViewById(R.id.optsDialogInadequate);

					opt1.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {

							opt1.setBackgroundColor(Color.parseColor("#BCB7B7"));

							return false;
						}

					});

					opt1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent reports = new Intent(getActivity(),
									ListReportsActivity.class);
							startActivity(reports);
						}
					});

					opt2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							Toast toast = Toast
									.makeText(
											getActivity(),
											"¡Muchas gracias por tu aporte! El reporte ya fue enviado a revisión",
											Toast.LENGTH_SHORT);
							toast.show();
							dialog.dismiss();
						}
					});

					dialog.show();
				}
			});

		}

		private void fill2() {

			ImageView arrowDown = (ImageView) rootView
					.findViewById(R.id.imageArrowDownDetail2);
			arrowDown.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final Dialog dialog = new Dialog(getActivity());
					dialog.setTitle("Opciones");
					dialog.setContentView(R.layout.dialog_view);

					final LinearLayout opt1 = (LinearLayout) dialog
							.findViewById(R.id.optsDialogSimilary);
					LinearLayout opt2 = (LinearLayout) dialog
							.findViewById(R.id.optsDialogInadequate);

					opt1.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {

							opt1.setBackgroundColor(Color.parseColor("#BCB7B7"));

							return false;
						}

					});

					opt1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent reports = new Intent(getActivity(),
									ListReportsActivity.class);
							startActivity(reports);
						}
					});

					opt2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getActivity(),
									"Esta opción aún no está disponible en el demo",
									Toast.LENGTH_LONG).show();
							/*Toast toast = Toast
									.makeText(
											getActivity(),
											"¡Muchas gracias por tu aporte! El reporte ya fue enviado a revisión",
											Toast.LENGTH_SHORT);*/
							dialog.dismiss();
						}
					});

					dialog.show();
				}
			});
		}

		private void fill3() {

			int SERVICE_DISABLED = 3;

			// SERVICE DISABLE if device not have google play services
			if ((map == null)
					&& (GooglePlayServicesUtil
							.isGooglePlayServicesAvailable(context) != SERVICE_DISABLED))
				try {
					Log.i("entra", "try");
					initMap();
					fillMarkers();
				} catch (GooglePlayServicesNotAvailableException e) {
					e.printStackTrace();
				}

		}

		private void initMap() throws GooglePlayServicesNotAvailableException {
			// TODO Auto-generated method stub
			map = ((SupportMapFragment) getFragmentManager().findFragmentById(
					R.id.detailReportMap)).getMap();

			LatLng coordenada = new LatLng(19.4326018, -99.1332049);
			CameraPosition camPos = new CameraPosition.Builder()
					.target(coordenada).zoom(12).build();

			CameraUpdate cameraUpdate = CameraUpdateFactory
					.newCameraPosition(camPos);

			map.moveCamera(cameraUpdate);
		} 
		
		private void fillMarkers() {
			addMarker("Gato", "Maltrato", 19.4326018, -99.1332049);
		}

		private void addMarker(String title, String snippet, double corLat,
				double corLong) {
			LatLng coordenada = new LatLng(corLat, corLong);

			map.addMarker(new MarkerOptions().position(coordenada).title(title)
					.snippet(snippet)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
		}

		@Override
		public void onDestroyView() {
			super.onDestroy();
			SupportMapFragment f = (SupportMapFragment) getFragmentManager()
					.findFragmentById(R.id.detailReportMap);
			if (f != null)
				getFragmentManager().beginTransaction().remove(f).commit();
		}
	}

}
