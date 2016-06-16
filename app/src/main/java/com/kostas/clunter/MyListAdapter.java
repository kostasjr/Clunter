package com.kostas.clunter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostas on 2015-12-01.
 */
public class MyListAdapter extends BaseAdapter {

    Context context;
    List<AdventureListItem> listItems;


    public MyListAdapter(Context context, ArrayList<AdventureListItem> listItems) {

        this.context = context;
        this.listItems = listItems;
    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView toughness;
        TextView name;
        TextView location;
        //TextView length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item_adventure, null);

            holder = new ViewHolder();

            //holder.pic = (ImageView) convertView.findViewById(R.id.list_item_icon);
            holder.name = (TextView) convertView.findViewById(R.id.list_item_name);
            holder.location = (TextView) convertView.findViewById(R.id.list_item_location);
            holder.toughness = (ImageView) convertView.findViewById(R.id.toughness_circle);
            //holder.length = (TextView) convertView.findViewById(R.id.list_item_length);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AdventureListItem item_pos = listItems.get(position);

        //holder.pic.setImageResource(item_pos.getAdventure_pic_id());
        holder.name.setText(item_pos.getAdventure_name());

        String t = item_pos.getAdventure_toughness();
        //String tt = "g";
        if(t.equals("g")){
            holder.toughness.setImageResource(R.drawable.circle_g);
        } else if (t.equals("o")){
            holder.toughness.setImageResource(R.drawable.circle_o);
        } else if (t.equals("h")){
            holder.toughness.setImageResource(R.drawable.circle_r);
        } else if(t.equals("gd")){
            holder.toughness.setImageResource(R.drawable.circle_g_done);
        } else if (t.equals("od")){
            holder.toughness.setImageResource(R.drawable.circle_o_done);
        } else {
            holder.toughness.setImageResource(R.drawable.circle_r_done);
        }
        //holder.length.setText(item_pos.getAdventure_length());
        holder.location.setText(item_pos.getAdventure_location());
        return convertView;
    }
}
