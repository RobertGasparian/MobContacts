package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.mobcontacts.R;

/**
 * Created by User on 7/26/2017.
 */

public class AddFragment extends Fragment {



    private Button add_edit;
    private EditText nameEdit, phoneEdit, ageEdit;
    private Spinner genderSpinner;
    private ArrayAdapter spinnerAdapter;


    public static AddFragment newInstance(int position, String name, String phone, int reqCode){
        AddFragment addFragment=new AddFragment();
        Bundle args=new Bundle();
        if(position!=-1){

        }
        return addFragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, container, false);
        init(view);

        return view;

    }

    private void init(View view){
        add_edit=(Button)view.findViewById(R.id.add_edit_button);
        nameEdit=(EditText)view.findViewById(R.id.name_edit);
        phoneEdit=(EditText)view.findViewById(R.id.phone_edit);
        ageEdit=(EditText)view.findViewById(R.id.age_edit);
        genderSpinner=(Spinner)view.findViewById(R.id.gender_spinner);
        spinnerAdapter=ArrayAdapter.createFromResource(getContext(),R.array.gender,R.layout.spinner_item);
        genderSpinner.setAdapter(spinnerAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Toast.makeText(getContext(),"Please select gender",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(),"Male",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(),"Female",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }
}
