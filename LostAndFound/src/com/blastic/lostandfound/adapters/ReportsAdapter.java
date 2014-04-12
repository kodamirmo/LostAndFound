package com.blastic.lostandfound.adapters;

import java.util.ArrayList;

import com.blastic.lostandfound.transferobjects.Report;
import com.blastic.lostandfound.views.ReportView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ReportsAdapter extends BaseAdapter{

	private ArrayList<Report> list;
	private ReportView reporView;

	public ReportsAdapter(Context context,ArrayList<Report> list){
		this.list=list;
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		reporView=new ReportView(inflater);
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
		
		if(convertView!=null)
			return convertView;
		else
			return reporView.getReportChart(list.get(position));
	}

}
