package com.blastic.lostandfound.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class GcmIntentService extends IntentService{

	public GcmIntentService(){
		super("GcmIntentService");
	}
	
	public GcmIntentService(String name) {
		super(name);		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("TAG","Manejador del mensaje");
		
		//Obtener la informacion que me llego del GCM
		Bundle extras = intent.getExtras();
		//Obtener el cliente de GCM
		GoogleCloudMessaging gcm=GoogleCloudMessaging.getInstance(this);
		//Obtener el tipo de mensaje
		String tipoMensaje=gcm.getMessageType(intent);
		
		//Si si llego informacion
		if(!extras.isEmpty()){
			if(tipoMensaje.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR)){
				//Si hubo un error se ejecuta esto
				Log.i("TAG","Ocurrio un error");
			}else if(tipoMensaje.equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)){
				//Se elimino el mensaje del server
				Log.i("TAG","Mensaje eliminado en el server");
			}else if(tipoMensaje.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)){
				//Mensaje entregado normalmente
				Log.i("TAG","Mensaje entregado correctamente");
				//Aqui estan los datos que nos mandaron
				String datosQueNosEnviaron=extras.toString();
				Log.e("TAG","*******DATOS***** " + datosQueNosEnviaron);
			}
		}
		//Liberar el broadcast receiver
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

}