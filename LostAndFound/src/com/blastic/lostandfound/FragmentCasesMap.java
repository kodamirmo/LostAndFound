package com.blastic.lostandfound;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class FragmentCasesMap extends Fragment {

	private static int SERVICE_DISABLED = 3;

	private GoogleMap map;
	private SupportMapFragment fragmentoDeMapa;
	private Fragment fragment;
	private Context context;

	private LocationManager locationManager;
	private LocationListener locationListener;
	private LatLng coordenada;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = inflater.getContext();
		return inflater.inflate(R.layout.fragment_map_causes, container, false);
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		if ((map == null)
				&& (GooglePlayServicesUtil
						.isGooglePlayServicesAvailable(context) != SERVICE_DISABLED))
			try {
				initMap();
				fillMarkers();
			} catch (GooglePlayServicesNotAvailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void initMap() throws GooglePlayServicesNotAvailableException {
		// Obtenemos una referencia al LocationManager
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		// Obtenemos la última posición conocida
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Mostramos la última posición conocida
		muestraPosicion(location);

		// Nos registramos para recibir actualizaciones de la posición
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				muestraPosicion(location);
			}

			public void onProviderDisabled(String provider) {
			}

			public void onProviderEnabled(String provider) {
				Log.i("Provider Status: ", "Provider ON");
				Toast toast1 = Toast.makeText(context,
						"Espera a que se actualize tu ubicación",
						Toast.LENGTH_SHORT);
				toast1.show();
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				Log.i("LocAndroid", "Provider Status: " + status);

			}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				15000, 0, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 15000, 0, locationListener);
	}

	public void muestraPosicion(Location location) {
		fragment = getFragmentManager().findFragmentById(R.id.myMap);
		fragmentoDeMapa = (SupportMapFragment) fragment;
		map = fragmentoDeMapa.getMap();

		if (location != null) {
			coordenada = new LatLng(location.getLatitude(),
					location.getLongitude());
		} else {
			coordenada = new LatLng(19.4326018, -99.1332049);
		}

		CameraPosition camPos = new CameraPosition.Builder().target(coordenada)
				.zoom(12).build();

		CameraUpdate cameraUpdate = CameraUpdateFactory
				.newCameraPosition(camPos);

		map.moveCamera(cameraUpdate);
	}

	public void removeMap() {
		Log.i("TAG", "Remove map");
		fragment.getFragmentManager().beginTransaction()
				.remove(fragmentoDeMapa).commit();
	}

	private void fillMarkers() {
		addMarker("Perro", "Extraviado", 19.4326018, -99.1332049);
		addMarker("Gato", "Maltrato", 19.5326118, -99.1332149);

	}

	private void addMarker(String title, String snippet, double corLat,
			double corLong) {
		LatLng coordenada = new LatLng(corLat, corLong);

		map.addMarker(new MarkerOptions().position(coordenada).title(title)
				.snippet(snippet)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
	}
	
}