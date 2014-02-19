package com.blastic.lostandfound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.blastic.lostandfound.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{

	private Spinner spinnerUserSex;
	private TextView terms;
	private Button birthBtn;
	private Calendar myCalendar;
	private ImageButton takePicBtn;
	private ImageButton choosePicBtn;
	private ImageView imgViewUserPic;

	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 0;
	private Bitmap setphoto;
	private Bitmap bmpBowRotated;
	
	//data from user
	private EditText userName;
	private EditText userMail;
	private EditText userCountry;
	private EditText userCity;
	private EditText userPass;
	private EditText userPassCon;
	private Bitmap temPhoto;

	// data for spinner for sex
	String[] userSex = { "Mujer", "Hombre" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	
		// calling layout elements
		userName = (EditText) findViewById(R.id.registerUserName);
		userMail = (EditText) findViewById(R.id.registerUserMail);
		userCountry = (EditText) findViewById(R.id.registerUserCountry);
		userCity = (EditText) findViewById(R.id.registerUserCity);
		userPass = (EditText) findViewById(R.id.registerUserPass);
		userPassCon = (EditText) findViewById(R.id.registerUserPassConf);

		// set adapter for spinner sex
		spinnerUserSex = (Spinner) findViewById(R.id.spinnerUserSex);
		spinnerUserSex.setAdapter(new TypesAdapter(RegisterActivity.this,
				R.layout.spinner_register, userSex));

		// properties for date picker
		myCalendar = Calendar.getInstance();
		birthBtn = (Button) findViewById(R.id.btnUserBirthday);

		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();

			}

		};

		birthBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(RegisterActivity.this, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		// set onClick listener for terms of use
		terms = (TextView) findViewById(R.id.textViewTerms);
		terms.setMovementMethod(LinkMovementMethod.getInstance());

		// take pic intent

		imgViewUserPic = (ImageView) findViewById(R.id.imgViewUserPic);

		takePicBtn = (ImageButton) findViewById(R.id.btnTakePhotoRegister);
		takePicBtn.setOnClickListener(this);

		// choose pic intent

		choosePicBtn = (ImageButton) findViewById(R.id.btnSelectPhotoRegister);
		choosePicBtn.setOnClickListener(this);
		
		if(savedInstanceState!=null)
			onRestore(savedInstanceState);

	}

	private void initActionImage(){
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//startActivity(cameraIntent);
		this.startActivityForResult(cameraIntent, TAKE_PICTURE);
	}
	private void initActionPick(){
		Intent galeryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		//startActivity(galeryIntent);
		this.startActivityForResult(galeryIntent, SELECT_PICTURE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Toast toast2 = Toast.makeText(getApplicationContext(),"Ocurrió un error", Toast.LENGTH_SHORT);

		// Review if the image come from the camera or the gallery

		if (resultCode == RESULT_OK) {
			// If comes from camera
			if (requestCode == TAKE_PICTURE) {
				if (data != null) {
					if (data.hasExtra("data")) {
						String pic = System.currentTimeMillis() + ".jpg";
						Bitmap photo = (Bitmap) data.getExtras().get("data");

						try {
							File temp = new File(Environment.getExternalStorageDirectory(), File.separator + "Pawhub");
							if (!temp.exists())
								temp.mkdirs();
							OutputStream stream = new FileOutputStream(
									Environment.getExternalStorageDirectory()+ File.separator + "Pawhub"
											+ File.separator + pic);
							photo.compress(CompressFormat.JPEG, 100, stream);
							stream.flush();
							stream.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							toast2.show();
						} catch (IOException e) {
							e.printStackTrace();
							toast2.show();
						}

						temPhoto = photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(),photo.getHeight());
						imgViewUserPic.setImageBitmap(photo);
						int w = photo.getWidth();
						int h = photo.getHeight();
						Log.i("h"+h,"w"+w);
						imgViewUserPic.setScaleType(ImageView.ScaleType.FIT_CENTER);

					} else {
						toast2.show();
					}
				}

				// If comes from gallery
			} else if (requestCode == SELECT_PICTURE) {

				try {
					this.imageFromGallery(resultCode, data, 300, 300);
					temPhoto=setphoto;
					imgViewUserPic.setImageBitmap(setphoto);

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
		inflater.inflate(R.menu.register_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_publish:
			openPublish();
			return true;
		case R.id.action_cancel:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void openPublish() {
		String userP = userPass.getText().toString();
		String userPC = userPassCon.getText().toString();
		
		if(userP.contentEquals(userPC))
			Toast.makeText(this, "Esta opción aún no está disponible en el demo", Toast.LENGTH_LONG).show();
		else{
			Toast.makeText(this, "El password y su confirmación no coinciden", Toast.LENGTH_LONG).show();
			userPass.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			userPassCon.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		}
	}

	// Method for update the text in button for birthday
	private void updateLabel() {

		String myFormat = "dd/MM/yy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		birthBtn.setText("Cumpleaños: " + sdf.format(myCalendar.getTime()));
	}

	public class TypesAdapter extends ArrayAdapter<String> {

		public TypesAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.spinner_register, parent,
					false);
			TextView label = (TextView) row
					.findViewById(R.id.textViewSpinnerRegisterAdptr);
			label.setText(userSex[position]);

			return row;
		}
	}

	@Override
	public void onClick(View v) {
		if(v==choosePicBtn)
			initActionPick();
		else if(v==takePicBtn)
			initActionImage();
	}

	protected void onSaveInstanceState(Bundle outState){
		outState.putString("NOMBRE", userName.getText().toString());
		int positionGenre=spinnerUserSex.getSelectedItemPosition();
		outState.putInt("GENERO", positionGenre);
		outState.putString("BIRTHDAY", birthBtn.getText().toString());
		outState.putString("EMAIL", userMail.getText().toString());
		outState.putString("PAIS", userCountry.getText().toString());
		outState.putString("CIUDAD", userCity.getText().toString());
		outState.putString("PASS", userPass.getText().toString());
		outState.putString("CONFIRM", userPassCon.getText().toString());
		if((temPhoto!=null)){
			outState.putParcelable("PHOTO", temPhoto);
		}
		super.onSaveInstanceState(outState);
	}
	
	private void onRestore(Bundle bundle){
		userName.setText(bundle.getString("NOMBRE"));
		spinnerUserSex.setSelection(bundle.getInt("GENERO"));
		birthBtn.setText(bundle.getString("BIRTHDAY"));
		userMail.setText(bundle.getString("EMAIL"));
		userCountry.setText(bundle.getString("PAIS"));
		userCity.setText(bundle.getString("CIUDAD"));
		userPass.setText(bundle.getString("PASS"));
		userPassCon.setText(bundle.getString("CONFIRM"));
		Bitmap photoBitmap=bundle.getParcelable("PHOTO");
		imgViewUserPic.setImageBitmap(photoBitmap);
		temPhoto=photoBitmap;
	}
}
