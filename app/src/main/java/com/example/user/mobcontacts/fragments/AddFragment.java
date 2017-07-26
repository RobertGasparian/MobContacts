package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    private final String TAG = getClass().getSimpleName();

    private Button add_edit;
    private EditText nameEdit, phoneEdit, ageEdit;
    private Spinner genderSpinner;
    private ArrayAdapter spinnerAdapter;
    private int reqCode = -1;
    private String name, phone;
    private int age, position, gender;


    public static AddFragment newInstance(int position, String name, String phone, int age, int gender, int reqCode) {
        AddFragment addFragment = new AddFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("name", name);
        args.putString("phone", phone);
        args.putInt("age", age);
        args.putInt("reqCode", reqCode);
        args.putInt("gender", gender);
        addFragment.setArguments(args);
        return addFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, container, false);
        init(view);
        editFill();
        return view;

    }

    private void init(View view) {

        Bundle bundle = getArguments();

        if (bundle != null) {
            reqCode = bundle.getInt("reqCode");
            if (reqCode != -1) {
                name = bundle.getString("name");
                phone = bundle.getString("phone");
                position = bundle.getInt("position");
                age = bundle.getInt("age");
                gender = bundle.getInt("gender");
            }
        } else {
            Log.e(TAG, "Args are null");
        }
        add_edit = (Button) view.findViewById(R.id.add_edit_button);
        nameEdit = (EditText) view.findViewById(R.id.name_edit);
        phoneEdit = (EditText) view.findViewById(R.id.phone_edit);
        ageEdit = (EditText) view.findViewById(R.id.age_edit);
        genderSpinner = (Spinner) view.findViewById(R.id.gender_spinner);

        spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.spinner_item);
        genderSpinner.setAdapter(spinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 1:
                        Toast.makeText(getContext(), "Male", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Female", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void editFill() {

        if (reqCode != -1) {
            nameEdit.setText(name);
            phoneEdit.setText(phone);
            ageEdit.setText(String.valueOf(age));
            genderSpinner.setSelection(gender);

        }
    }
}
