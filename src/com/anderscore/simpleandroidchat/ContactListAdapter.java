package com.anderscore.simpleandroidchat;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * 
 * @author mjohenneken
 * Attribut context, contacts
 *	ids R.id.tViewUser
 *
 */
public class ContactListAdapter extends BaseAdapter {
	
	ArrayList<Contact> contacts = new ArrayList<Contact>();
	Context context;
	
	/**
	 * TODO context
	 */
	public ContactListAdapter() {
		
	}
	
	@Override
	public int getCount() {		
		return  contacts.size();
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
		View rowView;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.row_item_contact_list, parent, false);
		
		TextView tViewUser = (TextView) rowView.findViewById(R.id.tViewUser);
		
		tViewUser.setText(getItem(position).getUser());
		
		//TODO OnlineStatus
		
		
		return rowView;
	}

	
	/**
	 * 
	 * @param values - neue Daten
	 * 	erneuert den Datenbestand und lässt den ListView neu laden
	 */
	public void updateList(ArrayList<Contact> contacts) {
		//TODO update values
	}


	private static class ViewHolder {
		public TextView tViewUser;
	}
	

}
