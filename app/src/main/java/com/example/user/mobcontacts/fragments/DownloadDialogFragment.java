package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.async.SaveImageAsync;
import com.example.user.mobcontacts.callbacks.GetDownloadedPath;

/**
 * Created by User on 7/28/2017.
 */

public class DownloadDialogFragment extends DialogFragment
        implements View.OnClickListener, GetDownloadedPath {


    private Spinner spinner;
    private EditText url_disc;
    private Button ok, cancel;


    public static DownloadDialogFragment newInstance() {

        Bundle args = new Bundle();

        DownloadDialogFragment fragment = new DownloadDialogFragment();

        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_dialog_fragment, container, false);

        spinner = (Spinner) view.findViewById(R.id.url_spinner);
        url_disc = (EditText) view.findViewById(R.id.url_disc);
        ok = (Button) view.findViewById(R.id.ok_button);
        cancel = (Button) view.findViewById(R.id.cancel_button);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.url_array, R.layout.spinner_item);
        spinner.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.ok_button:
                if (!url_disc.getText().toString().isEmpty()) {
                    if (spinner.getSelectedItemPosition() != 0) {
                        String url = spinner.getSelectedItem().toString();
                        SaveImageAsync saveImageAsync = new SaveImageAsync(getContext(), this);
                        saveImageAsync.execute(url);
                    }
                } else {
                    Toast.makeText(getContext(), R.string.enter_discription, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel_button:
                dismiss();
                break;
        }
    }

    @Override
    public void getDownloadedPath(String path) {

        ((AddEditFragment) getTargetFragment()).setURLtoPath(path, url_disc.getText().toString());
        dismiss();

    }

}
