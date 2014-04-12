package com.blastic.lostandfound;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailMapFragment extends Fragment{

	private static View view;
	private GoogleMap map;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.activity_detail_3, container, false);
        } catch (InflateException e) {
           
        }
        return view;
    }
	
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        initViews();
    }
	
	private void initViews(){
		
		if (map == null && getActivity() != null && getActivity().getSupportFragmentManager()!= null) {
            SupportMapFragment smf = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.detailReportMap);
            if (smf != null) {
                map = smf.getMap();
            }
        }
		
		LatLng coordenada = new LatLng(19.4326018, -99.1332049);
		CameraPosition camPos = new CameraPosition.Builder().target(coordenada).zoom(15).build();

		CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camPos);

		map.moveCamera(cameraUpdate);
		
		addMarker("Gato", "Maltrato", 19.4326018, -99.1332049);
	}
	
	private void addMarker(String title, String snippet, double corLat,double corLong) {
		LatLng coordenada = new LatLng(corLat, corLong);

		map.addMarker(new MarkerOptions().position(coordenada).title(title)
				.snippet(snippet)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
	}
	
}
