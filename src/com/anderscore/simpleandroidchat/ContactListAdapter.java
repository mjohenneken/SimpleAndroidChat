package com.anderscore.simpleandroidchat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * 
 * @author mjohenneken Attribut context, contacts ids R.id.tViewUser
 * 
 */
public class ContactListAdapter extends BaseAdapter {


	List<Contact> contacts = new ArrayList<Contact>();
	Context context;
	
	/**
	 * TODO context
	 */
	public ContactListAdapter(Context context) {
		this.context = context;
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
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_item_contact_list, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.tViewUser = (TextView) rowView.findViewById(R.id.tViewUser);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		holder.tViewUser.setText(contacts.get(position).getUser());

		if (contacts.get(position).isOnline()) {
			holder.tViewUser.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
		} else {
			holder.tViewUser.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_gray, 0);
		}

		return rowView;
	}
	
	/**
	 * 
	 * @param contacts - neue Daten
	 * 	erneuert den Datenbestand und lässt den ListView neu laden
	 */
	public void updateList(ArrayList<Contact> contacts) {
		this.contacts = contacts;
		notifyDataSetChanged();
	}

	private static class ViewHolder {
		public TextView tViewUser;
	}
}
