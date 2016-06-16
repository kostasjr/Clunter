package com.kostas.clunter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kostas on 2015-12-09.
 */
public class CluesAdapter extends BaseAdapter {

    Context context;
    List<String> clue_list;

    public CluesAdapter(Context context, List<String> clue_list) {
        this.context = context;
        this.clue_list = clue_list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /*@Override
    public int getItemViewType(int position) {
        return (clue_list.get(position).getContactType() == clue_list.CONTACT_WITH_IMAGE) ? 0 : 1;
    }*/


    public int isText(int position) {
        String value = clue_list.get(position);
        if (value.charAt(0) == 'd') {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getCount() { return clue_list.size(); }

    @Override
    public Object getItem(int position) {
        return clue_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clue_list.indexOf(getItem(position));
    }

    //public null setItem()

    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.list_item_adventure, null);


            if (isText(position) == 1){
                convertView = mInflater.inflate(R.layout.clue_text, null);
                TextView num = (TextView) convertView.findViewById(R.id.list_item_number);
                TextView clue = (TextView) convertView.findViewById(R.id.list_item_text_clue);

                num.setText(String.valueOf(position+1));
                clue.setText(clue_list.get(position));

            } else {
                convertView = mInflater.inflate(R.layout.clue_image, null);
                TextView num = (TextView) convertView.findViewById(R.id.list_item_number);
                ImageView pic = (ImageView) convertView.findViewById(R.id.list_item_image_clue);

                num.setText(String.valueOf(position + 1));

                context.getResources().getIdentifier(clue_list.get(position), null, context.getPackageName());
                pic.setImageResource(context.getResources().getIdentifier(clue_list.get(position), null, context.getPackageName()));
                //pic.setImageResource(clue_list.get(position));
            }
        }
        return convertView;
    }
}
