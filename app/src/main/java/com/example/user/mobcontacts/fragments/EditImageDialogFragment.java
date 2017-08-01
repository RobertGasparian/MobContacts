package com.example.user.mobcontacts.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.async.SaveImageAsync;
import com.example.user.mobcontacts.callbacks.GetDownloadedPath;
import com.example.user.mobcontacts.helpers.DBHelper;
import com.example.user.mobcontacts.models.ContactImage;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 8/1/2017.
 */

public class EditImageDialogFragment extends DialogFragment implements View.OnClickListener, GetDownloadedPath {


    private EditText disc_edit;
    private Spinner urlSpinner;
    private ImageButton camera, gallery, url;
    private final static String IMAGE_ID = "image_id";
    public final static int CAMERA_ACCESS = 7;
    public final static int CAMERA_RESULT = 8;
    public final static int GALLERY_RESULT = 9;
    private int imageId, contact_id;
    private String disc, path;
    private DBHelper dbHelper;


    public static EditImageDialogFragment newInstance(int image_id) {

        Bundle args = new Bundle();

        args.putInt(IMAGE_ID, image_id);
        EditImageDialogFragment fragment = new EditImageDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_image_dialog_fragment, container, false);

        imageId = getArguments().getInt(IMAGE_ID);
        dbHelper = new DBHelper(getContext());
        ContactImage image = dbHelper.getImage(imageId);
        path = image.getPath();
        contact_id = image.getContact_id();
        disc = image.getDiscription();
        disc_edit = (EditText) view.findViewById(R.id.edit_disc_dialog);
        urlSpinner = (Spinner) view.findViewById(R.id.edit_url_spinner);
        camera = (ImageButton) view.findViewById(R.id.edit_camera_button);
        gallery = (ImageButton) view.findViewById(R.id.edit_gallery_button);
        url = (ImageButton) view.findViewById(R.id.edit_url_button);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        url.setOnClickListener(this);
        disc_edit.setText(disc);


        urlSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (urlSpinner.getSelectedItemPosition() != 0) {
                    path = urlSpinner.getSelectedItem().toString();
                    SaveImageAsync saveImageAsync = new SaveImageAsync(getContext(), EditImageDialogFragment.this);
                    saveImageAsync.execute(path);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

        if (!disc_edit.getText().toString().isEmpty()) {
            switch (v.getId()) {

                case R.id.edit_camera_button:

                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_ACCESS);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_RESULT);
                        }
                    }
                    break;
                case R.id.edit_gallery_button:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_RESULT);

                    break;
                case R.id.edit_url_button:
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.url_array, R.layout.spinner_item);
                    urlSpinner.setAdapter(adapter);
                    urlSpinner.setVisibility(View.VISIBLE);
                    getDialog().setCanceledOnTouchOutside(false);
                    break;
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.empty_disc), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case CAMERA_RESULT: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_RESULT);

                } else {
                    Toast.makeText(getContext(), R.string.camera_permission_deny, Toast.LENGTH_SHORT).show();
                }

                return;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {


        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getContext(), photo);
            path = getRealPathFromURI(tempUri);
            disc = disc_edit.getText().toString();
            dbHelper.updateContactImage(new ContactImage(contact_id, imageId, path, disc));
            ((PagerItemFragment) getTargetFragment()).updateDetailedFragment();
            dismiss();


        } else if (requestCode == GALLERY_RESULT && resultCode == RESULT_OK) {

            Uri pickedImage = data.getData();
            path = getRealPathFromURI(pickedImage);
            disc = disc_edit.getText().toString();
            dbHelper.updateContactImage(new ContactImage(contact_id, imageId, path, disc));
            ((PagerItemFragment) getTargetFragment()).updateDetailedFragment();
            dismiss();


        }
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String realPath = cursor.getString(idx);
        cursor.close();
        return realPath;
    }


    @Override
    public void getDownloadedPath(String path) {
        disc = disc_edit.getText().toString();
        dbHelper.updateContactImage(new ContactImage(contact_id, imageId, path, disc));
        ((PagerItemFragment) getTargetFragment()).updateDetailedFragment();
        dismiss();
    }
}
