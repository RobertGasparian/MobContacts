package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.mobcontacts.R;

/**
 * Created by User on 7/26/2017.
 */

public class MenuDialogFragment extends DialogFragment {

    private String[]items={"Edit","Delete"};
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.menu_dialog_fragment,container,false);
        listView=(ListView)view.findViewById(R.id.menu_lv);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,items);
        listView.setAdapter(adapter);
        return view;
    }
}
