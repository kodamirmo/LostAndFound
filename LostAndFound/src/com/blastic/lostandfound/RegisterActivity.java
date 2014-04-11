package com.blastic.lostandfound;

import java.io.IOException;
import com.blastic.lostandfound.R;
import com.blastic.lostandfound.adapters.UserTypeAdapter;
import com.blastic.lostandfound.config.Config;
import com.blastic.lostandfound.dialogs.DialogNoPhoto;
import com.blastic.lostandfound.models.UserRegister;
import com.blastic.lostandfound.utils.ImageUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity implements OnClickListener {

	private Spinner spinnerUserSex;
	private TextView tvTerms;
	private ImageButton btnTakePic;
	private ImageButton btnChoosePic;
	private ImageView imgViewUserPic;
	private EditText etUsername;
	private EditText etEmail;
	private EditText etCountry;
	private EditText etCity;
	private EditText etPassword;
	private EditText etPasswordConfirm;
	private EditText etAge;
	private Bitmap temPhoto;

	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 0;
	
	private boolean selectAPicture;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		activity=this;
		initActionBar();
		initViews();
	}
	
	private void initActionBar(){
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(Config.actionBarColor)));
	}

	private void initViews() {

		selectAPicture=false;
		etUsername = (EditText) findViewById(R.id.registerUserName);
		etEmail = (EditText) findViewById(R.id.registerUserMail);
		etCountry = (EditText) findViewById(R.id.registerUserCountry);
		etCity = (EditText) findViewById(R.id.registerUserCity);
		etPassword = (EditText) findViewById(R.id.registerUserPass);
		etPasswordConfirm = (EditText) findViewById(R.id.registerUserPassConf);
		etAge = (EditText) findViewById(R.id.etUserBirthday);
		spinnerUserSex = (Spinner) findViewById(R.id.spinnerUserSex);
		tvTerms = (TextView) findViewById(R.id.textViewTerms);
		imgViewUserPic = (ImageView) findViewById(R.id.imgViewUserPic);
		btnTakePic = (ImageButton) findViewById(R.id.btnTakePhotoRegister);
		btnChoosePic = (ImageButton) findViewById(R.id.btnSelectPhotoRegister);

		spinnerUserSex.setAdapter(new UserTypeAdapter(this));

		tvTerms.setMovementMethod(LinkMovementMethod.getInstance());
		btnTakePic.setOnClickListener(this);
		btnChoosePic.setOnClickListener(this);
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
						selectAPicture=true;
					} else {
						Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_LONG).show();
					}
				}
			} else if (requestCode == SELECT_PICTURE) {
				try {
					Bitmap photo = ImageUtils.imageFromGallery(this, data, 300, 300);
					imgViewUserPic.setImageBitmap(photo);
					selectAPicture=true;
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
			registerClick();
			return true;
		case R.id.action_cancel:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void registerClick(){
		if(checkFields()){
			if(!selectAPicture){
				DialogNoPhoto dialog=new DialogNoPhoto();
				dialog.show(getSupportFragmentManager(), "DIALOG");
			}else
				doRegister();
		}
	}
	
	public void doRegister(){
		UserRegister register=new UserRegister();
		new AsyncRegister().execute(register);
	}
	
	private boolean checkFields(){
		
		boolean emptyFields=false;
		boolean matchPasswords=false;
		
		if(etUsername.getText().toString().trim().equals("")){
			etUsername.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			emptyFields=true;
		}else
			etUsername.setBackgroundResource(R.drawable.corner_and_border_white_editext);
		if(etEmail.getText().toString().trim().equals("")){
			etEmail.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			emptyFields=true;
		}else
			etEmail.setBackgroundResource(R.drawable.corner_and_border_white_editext);
		if(etAge.getText().toString().trim().equals("")){
			etAge.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			emptyFields=true;
		}else
			etAge.setBackgroundResource(R.drawable.corner_and_border_white_editext);
		if(etCountry.getText().toString().trim().equals("")){
			etCountry.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			emptyFields=true;
		}else
			etCountry.setBackgroundResource(R.drawable.corner_and_border_white_editext);
		if(etCity.getText().toString().trim().equals("")){
			etCity.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			emptyFields=true;
		}else
			etCity.setBackgroundResource(R.drawable.corner_and_border_white_editext);
		if(etPassword.getText().toString().trim().equals("")){
			etPassword.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			emptyFields=true;
		}else
			etPassword.setBackgroundResource(R.drawable.corner_and_border_white_editext);
		if(etPasswordConfirm.getText().toString().trim().equals("")){
			etPasswordConfirm.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			emptyFields=true;
		}else
			etPasswordConfirm.setBackgroundResource(R.drawable.corner_and_border_white_editext);
		
		if(emptyFields){
			Toast.makeText(this,"Llene todos los campos", Toast.LENGTH_LONG).show();
			return false;
		}
			
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if(!etEmail.getText().toString().trim().matches(emailreg)){
			Toast.makeText(this, "Email no valido", Toast.LENGTH_LONG).show();
			return false;
		}
		
		if(etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
			etPassword.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			etPasswordConfirm.setBackgroundResource(R.drawable.corner_and_border_white_editext);
			matchPasswords=true;
		}else{
			etPassword.setBackgroundResource(R.drawable.corner_and_border_red_editext);
			etPasswordConfirm.setBackgroundResource(R.drawable.corner_and_border_red_editext);
		}
		
		if(!matchPasswords){
			Toast.makeText(this,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == btnChoosePic)
			initActionPick();
		else if (v == btnTakePic)
			initActionImage();
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("NOMBRE", etUsername.getText().toString());
		int positionGenre = spinnerUserSex.getSelectedItemPosition();
		outState.putInt("GENERO", positionGenre);
		outState.putString("BIRTHDAY", etAge.getText().toString());
		outState.putString("EMAIL", etEmail.getText().toString());
		outState.putString("PAIS", etCountry.getText().toString());
		outState.putString("CIUDAD", etCity.getText().toString());
		outState.putString("PASS", etPassword.getText().toString());
		outState.putString("CONFIRM", etPasswordConfirm.getText().toString());
		outState.putBoolean("IMAGE", selectAPicture);
		if ((temPhoto != null)) {
			outState.putParcelable("PHOTO", temPhoto);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		selectAPicture=bundle.getBoolean("PHOTO");
		etUsername.setText(bundle.getString("NOMBRE"));
		spinnerUserSex.setSelection(bundle.getInt("GENERO"));
		etAge.setText(bundle.getString("BIRTHDAY"));
		etEmail.setText(bundle.getString("EMAIL"));
		etCountry.setText(bundle.getString("PAIS"));
		etCity.setText(bundle.getString("CIUDAD"));
		etPassword.setText(bundle.getString("PASS"));
		etPasswordConfirm.setText(bundle.getString("CONFIRM"));
		Bitmap photoBitmap = bundle.getParcelable("PHOTO");
		imgViewUserPic.setImageBitmap(photoBitmap);
		temPhoto = photoBitmap;
	}
	
	
	private class AsyncRegister extends AsyncTask<UserRegister, Void,Boolean>{


		protected ProgressDialog dialogo;
		
		@Override
		protected void onPreExecute() {
			dialogo = new ProgressDialog(activity);
			dialogo.setIndeterminate(true);
			dialogo.setMessage("Registrando...");
			dialogo.setTitle("Lost & Found");
			dialogo.setCancelable(false);
			dialogo.show();
		}
		
		@Override
		protected Boolean doInBackground(UserRegister... params) {
			
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dialogo.cancel();
			
			if(result){
				Toast.makeText(activity,"Registrado", Toast.LENGTH_LONG).show();
				activity.finish();
			}else{
				Toast.makeText(activity,"Error al registrarte", Toast.LENGTH_LONG).show();
			}
	
		}	
		
	}
}
