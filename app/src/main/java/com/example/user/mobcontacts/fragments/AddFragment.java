package com.example.user.mobcontacts.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.helpers.DBHelper;
import com.example.user.mobcontacts.utilities.ValidationUtility;
import com.example.user.mobcontacts.models.Contact;

/**
 * Created by User on 7/26/2017.
 */

public class AddFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private Button add_edit;
    private EditText nameEdit, phoneEdit, ageEdit;
    private Spinner genderSpinner;
    private ArrayAdapter spinnerAdapter;
    public final static int ADD_MODE = 0;
    public final static int EDIT_MODE = 1;
    public final static int WRITE_ACCESS = 2;
    private int DEFAULT_MODE = ADD_MODE;
    private int id;
    public final static String ID = "id", MODE = "mode";
    private String name, phone;
    private int age, gender;


    public static AddFragment newInstance(int id, int mode) {
        AddFragment addFragment = new AddFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        args.putInt(MODE, mode);
        addFragment.setArguments(args);
        return addFragment;
    }

    public static AddFragment newInstance(int mode) {
        AddFragment addFragment = new AddFragment();
        Bundle args = new Bundle();
        args.putInt(MODE, mode);
        addFragment.setArguments(args);
        return addFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, container, false);
        init(view);
        editFill();

        add_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!ageEdit.getText().toString().isEmpty()) {

                    if (ValidationUtility.checkValidation(nameEdit.getText().toString(),
                            phoneEdit.getText().toString(),
                            genderSpinner.getSelectedItemPosition())) {

                        DBHelper dbHelper = new DBHelper(getContext());


                        if (DEFAULT_MODE == 0) {
                            dbHelper.addContact(new Contact(nameEdit.getText().toString(),
                                    phoneEdit.getText().toString(),
                                    Integer.parseInt(ageEdit.getText().toString()),
                                    genderSpinner.getSelectedItemPosition()));

                        } else {

                            Contact contact = new Contact(nameEdit.getText().toString(),
                                    phoneEdit.getText().toString(),
                                    Integer.parseInt(ageEdit.getText().toString()),
                                    genderSpinner.getSelectedItemPosition());
                            contact.setId(id);
                            dbHelper.updateContact(contact);

                        }

                        FragmentManager fragmentManager = getFragmentManager();
                        ContactsFragment contactsFragment = new ContactsFragment();
                        fragmentManager.beginTransaction().replace(R.id.main_fragment, contactsFragment).commit();


                    } else {
                        Toast.makeText(getContext(), R.string.field_empty_toast, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.field_empty_toast, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    private void init(View view) {

        add_edit = (Button) view.findViewById(R.id.add_edit_button);
        Bundle bundle = getArguments();

        if (bundle != null) {

            DEFAULT_MODE = bundle.getInt(MODE);
            id = bundle.getInt(ID);

            if (DEFAULT_MODE != 0) {

                add_edit.setText(R.string.Edit);
                DBHelper dbHelper = new DBHelper(getContext());
                Contact contact = dbHelper.getContact(id);
                name = contact.getName();
                phone = contact.getPhone();
                age = contact.getAge();
                gender = contact.getGender();

            }
        } else {

            Log.e(TAG, "Args are null");

        }

        nameEdit = (EditText) view.findViewById(R.id.name_edit);
        phoneEdit = (EditText) view.findViewById(R.id.phone_edit);
        ageEdit = (EditText) view.findViewById(R.id.age_edit);
        genderSpinner = (Spinner) view.findViewById(R.id.gender_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.spinner_item);
        genderSpinner.setAdapter(spinnerAdapter);


    }

    private void editFill() {

        if (DEFAULT_MODE != 0) {

            nameEdit.setText(name);
            phoneEdit.setText(phone);
            ageEdit.setText(String.valueOf(age));
            genderSpinner.setSelection(gender);

        }
    }


}

