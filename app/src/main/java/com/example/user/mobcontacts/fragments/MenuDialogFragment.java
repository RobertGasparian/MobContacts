package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.mobcontacts.R;

/**
 * Created by User on 7/26/2017.
 */

public class MenuDialogFragment extends DialogFragment {

    private String[]items={"Edit","Delete"};
    private ListView listView;
    private int reqCode=1;
    private String name, phone;
    private int age, gender, position;



    public static MenuDialogFragment newInstance(int position, String name, String phone, int age, int gender){
        MenuDialogFragment menuDialogFragment=new MenuDialogFragment();
        Bundle args=new Bundle();
        args.putInt("position",position);
        args.putString("name",name);
        args.putString("phone",phone);
        args.putInt("age",age);
        args.putInt("gender",gender);
        menuDialogFragment.setArguments(args);
        return menuDialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.menu_dialog_fragment,container,false);
        listView=(ListView)view.findViewById(R.id.menu_lv);
        reqCode =getArguments().getInt("reqCode");
        name=getArguments().getString("name");
        phone=getArguments().getString("phone");
        age=getArguments().getInt("age");
        gender=getArguments().getInt("gender");
        position=getArguments().getInt("position");
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        AddFragment addFragment=AddFragment.newInstance(MenuDialogFragment.this.position,name,phone,age,gender,reqCode);
                        FragmentManager fragmentManager=getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_fragment,addFragment).addToBackStack("").commit();
                        dismiss();
                        break;
                    case 1:
                        Toast.makeText(getContext(),"delete",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return view;
    }
}
