package com.blastic.lostandfound;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.blastic.lostandfound.tw.TwitterApp;
import com.blastic.lostandfound.tw.TwitterApp.TwDialogListener;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.blastic.lostandfound.R;

public class LoginScreen extends Activity implements OnClickListener {
	
	private final String ENTER_USERNAME="Ingresa un usuario";
    private final String ENTER_PASSWORD="Ingresa la contraseña";
    private final String INCORRECT_USERNAME="Usuario incorrecto";
    private final String INCORRECT_PASSWORD="Contraseña incorrecta";

	// For Twitter
	private TwitterApp mTwitter;
	private ImageButton mTwitterBtn;

	private static final String twitter_consumer_key = "CLlR7aD9Yp55nfbLCLUxSw";
	private static final String twitter_secret_key = "gbnIccdYwZ9ILDRXrYDPq9mlEv6FuW0zPy1mjThA";

	private ImageButton btnLogin;
	private ImageButton btnLoginFacebook;
	private ImageButton btnGuest;
	private Button btnRegister;

	// For Facebook

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {

		@Override
		public void call(final Session session, SessionState state,
				Exception exception) {

			if (session.isOpened()) {

				Request.newMeRequest(session, new Request.GraphUserCallback() {

					@Override
					public void onCompleted(GraphUser user, Response response) {

						if (user != null) {
							String id = user.getId();
							String name = user.getName();

							SharedPreferences sharedPref = LoginScreen.this.getSharedPreferences("userPrefs",MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedPref.edit();
							editor.putString("username",""+name);
							editor.commit();
							
							Toast.makeText(LoginScreen.this,
									"Conectado mediante Facebook como " + name,
									Toast.LENGTH_LONG).show();
							
							simpleLogin();

						} else {
							Toast.makeText(LoginScreen.this,
									"No se pudo iniciar sesión",
									Toast.LENGTH_LONG).show();
						}
					}
				}).executeAsync();
			} else {
				Toast.makeText(LoginScreen.this,
						"Espera mientras se inicia la sesión",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		
		SharedPreferences preferences = this.getSharedPreferences("userPrefs",MODE_PRIVATE);
		String prefName = preferences.getString("username", "nothing");

		initViews();
	}

	private void initViews() {
		TextView appTitle = (TextView) findViewById(R.id.login_app_tittle);

		SpannableString text = new SpannableString("" + appTitle.getText());
		text.setSpan(new ForegroundColorSpan(Color.rgb(99, 194, 208)), 0, 4, 0);
		text.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 5, 7, 1);
		text.setSpan(new ForegroundColorSpan(Color.rgb(255, 212, 0)), 7,
				text.length(), 2);
		appTitle.setText(text, BufferType.SPANNABLE);

		TextView appsubTitle = (TextView) findViewById(R.id.login_app_subtittle);
		appsubTitle.setTypeface(null, Typeface.BOLD);

		btnLoginFacebook = (ImageButton) findViewById(R.id.loginFbBtn);
		btnLogin = (ImageButton) findViewById(R.id.loginBtn);
		btnGuest = (ImageButton) findViewById(R.id.loginGuestBtn); 
		btnRegister = (Button) findViewById(R.id.register);
		mTwitterBtn = (ImageButton) findViewById(R.id.loginTwBtn);

		btnLogin.setOnClickListener(this);
		btnLoginFacebook.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		btnGuest.setOnClickListener(this);
		mTwitterBtn.setOnClickListener(this);

		mTwitter = new TwitterApp(this, twitter_consumer_key,
				twitter_secret_key);

		mTwitter.setListener(mTwLoginDialogListener);

		if (mTwitter.hasAccessToken()) {
			simpleLogin();
		}

		if (Session.getActiveSession().getPermissions().size() != 0)
			simpleLogin();
	}

	private void initFacebookSession() {
		Session.openActiveSession(this, true, callback);
	}

	private void onTwitterClick() {
		if (mTwitter.hasAccessToken()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage("¿Borrar la conexión actual de Twitter?")
					.setCancelable(false)
					.setPositiveButton("Si",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									mTwitter.resetAccessToken();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();

									Toast.makeText(getApplicationContext(),
											"Se canceló la petición",
											Toast.LENGTH_LONG).show();
								}
							});
			final AlertDialog alert = builder.create();

			alert.show();
		} else {
			mTwitter.authorize();
		}

	}

	private void onRegisteredLogin() {
		/* EditText user = (EditText) findViewById(R.id.editTextNombre);
		EditText password = (EditText) findViewById(R.id.editTextPassword);

		if (user.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), ENTER_USERNAME,
					Toast.LENGTH_SHORT).show();
			return;
		} else if (password.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), ENTER_PASSWORD,
					Toast.LENGTH_SHORT).show();
			return;
		}else if(!user.getText().toString().equals("demo")){
			Toast.makeText(getApplicationContext(), INCORRECT_USERNAME,
					Toast.LENGTH_SHORT).show();
			return;
		} else if(!password.getText().toString().equals("demo")){
			Toast.makeText(getApplicationContext(), INCORRECT_PASSWORD,
					Toast.LENGTH_SHORT).show();
			return;
		}*/
		
		SharedPreferences sharedPref = this.getSharedPreferences("userPrefs",MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("username","loginD");
		editor.commit();
		
		/*Toast.makeText(LoginScreen.this,
				"Conectado como demo",
				Toast.LENGTH_LONG).show();*/
		simpleLogin();

	}

	public void simpleLogin() {
		Intent home = new Intent(this, Home.class);
		home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(home);
		this.finish();
	}

	public void registerUser() {
		Intent registerIntent = new Intent(this, RegisterActivity.class);
		startActivity(registerIntent);
	}

	@Override
	public void onClick(View v) {
		if (v == btnLoginFacebook)
			initFacebookSession();
		else if (v == btnLogin)
			onRegisteredLogin();
		else if (v == btnRegister)
			registerUser();
		else if (v == mTwitterBtn)
			onTwitterClick();
		else if(v == btnGuest){
			SharedPreferences sharedPref = this.getSharedPreferences("userPrefs",MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("username","guest");
			editor.commit();
			simpleLogin();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
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

			Toast.makeText(LoginScreen.this,
					"Conectado mediante Twitter como " + username,
					Toast.LENGTH_LONG).show();
			SharedPreferences sharedPref = LoginScreen.this.getSharedPreferences("userPrefs",MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("username",""+username);
			editor.commit();
			simpleLogin();
		}

		@Override
		public void onError(String value) {
			Toast.makeText(LoginScreen.this, "La conexión con Twitter falló",
					Toast.LENGTH_LONG).show();
		}
	};
}