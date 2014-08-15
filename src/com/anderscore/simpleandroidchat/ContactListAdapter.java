package com.anderscore.simpleandroidchat;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {

	Context context;
	List<Contact> contacts = new ArrayList<Contact>();
	
	static class ViewHolder {
		TextView tViewUser;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Contact getItem(int position) {

		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if(rowView==null){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.row_item_contact_list, parent, false);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.tViewUser = (TextView) rowView.findViewById(R.id.tViewUser);
		rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		//Data
		holder.tViewUser.setText(contacts.get(position).getName());
		
		if (contacts.get(position).isOnline()) {
			holder.tViewUser.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.circle_green), null);
		}
		else {
			holder.tViewUser.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.circle_gray), null);			
		}

		return rowView;
	}

}
