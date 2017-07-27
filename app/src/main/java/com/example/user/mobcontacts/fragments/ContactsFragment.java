package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.adapters.ContactAdapter;
import com.example.user.mobcontacts.callbacks.OpenDialogCallback;
import com.example.user.mobcontacts.helpers.DBHelper;
import com.example.user.mobcontacts.models.Contact;
import java.util.List;


public class ContactsFragment extends Fragment implements OpenDialogCallback {

    private final String TAG = getClass().getSimpleName();

    private ContactAdapter adapter;
    private final static int REQUEST_CODE = 2;


    public static ContactsFragment newInstance() {

        Bundle args = new Bundle();

        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contacts_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.contacts_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Contact> contactList = getData();
        adapter = new ContactAdapter(contactList, getContext());
        adapter.setDialogCallback(this);
        recyclerView.setAdapter(adapter);
        return view;

    }


    private List<Contact> getData() {

        DBHelper dbHelper = new DBHelper(getContext());
        List<Contact> contacts = dbHelper.getAllContacts();
        return contacts;

    }

    @Override
    public void openDialog(int id) {

        FragmentManager fragmentManager = getFragmentManager();
        MenuDialogFragment dialogFragment = MenuDialogFragment.newInstance(id);
        dialogFragment.setTargetFragment(this, REQUEST_CODE);
        dialogFragment.show(fragmentManager,TAG);

    }


    public void updateContactList() {

        adapter.updateList(getData());

    }
}
