package com.blastic.lostandfound;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.blastic.lostandfound.R;
import com.blastic.lostandfound.data.AppCache;
import com.blastic.lostandfound.location.UserLocation;
import com.blastic.lostandfound.location.UserLocation.NoLocationException;

public class FragmentCasesMap extends Fragment {

	private static View view;
	private GoogleMap map;
	
	private Activity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map_causes, container, false);
        } catch (InflateException e) {
           
        }
        return view;
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		activity=getActivity();
		initViews();
	}

	private void initViews(){

		if (map == null && getActivity() != null && getActivity().getSupportFragmentManager()!= null) {
            SupportMapFragment smf = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.myMap);
            if (smf != null) {
                map = smf.getMap();
            }
        }
		
		fillMarkers();
		if(AppCache.getMyLocation()==null)
			new AsyncTaskLocation().execute();
		else
			muestraPosicion(AppCache.getMyLocation());
	}
	
	private void muestraPosicion(Location location) {

		LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
		
		CameraPosition camPos = new CameraPosition.Builder()
		.target(coordenada).zoom(15).build();

		CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camPos);

		map.moveCamera(cameraUpdate);
		
		addMarker("Yo", "", location.getLatitude(), location.getLongitude());
	}


	private void fillMarkers() {
		addMarker("Perro", "Extraviado", 19.4326018, -99.1332049);
		addMarker("Gato", "Maltrato", 19.5326118, -99.1332149);

	}

	private void addMarker(String title, String snippet, double corLat,double corLong) {
		LatLng coordenada = new LatLng(corLat, corLong);

		map.addMarker(new MarkerOptions().position(coordenada).title(title)
				.snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
	}
	
	
	private class AsyncTaskLocation extends AsyncTask<Void, Void, Location>{

		private UserLocation userLocation;
		protected ProgressDialog dialogo;
		
		@Override
		protected void onPreExecute(){
			userLocation = new UserLocation(activity);
			dialogo = new ProgressDialog(activity);
			dialogo.setIndeterminate(true);
			dialogo.setMessage("Localizandote...");
			dialogo.setTitle("Lost & Found");
			dialogo.setCancelable(false);
			dialogo.show();
		}
		
		@Override
		protected Location doInBackground(Void... params) {
					
			boolean haveLocation=false;
			Location location = null;
			
			do{
				try {
					location=userLocation.getLocation();
					haveLocation=true;
				} catch (NoLocationException e) {
					haveLocation=false;
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
					}
				}
			}while(!haveLocation);
			
			return location;
		}
		
		@Override
		protected void onPostExecute(Location location){
			userLocation.endLocation();
			dialogo.cancel();
			AppCache.setMyLocation(location);
			muestraPosicion(location);
		}
		
	}
	
}