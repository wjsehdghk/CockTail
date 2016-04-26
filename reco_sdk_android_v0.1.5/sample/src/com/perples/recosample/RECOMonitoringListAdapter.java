package com.perples.recosample;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;

public class RECOMonitoringListAdapter extends BaseAdapter {
	private HashMap<RECOBeaconRegion, RECOBeaconRegionState> mMonitoredRegions;
	private HashMap<RECOBeaconRegion, String> mLastUpdateTime;
	private ArrayList<RECOBeaconRegion> mMonitoredRegionLists;
	
	private LayoutInflater mLayoutInflater;
	
	public RECOMonitoringListAdapter(Context context) {
		super();
		mMonitoredRegions = new HashMap<RECOBeaconRegion, RECOBeaconRegionState>();
		mLastUpdateTime = new HashMap<RECOBeaconRegion, String>();
		mMonitoredRegionLists = new ArrayList<RECOBeaconRegion>();
		
		mLayoutInflater = LayoutInflater.from(context);
	}
	
	public void updateRegion(RECOBeaconRegion recoRegion, RECOBeaconRegionState recoState, String updateTime) {
		mMonitoredRegions.put(recoRegion, recoState);
		mLastUpdateTime.put(recoRegion, updateTime);
		if(!mMonitoredRegionLists.contains(recoRegion)) {
			mMonitoredRegionLists.add(recoRegion);
		}
	}
	
	public void clear() {
		mMonitoredRegions.clear();
	}

	@Override
	public int getCount() {
		return mMonitoredRegions.size();
	}

	@Override
	public Object getItem(int position) {
		return mMonitoredRegions.get(mMonitoredRegionLists.get(position));
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if(convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.list_monitoring_region, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.recoRegionID = (TextView)convertView.findViewById(R.id.region_uniqueID);
			viewHolder.recoRegionState = (TextView)convertView.findViewById(R.id.region_state);
			viewHolder.recoRegionTime = (TextView)convertView.findViewById(R.id.region_update_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		RECOBeaconRegion recoRegion = mMonitoredRegionLists.get(position);
		RECOBeaconRegionState recoState = mMonitoredRegions.get(recoRegion);
		
		String recoRegionUniqueID = recoRegion.getUniqueIdentifier();
		String recoRegionState = recoState.toString();
		String recoUpdateTime = mLastUpdateTime.get(recoRegion);
		
		viewHolder.recoRegionID.setText(recoRegionUniqueID);
		viewHolder.recoRegionState.setText(recoRegionState);
		viewHolder.recoRegionTime.setText(recoUpdateTime);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView recoRegionID;
		TextView recoRegionState;
		TextView recoRegionTime;
	}
}
