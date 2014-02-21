package com.blastic.lostandfound.adapters;

import com.blastic.lostandfound.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserTypeAdapter extends BaseAdapter {

	private String[] userSex = { "Mujer", "Hombre" };
	private LayoutInflater inflater;
	
	public UserTypeAdapter(Context context) {
		inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return userSex.length;
	}

	@Override
	public Object getItem(int position) {
		return userSex[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = inflater.inflate(R.layout.spinner_register, parent,false);
		TextView label = (TextView) row.findViewById(R.id.textViewSpinnerRegisterAdptr);
		label.setText(userSex[position]);
		return row;
	}	
}
