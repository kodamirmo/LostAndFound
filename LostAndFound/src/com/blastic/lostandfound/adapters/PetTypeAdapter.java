package com.blastic.lostandfound.adapters;

import java.util.ArrayList;

import com.blastic.lostandfound.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PetTypeAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<PetType> list;
	
	public PetTypeAdapter(Context context,ArrayList<PetType> list){
		this.context=context;
		this.list=list;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View row=convertView;
		
		if(row==null)
			row= inflater.inflate(R.layout.spinner_report_pet_types, parent, false);
		
		PetType petType=list.get(position);
		
		TextView label = (TextView) row.findViewById(R.id.textPetTypeSpinnerAdptr);
		label.setText(petType.getName());

		ImageView icon = (ImageView) row.findViewById(R.id.imgPetTypeSpinnerAdptr);
		icon.setImageResource(petType.getImage());

		return row;
	}
	
	public static class PetType{
		
		private int image;
		private String name;
		
		public PetType(int image, String name) {
			this.image = image;
			this.name = name;
		}

		public int getImage() {
			return image;
		}

		public String getName() {
			return name;
		}
	}	
	
	public static ArrayList<PetType> getPetTypes(){
		ArrayList<PetType> listTypes=new ArrayList<PetTypeAdapter.PetType>();
		listTypes.add(new PetType(R.drawable.dog_icon,"Perro"));
		listTypes.add(new PetType(R.drawable.cat_icon,"Gato"));
		listTypes.add(new PetType(R.drawable.doc_icon,"Otro"));
		return listTypes;
	}

}
