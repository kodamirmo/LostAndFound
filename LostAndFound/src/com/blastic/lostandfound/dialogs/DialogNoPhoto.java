package com.blastic.lostandfound.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.blastic.lostandfound.RegisterActivity;

public class DialogNoPhoto extends DialogFragment{
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setMessage("Te vas a registrar sin foto ¿Continuar?")
                .setTitle("Registro")
                .setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {     
                            dialog.cancel();
                            ((RegisterActivity)getActivity()).doRegister();
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
