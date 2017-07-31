package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mobcontacts.R;

/**
 * Created by User on 7/31/2017.
 */

public class DiscDialogFragment extends DialogFragment {


    private EditText discEdit;
    private Button ok;

    public static DiscDialogFragment newInstance() {

        Bundle args = new Bundle();

        DiscDialogFragment fragment = new DiscDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.disc_dialog_fragment, container, false);

        getDialog().setCanceledOnTouchOutside(false);
        discEdit = (EditText) view.findViewById(R.id.disc_edit);
        ok = (Button) view.findViewById(R.id.disc_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!discEdit.getText().toString().isEmpty()) {
                    ((AddEditFragment) getTargetFragment()).setDiscription(discEdit.getText().toString());
                    dismiss();
                }else{
                    Toast.makeText(getContext(),R.string.enter_discription,Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
