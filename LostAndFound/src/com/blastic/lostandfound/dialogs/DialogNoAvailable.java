package com.blastic.lostandfound.dialogs;

import com.blastic.lostandfound.LoginScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DialogNoAvailable extends DialogFragment{

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setMessage("Como invitado, no puedes usar esta opcion. ¿Iniciar sesion?")
                .setTitle("Invitado")
                .setPositiveButton("Iniciar sesion",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {     
                            dialog.cancel();
                            Intent goToLogin=new Intent(getActivity(), LoginScreen.class);
                            goToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().startActivity(goToLogin);
                        }
                    }
                )
                .setNegativeButton("Cancelar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                           dialog.cancel();
                        }
                    }
                )
                .create();
    }
	
}
