package com.blastic.lostandfound;

import java.util.ArrayList;

import com.blastic.lostandfound.R;
import com.blastic.lostandfound.adapters.ReportsAdapter;
import com.blastic.lostandfound.data.AppCache;
import com.blastic.lostandfound.transferobjects.Report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CasesListFragment extends Fragment{
	
	private ListView listView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cases_list_layout, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        initViews();
    }
	
	private void initViews(){
		//Bundle arguments=getArguments();
        //int screenType=arguments.getInt("TYPE");
     
        listView=(ListView)getView().findViewById(R.id.listViewReports);
        
        ArrayList<Report> listReports=AppCache.getAllReports();
        
        ReportsAdapter adapter=new ReportsAdapter(getActivity(), listReports);
        
        listView.setAdapter(adapter);
	}

}
