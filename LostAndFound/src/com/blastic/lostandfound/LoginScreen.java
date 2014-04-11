package com.blastic.lostandfound;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.blastic.lostandfound.dialogs.DialogConfirmGuest;
import com.blastic.lostandfound.preferences.UserData;
import com.blastic.lostandfound.tw.TwitterApp;
import com.blastic.lostandfound.tw.TwitterApp.TwDialogListener;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.blastic.lostandfound.R;

public class LoginScreen extends ActionBarActivity implements OnClickListener {

	// For Twitter
	private TwitterApp mTwitter;
	private ImageButton mTwitterBtn;

	// For Facebook
	private UiLifecycleHelper uiHelper;
		
	private ImageButton btnLogin;
	private ImageButton btnLoginFacebook;
	private ImageButton btnGuest;
	private Button btnRegister;
	private EditText etUsername;
	private EditText etPassowrd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
	
		initActionBar();
		initViews();
		initSocialNetworks(savedInstanceState);
	}
	
	private void initActionBar(){
		getSupportActionBar().hide();
	}
	
	private void initSocialNetworks(Bundle savedInstanceState){
		uiHelper = new UiLifecycleHelper(this, facebookCallback);
		uiHelper.onCreate(savedInstanceState);
		
		mTwitter=TwitterApp.getTwitterApp(this);
		mTwitter.setListener(mTwLoginDialogListener);

		if (mTwitter.hasAccessToken()) {
			goToHome();
		}

		if (Session.getActiveSession().getPermissions().size() != 0)
			goToHome();
	}

	private void initViews() {
		
		SpannableString spannable = new SpannableString(getString(R.string.app_name));
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(99, 194, 208)), 0, 4, 0);
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 5, 7, 1);
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(255, 212, 0)), 7,spannable.length(), 2);
		
		final TextView appTitle = (TextView) findViewById(R.id.login_app_tittle);
		appTitle.setText(spannable, BufferType.SPANNABLE);

		btnLoginFacebook = (ImageButton) findViewById(R.id.loginFbBtn);
		btnLogin = (ImageButton) findViewById(R.id.loginBtn);
		btnGuest = (ImageButton) findViewById(R.id.loginGuestBtn); 
		btnRegister = (Button) findViewById(R.id.register);
		mTwitterBtn = (ImageButton) findViewById(R.id.loginTwBtn);
		etUsername = (EditText) findViewById(R.id.etLoginUser);
		etPassowrd = (EditText)findViewById(R.id.etLoginPassword);

		btnLogin.setOnClickListener(this);
		btnLoginFacebook.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		btnGuest.setOnClickListener(this);
		mTwitterBtn.setOnClickListener(this);
		
	}

	private void loginInFacebook() {
		Session.openActiveSession(this, true, facebookCallback);
	}

	private void loginInTwitter() {
		if (mTwitter.hasAccessToken()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage("��Borrar la conexi��n actual de Twitter?")
					.setCancelable(false)
					.setPositiveButton("Si",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									mTwitter.resetAccessToken();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									dialog.cancel();
									showMessage("Login cancelado");
								}
							});
			final AlertDialog alert = builder.create();
			alert.show();
		} else {
			mTwitter.authorize();
		}
	}
	
	private void loginPawhub(){
		if(validateFields())
			if(checkPawHubData())
				goToHome();
	}
	
	private void loginAsAGuest(){
		DialogConfirmGuest dialog=new DialogConfirmGuest();
		dialog.show(getSupportFragmentManager(), "GUEST");
	}


	public void goToHome() {
		Intent home = new Intent(this, Home.class);
		startActivity(home);
		this.finish();
	}

	private void registerUser() {
		Intent registerIntent = new Intent(this, RegisterActivity.class);
		startActivity(registerIntent);
	}
	
	private boolean validateFields(){
		if(etUsername.getText().toString().trim().equals("")){
			showMessage("Username esta vacio");
			return false;
		}
		if(etPassowrd.getText().toString().trim().equals("")){
			showMessage("Password esta vacio");
			return false;
		}
		return true;
	}
	
	private boolean checkPawHubData(){
		//Call to WebService
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == btnLoginFacebook)
			loginInFacebook();
		else if (v == btnLogin)
			loginPawhub();
		else if (v == mTwitterBtn)
			loginInTwitter();
		else if(v == btnGuest)
			loginAsAGuest();
		else if (v == btnRegister)
			registerUser();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

		@Override
		public void onComplete(String value) {
			String username = mTwitter.getUsername();
			username = (username.equals("")) ? "Sin nombre" : username;

			showMessage("Conectado mediante Twitter como " + username);
			UserData.setUsername(getApplicationContext(), username);
			goToHome();
		}

		@Override
		public void onError(String value) {
			showMessage("La conex��n con Twitter fallo");
		}
	};
	
	private Session.StatusCallback facebookCallback = new Session.StatusCallback() {

		@Override
		public void call(final Session session, SessionState state,Exception exception) {

			if (session.isOpened()) {

				Request.newMeRequest(session, new Request.GraphUserCallback() {

					@Override
					public void onCompleted(GraphUser user, Response response) {

						if (user != null) {
							
							String username = user.getName();
							UserData.setUsername(getApplicationContext(), username);
							showMessage("Conectado mediante Facebook como " + username);						
							goToHome();

						} else {
							showMessage("La conexi��n con Facebook fallo");
						}
					}
				}).executeAsync();
			} else {
				showMessage("Iniciando sesi��n");
			}
		}
	};
	
	private void showMessage(String message){
		Toast.makeText(LoginScreen.this,message,Toast.LENGTH_LONG).show();
	}
}