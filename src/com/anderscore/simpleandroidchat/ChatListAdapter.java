package com.anderscore.simpleandroidchat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {

	private Context context;
	private List<ChatMsg> messages = new ArrayList<ChatMsg>();
	
	static class ViewHolder {
		TextView tViewUser;
		TextView tViewMessage;
	}
	
	public ChatListAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public ChatMsg getItem(int position) {
		return messages.get(position);
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
			rowView = inflater.inflate(R.layout.row_item_chat_list,parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.tViewMessage = (TextView) rowView.findViewById(R.id.tViewMessage);
			viewHolder.tViewUser = (TextView) rowView.findViewById(R.id.tViewUser);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		ChatMsg msg = getItem(position);
		holder.tViewMessage.setText(msg.getMsg());
		holder.tViewUser.setText(msg.getContact().getUser());
		return rowView;
	}
	
	public void updateList(ChatMsg msg) {
		messages.add(msg);
		notifyDataSetChanged();
	}
	
	public void updateList(ArrayList<ChatMsg> messages) {
		this.messages = messages;
		notifyDataSetChanged();
	}
	
}
