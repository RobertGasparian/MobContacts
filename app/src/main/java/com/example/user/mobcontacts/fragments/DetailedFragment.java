package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mobcontacts.R;

/**
 * Created by User on 7/31/2017.
 */

public class DetailedFragment extends Fragment {


    public static DetailedFragment newInstance() {

        Bundle args = new Bundle();

        DetailedFragment fragment = new DetailedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailed_fragment,container,false);



        return view;
    }
}
