package com.kostas.clunter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList<AdventureListItem> rowItems;
    ListView myListView;
    String[] names;
    String[] locations;
    String[] AdId;
    String[] toughness;
    View rootView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        SharedPreferences sharedPrefs = getContext().getSharedPreferences("sp_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        if(!sharedPrefs.contains("initialized")){
            ed = sharedPrefs.edit();

            ed.putBoolean("initialized", true);

            names = getResources().getStringArray(R.array.adventure_names);
            locations = getResources().getStringArray(R.array.adventure_locations);
            AdId = getResources().getStringArray(R.array.adventure_ids);
            toughness = getResources().getStringArray(R.array.adventure_toughness);

            saveArray(names,"names", getContext());
            saveArray(locations,"locations", getContext());
            saveArray(AdId,"AdId", getContext());
            saveArray(toughness,"toughness", getContext());

            ed.commit();
        }

        rowItems = new ArrayList<>();

        names = loadArray("names", getContext());
        locations = loadArray("locations", getContext());
        AdId = loadArray("AdId", getContext());
        toughness = loadArray("toughness", getContext());

        for (int i = 0; i < names.length; i++) {
            AdventureListItem item = new AdventureListItem(names[i],locations[i], toughness[i]);
            rowItems.add(item);
        }

        myListView = (ListView) rootView.findViewById(R.id.listView_adventures);
        MyListAdapter adapter = new MyListAdapter(getContext(), rowItems);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String adventureId = AdId[position];
        Intent myIntent = new Intent(getActivity(), AdventureActivity.class);
        myIntent.putExtra("adventureInfoId", adventureId);
        startActivity(myIntent);

    }

    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("AdventureListData", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("AdventureListData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        names = loadArray("names", getContext());
        locations = loadArray("locations", getContext());
        AdId = loadArray("AdId", getContext());
        toughness = loadArray("toughness", getContext());

        rowItems.clear();

        for (int i = 0; i < names.length; i++) {
            AdventureListItem item = new AdventureListItem(names[i],locations[i], toughness[i]);
            rowItems.add(item);
        }

        MyListAdapter adapter = new MyListAdapter(getContext(), rowItems);
        myListView.setAdapter(adapter);
    }

}
