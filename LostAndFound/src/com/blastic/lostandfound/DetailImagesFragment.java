package com.blastic.lostandfound;

import com.blastic.lostandfound.dialogs.DialogDetailsOption;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class DetailImagesFragment extends Fragment implements OnClickListener{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_detail_1, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        initViews();
    }
	
	private void initViews(){
	
	}

	@Override
	public void onClick(View v) {
		DialogDetailsOption dialog=new DialogDetailsOption();
		dialog.show(getChildFragmentManager(), "DIALOG");
	}

	
}
