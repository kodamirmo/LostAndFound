package com.blastic.lostandfound;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.blastic.lostandfound.R;
import com.blastic.lostandfound.adapters.UserTypeAdapter;
import com.blastic.lostandfound.utils.ImageUtils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private Spinner spinnerUserSex;
	private TextView terms;
	private Button birthBtn;
	private Calendar myCalendar;
	private ImageButton takePicBtn;
	private ImageButton choosePicBtn;
	private ImageView imgViewUserPic;
	private EditText userName;
	private EditText userMail;
	private EditText userCountry;
	private EditText userCity;
	private EditText userPass;
	private EditText userPassCon;
	private Bitmap temPhoto;

	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 0;
	
	private DatePickerDialog.OnDateSetListener onDateLietener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initViews();
	}

	private void initViews() {

		userName = (EditText) findViewById(R.id.registerUserName);
		userMail = (EditText) findViewById(R.id.registerUserMail);
		userCountry = (EditText) findViewById(R.id.registerUserCountry);
		userCity = (EditText) findViewById(R.id.registerUserCity);
		userPass = (EditText) findViewById(R.id.registerUserPass);
		userPassCon = (EditText) findViewById(R.id.registerUserPassConf);
		birthBtn = (Button) findViewById(R.id.btnUserBirthday);
		spinnerUserSex = (Spinner) findViewById(R.id.spinnerUserSex);
		terms = (TextView) findViewById(R.id.textViewTerms);
		imgViewUserPic = (ImageView) findViewById(R.id.imgViewUserPic);
		takePicBtn = (ImageButton) findViewById(R.id.btnTakePhotoRegister);
		choosePicBtn = (ImageButton) findViewById(R.id.btnSelectPhotoRegister);

		spinnerUserSex.setAdapter(new UserTypeAdapter(this));
		myCalendar = Calendar.getInstance();
		onDateLietener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}

		};
		terms.setMovementMethod(LinkMovementMethod.getInstance());
		takePicBtn.setOnClickListener(this);
		choosePicBtn.setOnClickListener(this);
		imgViewUserPic.setScaleType(ImageView.ScaleType.FIT_CENTER);
		
	}

	private void initActionImage() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, TAKE_PICTURE);
	}

	private void initActionPick() {
		Intent galeryIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		this.startActivityForResult(galeryIntent, SELECT_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			if (requestCode == TAKE_PICTURE) {
				if (data != null) {
					if (data.hasExtra("data")) {
						Bitmap photo = (Bitmap) data.getExtras().get("data");
						ImageUtils.saveBitmapAsAPicture(this, photo);
						imgViewUserPic.setImageBitmap(photo);
					} else {
						Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_LONG).show();
					}
				}
			} else if (requestCode == SELECT_PICTURE) {
				try {
					Bitmap photo = ImageUtils.imageFromGallery(this, data, 300, 300);
					imgViewUserPic.setImageBitmap(photo);

				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.register_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_publish:
			register();
			return true;
		case R.id.action_cancel:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void register(){
		if(checkFields()){
			
		}else{
			
		}
	}
	
	private boolean checkFields(){
		boolean emptyFields=false;
		boolean noMatchPasswords=false;
		if(!userName.getText().toString().trim().equals("")){
			userName.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			emptyFields=true;
		}else
			userName.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		if(!userMail.getText().toString().trim().equals("")){
			userMail.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			emptyFields=true;
		}else
			userMail.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		if(!birthBtn.getText().toString().trim().equals("Cumpleaños")){
			birthBtn.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			emptyFields=true;
		}else
			birthBtn.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		if(!userCountry.getText().toString().trim().equals("")){
			userCountry.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			emptyFields=true;
		}else
			userCountry.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		if(!userCity.getText().toString().trim().equals("")){
			userCity.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			emptyFields=true;
		}else
			userCity.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		if(!userPass.getText().toString().trim().equals("")){
			userPass.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			emptyFields=true;
		}else
			userPass.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		if(!userPassCon.getText().toString().trim().equals("")){
			userPassCon.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			emptyFields=true;
		}else
			userPassCon.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		
		if(!userPassCon.getText().toString().trim().equals("") && !userPass.getText().toString().trim().equals("")){
			if(userPass.getText().toString().equals(userPassCon.getText().toString())){
				userPass.setBackgroundResource(R.drawable.corner_and_border_red_editext);
				userPassCon.setBackgroundResource(R.drawable.corner_and_border_red_editext);
				noMatchPasswords=true;
			}else{
				userPass.setBackgroundResource(R.drawable.corner_and_border_white_editext);
				userPassCon.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			}
		}else{
			
		}
		
		
		if(emptyFields && noMatchPasswords)
			return true;
		
		String message="";
		if(!emptyFields && !noMatchPasswords)
			message="Las contraseñas no coinciden.\nLlene todos los campos";
		else if(!emptyFields)
			message="Llene todos los campos";
		else if(!noMatchPasswords)
			message="Las contraseñas no coinciden";
		
		Toast.makeText(this,message, Toast.LENGTH_LONG).show();
		return false;
	}


	// Method for update the text in button for birthday
	private void updateLabel() {
		String myFormat = "dd/MM/yy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		birthBtn.setText("Cumpleaños: " + sdf.format(myCalendar.getTime()));
	}

	@Override
	public void onClick(View v) {
		if (v == choosePicBtn)
			initActionPick();
		else if (v == takePicBtn)
			initActionImage();
		else if(v==birthBtn){
			new DatePickerDialog(RegisterActivity.this,onDateLietener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
					myCalendar.get(Calendar.DAY_OF_MONTH)).show();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("NOMBRE", userName.getText().toString());
		int positionGenre = spinnerUserSex.getSelectedItemPosition();
		outState.putInt("GENERO", positionGenre);
		outState.putString("BIRTHDAY", birthBtn.getText().toString());
		outState.putString("EMAIL", userMail.getText().toString());
		outState.putString("PAIS", userCountry.getText().toString());
		outState.putString("CIUDAD", userCity.getText().toString());
		outState.putString("PASS", userPass.getText().toString());
		outState.putString("CONFIRM", userPassCon.getText().toString());
		if ((temPhoto != null)) {
			outState.putParcelable("PHOTO", temPhoto);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		userName.setText(bundle.getString("NOMBRE"));
		spinnerUserSex.setSelection(bundle.getInt("GENERO"));
		birthBtn.setText(bundle.getString("BIRTHDAY"));
		userMail.setText(bundle.getString("EMAIL"));
		userCountry.setText(bundle.getString("PAIS"));
		userCity.setText(bundle.getString("CIUDAD"));
		userPass.setText(bundle.getString("PASS"));
		userPassCon.setText(bundle.getString("CONFIRM"));
		Bitmap photoBitmap = bundle.getParcelable("PHOTO");
		imgViewUserPic.setImageBitmap(photoBitmap);
		temPhoto = photoBitmap;
	}
}
