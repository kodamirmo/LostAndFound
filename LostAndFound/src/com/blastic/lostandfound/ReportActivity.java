package com.blastic.lostandfound;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.blastic.lostandfound.adapters.FancyCoverFlowSampleAdapter;
import com.blastic.lostandfound.adapters.PetTypeAdapter;
import com.blastic.lostandfound.adapters.ReportTypeAdapter;
import com.blastic.lostandfound.config.Config;
import com.blastic.lostandfound.data.AppCache;
import com.blastic.lostandfound.location.UserLocation;
import com.blastic.lostandfound.location.UserLocation.NoLocationException;
import com.blastic.lostandfound.utils.ImageUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

public class ReportActivity extends ActionBarActivity{
	
	private Spinner spinnerReportType;
	private Spinner spinnerPetAge;
	private Spinner spinnerPetType;
	private EditText petName;
	private EditText reportTel;
	private EditText petFeatures;
	private EditText reportMsg;
	private ImageButton takePic;
	private ImageButton choosePic;
	private FancyCoverFlow fancyCoverFlow;
	private GoogleMap map;
	
	private final int TAKE_PICTURE = 100;
	private final int SELECT_PICTURE = 200;
	
	private FancyCoverFlowSampleAdapter imagesAdapter;
	private ArrayList<Bitmap> petImages;
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);		

		activity=this;
		initActionBar();
		initViews();
	}
	
	private void initActionBar(){
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(Config.actionBarColor)));
	}

	private void initViews(){
		spinnerReportType = (Spinner) findViewById(R.id.spinnerReportType);
		spinnerPetAge = (Spinner) findViewById(R.id.spinnerReportPetAge);
		spinnerPetType = (Spinner) findViewById(R.id.spinnerReportPetType);
		petName = (EditText) findViewById(R.id.editTextpetName);
		reportTel = (EditText) findViewById(R.id.editTextpetReportTel);
		petFeatures = (EditText) findViewById(R.id.editTextpetFeatures);
		reportMsg = (EditText) findViewById(R.id.editTextReportMsg);
		takePic = (ImageButton) findViewById(R.id.btnTakePhotoReport);
		choosePic = (ImageButton) findViewById(R.id.btnSelectPhotoReport);
		fancyCoverFlow = (FancyCoverFlow)findViewById(R.id.fancyCoverFlow);
		map=((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.reportMap)).getMap();
		
		takePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, TAKE_PICTURE);
			}
		});
		
		choosePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
		        intent.setType("image/*");
		        intent.setAction(Intent.ACTION_GET_CONTENT);
		        startActivityForResult(Intent.createChooser(intent,"Elige una imagen"), SELECT_PICTURE);
			}
		});
		
		initSpinners();
		initFancy();
		
		if(AppCache.getMyLocation()==null)
			new AsyncTaskLocation().execute();
		else
			muestraPosicion(AppCache.getMyLocation());
	}
	
	private void initSpinners(){
		ReportTypeAdapter reportTypeAdapter=new ReportTypeAdapter(this);
		spinnerReportType.setAdapter(reportTypeAdapter);
		
		String[] ageRangeArray = { "0 - 3 meses", "3 - 6 meses", "6 - 9 meses","9 meses - 1 año", 
				"1 - 4 años", "4 - 8 años", "8 - 12 años","12 en adelante" };
		
		ArrayAdapter<String> ageRangesAdapter = new ArrayAdapter<String>(this,R.layout.spinner_report_age, ageRangeArray);
		spinnerPetAge.setAdapter(ageRangesAdapter);
		
		PetTypeAdapter petTypeAdapter=new PetTypeAdapter(this);
		spinnerPetType.setAdapter(petTypeAdapter);
	}
	
	private void initFancy(){
		petImages = new ArrayList<Bitmap>();
		imagesAdapter=new FancyCoverFlowSampleAdapter(petImages);
		fancyCoverFlow.setAdapter(imagesAdapter);
		fancyCoverFlow.setUnselectedAlpha(1.0f);
		fancyCoverFlow.setUnselectedSaturation(0.0f);
		fancyCoverFlow.setUnselectedScale(0.5f);
		fancyCoverFlow.setMaxRotation(0);
		fancyCoverFlow.setScaleDownGravity(0.2f);
		fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
	}
	
	private void muestraPosicion(Location location) {

		LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
		
		CameraPosition camPos = new CameraPosition.Builder()
		.target(coordenada).zoom(15).build();

		CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camPos);

		map.moveCamera(cameraUpdate);
		
		addMarker("Yo", "", location.getLatitude(), location.getLongitude());
	}
	
	private void addMarker(String title, String snippet, double corLat,double corLong) {
		LatLng coordenada = new LatLng(corLat, corLong);

		map.addMarker(new MarkerOptions().position(coordenada).title(title)
				.snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_report_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK) {
	        if (requestCode == SELECT_PICTURE) {
	            Uri selectedImageUri = data.getData();
	            if (Build.VERSION.SDK_INT < 19) {
	            	String selectedImagePath = ImageUtils.getPath(this,selectedImageUri);
	                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
	                updateFacy(bitmap);
	            }
	            else {
	                ParcelFileDescriptor parcelFileDescriptor;
	                try {
	                    parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
	                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
	                    Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
	                    parcelFileDescriptor.close();
	                    updateFacy(image);
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	}
	
	private void updateFacy(Bitmap bitmap){
		petImages.add(bitmap);
		imagesAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		int positionReportType=spinnerReportType.getSelectedItemPosition();
		outState.putInt("POSITION_REPORTYPE", positionReportType);
		outState.putString("NOMBRE", petName.getText().toString());
		int positionPetAge=spinnerPetAge.getSelectedItemPosition();
		outState.putInt("POSITION_PETAGE", positionPetAge);
		int positionPetType=spinnerPetType.getSelectedItemPosition();
		outState.putInt("POSITION_PETTYPE", positionPetType);
		outState.putString("TELEFONO", reportTel.getText().toString());
		outState.putString("CARACTERS", petFeatures.getText().toString());
		outState.putString("MENSAJE", reportMsg.getText().toString());
		if(!petImages.isEmpty()){
			outState.putParcelableArrayList("FOTOS", petImages);
		}
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
			
		super.onRestoreInstanceState(bundle);
		
		spinnerReportType.setSelection(bundle.getInt("POSITION_REPORTYPE"));
		petName.setText(bundle.getString("NOMBRE"));
		spinnerPetAge.setSelection(bundle.getInt("POSITION_PETAGE"));
		spinnerPetType.setSelection(bundle.getInt("POSITION_PETTYPE"));
		reportTel.setText(bundle.getString("TELEFONO"));
		petFeatures.setText(bundle.getString("CARACTERS"));
		reportMsg.setText(bundle.getString("MENSAJE"));
		
		if(bundle.containsKey("FOTOS")){
			ArrayList<Bitmap> arrayPhotos = bundle.getParcelableArrayList("FOTOS"); 
			Iterator<Bitmap> iterator=arrayPhotos.iterator();
			while(iterator.hasNext()){
				petImages.add(iterator.next());
			}
			imagesAdapter.notifyDataSetChanged();
		}	
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

