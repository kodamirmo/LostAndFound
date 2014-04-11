package com.blastic.lostandfound.dialogs;

import com.blastic.lostandfound.LoginScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DialogConfirmGuest extends DialogFragment{
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setMessage("Como invitado, no podras disfrutar de todas las caracteristicas. ¿Continuar?")
                .setTitle("Invitado")
                .setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {     
                            dialog.cancel();
                            ((LoginScreen)getActivity()).goToHome();
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
