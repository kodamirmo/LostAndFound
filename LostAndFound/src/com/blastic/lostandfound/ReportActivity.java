package com.blastic.lostandfound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.blastic.lostandfound.adapters.FancyCoverFlowSampleAdapter;
import com.blastic.lostandfound.adapters.PetTypeAdapter;
import com.blastic.lostandfound.adapters.ReportTypeAdapter;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.blastic.lostandfound.R;

public class ReportActivity extends FragmentActivity {

	// creating necessary elements

	private GoogleMap map;

	private FancyCoverFlow fancyCoverFlow;

	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 0;
	private static int SERVICE_DISABLED = 3;

	private Bitmap setphoto;
	private Bitmap bmpBowRotated;

	private Spinner spinnerReportType;
	private Spinner spinnerPetAge;
	private Spinner spinnerPetType;
	private EditText petName;
	private EditText reportTel;
	private EditText petFeatures;
	private EditText reportMsg;
	private ImageButton takePic;
	private ImageButton choosePic;

	private LocationManager locationManager;
	private LocationListener locationListener;
	private LatLng coordenada;
	private Location location;

	static Marker marker;

	// data for age range
	String[] ageRangeArray = { "0 - 3 meses", "3 - 6 meses", "6 - 9 meses",
			"9 meses - 1 año", "1 - 4 años", "4 - 8 años", "8 - 12 años",
			"12 en adelante" };
	

	// array list for images
	ArrayList<Bitmap> fancyPics = new ArrayList<Bitmap>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		// calling Bitmap from Home
		Intent mIntent = getIntent();
		Bitmap bitmap = (Bitmap) mIntent.getParcelableExtra("BitmapImage");

		// calling layout elements

		spinnerReportType = (Spinner) findViewById(R.id.spinnerReportType);
		spinnerPetAge = (Spinner) findViewById(R.id.spinnerReportPetAge);
		spinnerPetType = (Spinner) findViewById(R.id.spinnerReportPetType);
		petName = (EditText) findViewById(R.id.editTextpetName);
		reportTel = (EditText) findViewById(R.id.editTextpetReportTel);
		petFeatures = (EditText) findViewById(R.id.editTextpetFeatures);
		reportMsg = (EditText) findViewById(R.id.editTextReportMsg);

		// adding adapter for types
		ReportTypeAdapter reportTypeAdapter=new ReportTypeAdapter(this, ReportTypeAdapter.getReportTypes());
		spinnerReportType.setAdapter(reportTypeAdapter);

