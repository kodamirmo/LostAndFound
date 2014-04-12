package com.blastic.lostandfound.dialogs;

import com.blastic.lostandfound.R;
import com.blastic.lostandfound.SimilarReportsScreen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DialogNoAvailable extends DialogFragment{

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

		final Dialog dialog = new Dialog(getActivity());
		dialog.setTitle("Opciones");
		dialog.setContentView(R.layout.dialog_view);

		LinearLayout optionSimilar = (LinearLayout) dialog.findViewById(R.id.optsDialogSimilary);
		LinearLayout optionIndequate = (LinearLayout) dialog.findViewById(R.id.optsDialogInadequate);
		
		optionSimilar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent reports = new Intent(getActivity(),SimilarReportsScreen.class);
				startActivity(reports);
				dialog.dismiss();
			}
		});

		optionIndequate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(),
								"¡Gracias por tu aporte! El reporte ya fue enviado a revisión",Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		
		return dialog;	
    }
	
}
