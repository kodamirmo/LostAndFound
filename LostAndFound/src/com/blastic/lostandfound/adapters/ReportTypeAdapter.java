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

public class ReportTypeAdapter extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<ReportType> list;

	public ReportTypeAdapter(Context context,ArrayList<ReportType> list){
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
			row = inflater.inflate(R.layout.spinner_report_types, parent, false);
		
			ReportType reportType = list.get(position);
			
			TextView label = (TextView) row.findViewById(R.id.textSpinnerAdptr);
			label.setText(reportType.getName());

			ImageView icon = (ImageView) row.findViewById(R.id.imgSpinnerAdptr);
			icon.setImageResource(reportType.getImage());
		
		return row;
	}
	
	public static class ReportType{
		private int image;
		private String name;
		
		public ReportType(int image, String name) {
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
	
	public static ArrayList<ReportType> getReportTypes(){
		ArrayList<ReportType> listTypes=new ArrayList<ReportTypeAdapter.ReportType>();
		listTypes.add(new ReportType(R.drawable.missing_icon_blue,"Extraviado"));
		listTypes.add(new ReportType(R.drawable.found_icon_blue,"Encontrado"));
		listTypes.add(new ReportType(R.drawable.abuse_icon_blue,"Maltrato"));
		listTypes.add(new ReportType(R.drawable.home_icon_blue,"Busca Hogar"));
		listTypes.add(new ReportType(R.drawable.acci_icon_blue,"Accidente"));
		
		return listTypes;
	}

}