		// adapter for age range
		ArrayAdapter<String> ageRangesAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_report_age, ageRangeArray);
		spinnerPetAge.setAdapter(ageRangesAdapter);

		// adapter for pet types
		PetTypeAdapter petTypeAdapter=new PetTypeAdapter(this, PetTypeAdapter.getPetTypes());
		spinnerPetType.setAdapter(petTypeAdapter);

		// adding Bitmap from home
		if (bitmap != null) {
			fancyPics.add(bitmap);
			createFancy();
		}

		// take pic intent

		takePic = (ImageButton) findViewById(R.id.btnTakePhotoReport);
		takePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, TAKE_PICTURE);
			}
		});

		// choose pic intent

		choosePic = (ImageButton) findViewById(R.id.btnSelectPhotoReport);
		choosePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent galeryIntent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(galeryIntent, SELECT_PICTURE);
			}
		});
		// SERVICE DISABLE if device not have google play services
		if ((map == null) && (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) != SERVICE_DISABLED)){
			try {
				initMap();
			} catch (GooglePlayServicesNotAvailableException e) {
				e.printStackTrace();
			}
		}
		
		if(savedInstanceState!=null){
			restablecer(savedInstanceState);
		}

	}// Termina on create

	private void restablecer(Bundle bundle) {
		spinnerReportType.setSelection(bundle.getInt("POSITION_REPORTYPE"));
		petName.setText(bundle.getString("NOMBRE"));
		spinnerPetAge.setSelection(bundle.getInt("POSITION_PETAGE"));
		spinnerPetType.setSelection(bundle.getInt("POSITION_PETTYPE"));
		reportTel.setText(bundle.getString("TELEFONO"));
		petFeatures.setText(bundle.getString("CARACTERS"));
		reportMsg.setText(bundle.getString("MENSAJE"));
		ArrayList<Bitmap> arrayPhotos = bundle.getParcelableArrayList("FOTOS"); 
		for(int i=0; i<arrayPhotos.size();i++){
			fancyPics.add(arrayPhotos.get(i));
			createFancy();
		}
		
	}

	private void initMap() throws GooglePlayServicesNotAvailableException {
		// Obtenemos una referencia al LocationManager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Obtenemos la última posición conocida
		location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Mostramos la última posición conocida
		muestraPosicion(location);

		// Nos registramos para recibir actualizaciones de la posición
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				muestraPosicion(location);
			}

			public void onProviderDisabled(String provider) {
				/*if (location == null) {
					Toast toast2 = Toast.makeText(getApplicationContext(),
							"Es necesario tener el GPS habilitado",
							Toast.LENGTH_SHORT);
					toast2.show();
					Intent intent = new Intent(
							android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
				}*/
			}

			public void onProviderEnabled(String provider) {
				Log.i("Provider Status: ", "Provider ON");
				Toast toast1 = Toast.makeText(getApplicationContext(),
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

	public void muestraPosicion(Location loc) {

		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.reportMap)).getMap();
		}

		if (loc != null) {
			coordenada = new LatLng(loc.getLatitude(), loc.getLongitude());
			if (marker != null) {
				marker.remove();
			}
			addMarker();
			/*Toast.makeText(ReportActivity.this,
					"¡Tu ubicación ya ha sido actualizada!", Toast.LENGTH_SHORT)
					.show();*/
			if (locationListener != null)
				locationManager.removeUpdates(locationListener);

		} else 
			coordenada = new LatLng(19.4326018, -99.1332049);

		CameraPosition camPos = new CameraPosition.Builder().target(coordenada)
				.zoom(15).build();

		CameraUpdate cameraUpdate = CameraUpdateFactory
				.newCameraPosition(camPos);
		map.moveCamera(cameraUpdate);
	}

	public void addMarker() {
		marker = map.addMarker(new MarkerOptions().position(coordenada).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Review if the image come from the camera or the gallery
		Toast toast2 = Toast.makeText(getApplicationContext(),
				"Ocurrió un error", Toast.LENGTH_SHORT);

		if (resultCode == RESULT_OK) {

			// If comes from camera
			if (requestCode == TAKE_PICTURE) {
				if (data != null) {
					if (data.hasExtra("data")) {
						String pic = System.currentTimeMillis() + ".jpg";
						Bitmap photo = (Bitmap) data.getExtras().get("data");

						try {
							File temp = new File(
									Environment.getExternalStorageDirectory(),
									File.separator + "Pawhub");
							if (!temp.exists())
								temp.mkdirs();
							OutputStream stream = new FileOutputStream(
									Environment.getExternalStorageDirectory()
											+ File.separator + "Pawhub"
											+ File.separator + pic);
							photo.prepareToDraw();
							photo.compress(CompressFormat.JPEG, 0, stream);
							stream.flush();
							stream.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							toast2.show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							toast2.show();
						}

						photo = Bitmap.createBitmap(photo, 0, 0,
								photo.getWidth(), photo.getHeight());
						fancyPics.add(photo);
						// Fancy Cover for Images

						createFancy();

					} else {
						toast2.show();
					}
				}

				// If comes from gallery
			} else if (requestCode == SELECT_PICTURE) {

				try {
					this.imageFromGallery(resultCode, data, 200, 200);
					fancyPics.add(setphoto);
					// Fancy Cover for Images
					Log.i("array", "" + fancyPics.size());

					createFancy();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					toast2.show();
				}

			}

		}

	}

	// Takes the image chosen and resizes to show it in image view
	private void imageFromGallery(int resultCode, Intent data, int reqWidth,
			int reqHeight) throws IOException {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

		String profile_Path = cursor.getString(columnIndex);
		cursor.close();

		// First decode with inJustDecodeBounds=true to check dimensions

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		setphoto = BitmapFactory.decodeFile(profile_Path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		ExifInterface ei = new ExifInterface(profile_Path);
		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
				ExifInterface.ORIENTATION_NORMAL);

		setphoto = BitmapFactory.decodeFile(profile_Path, options);

		switch (orientation) {
		case ExifInterface.ORIENTATION_ROTATE_90:
			rotateImage(setphoto, 90);
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			rotateImage(setphoto, 180);
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			rotateImage(setphoto, 270);
			break;
		}

	}

	private void rotateImage(Bitmap setphoto2, int i) {
		// TODO Auto-generated method stub
		Matrix matrix = new Matrix();
		matrix.setRotate(i);
		bmpBowRotated = Bitmap.createBitmap(setphoto2, 0, 0,
				setphoto2.getWidth(), setphoto2.getHeight(), matrix, false);
		setphoto = bmpBowRotated;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_report_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_share:
			shareIntent();
			return true;
		case R.id.action_publish:
			openPublish();
			return true;
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void openSettings() {
		if (locationListener != null)
			locationManager.removeUpdates(locationListener);

		//Intent openSet = new Intent(this, SettingsActivity.class);
		//startActivity(openSet);
	}

	private void openPublish() {
		if (locationListener != null)
			locationManager.removeUpdates(locationListener);

		Intent detailsIntent = new Intent(this, DetailsActivity.class);
		detailsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(detailsIntent);

	}

	private void shareIntent() {

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharingIntent.setType("text/html");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
				"Texto a compartir");
		startActivity(Intent.createChooser(sharingIntent, "Compartir con"));

	}
	
	private void createFancy() {
		this.fancyCoverFlow = (FancyCoverFlow) this
				.findViewById(R.id.fancyCoverFlow);

		this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter(
				fancyPics));
		this.fancyCoverFlow.setUnselectedAlpha(1.0f);
		this.fancyCoverFlow.setUnselectedSaturation(0.0f);
		this.fancyCoverFlow.setUnselectedScale(0.5f);
		this.fancyCoverFlow.setMaxRotation(0);
		this.fancyCoverFlow.setScaleDownGravity(0.2f);
		this.fancyCoverFlow
				.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (locationListener != null)
			locationManager.removeUpdates(locationListener);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
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
		if(!fancyPics.isEmpty()){
			outState.putParcelableArrayList("FOTOS", fancyPics);
		}
		
		super.onSaveInstanceState(outState);
	}

}
