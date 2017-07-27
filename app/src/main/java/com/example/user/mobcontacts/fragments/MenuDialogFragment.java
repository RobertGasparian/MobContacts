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
import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.helpers.DBHelper;

/**
 * Created by User on 7/26/2017.
 */

public class MenuDialogFragment extends DialogFragment {

    private final String TAG = getClass().getSimpleName();


    private int  ID;

    public static MenuDialogFragment newInstance(int id){
        MenuDialogFragment menuDialogFragment=new MenuDialogFragment();
        Bundle args=new Bundle();
        args.putInt(AddFragment.ID,id);
        menuDialogFragment.setArguments(args);
        return menuDialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.menu_dialog_fragment,container,false);
        ListView listView=(ListView)view.findViewById(R.id.menu_lv);
        ID=getArguments().getInt(AddFragment.ID);
        String[] items=getResources().getStringArray(R.array.menu_dialog);
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0:
                        AddFragment addFragment=AddFragment.newInstance(ID, AddFragment.EDIT_MODE);
                        FragmentManager fragmentManager=getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_fragment,addFragment).addToBackStack(TAG).commit();
                        dismiss();
                        break;
                    case 1:
                        DBHelper dbHelper=new DBHelper(getContext());
                        dbHelper.deleteContact(ID);
                        ((ContactsFragment)getTargetFragment()).updateContactList();
                        dismiss();
                        break;
                }

            }
        });

        return view;

    }


}
