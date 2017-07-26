package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.adapters.ContactAdapter;
import com.example.user.mobcontacts.models.Contact;
import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private ContactAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.contacts_fragment,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.contacts_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactList=new ArrayList<>();
        contactList=getData();
        adapter=new ContactAdapter(contactList,getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }


    private List<Contact> getData(){

        List<Contact> contacts=new ArrayList<>();
        for (int i = 0; i <10; i++) {

            Contact contact=new Contact("Anun", "+37465478515",R.drawable.avatar);
            contacts.add(contact);

        }

        return contacts;

    }
}
