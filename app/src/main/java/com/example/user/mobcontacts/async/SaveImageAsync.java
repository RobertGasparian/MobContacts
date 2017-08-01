package com.example.user.mobcontacts.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.user.mobcontacts.callbacks.GetDownloadedPath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 7/28/2017.
 */

public class SaveImageAsync extends AsyncTask<String, String, String> {


    private Context context;
    private ProgressDialog pDialog;
    private URL ImageUrl;
    private String myFilePath;
    private Bitmap bmImg = null;
    private GetDownloadedPath downloadedPath;


    public SaveImageAsync(Context context, GetDownloadedPath getDownloadedPath) {
        this.context = context;
        downloadedPath = getDownloadedPath;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            ImageUrl = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) ImageUrl
                    .openConnection();
            connection.setDoInput(true);
            InputStream inputStream = connection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bmImg = BitmapFactory.decodeStream(inputStream, null, options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String path = ImageUrl.getPath();
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            File filepath = context.getExternalFilesDir(null);
            File dir = new File(filepath.getAbsolutePath() + "/MobContacts/");
            dir.mkdirs();
            String fileName = idStr;
            File file = new File(dir, fileName);
            myFilePath = file.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(file);
            bmImg.compress(Bitmap.CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return myFilePath;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (bmImg == null) {

            Toast.makeText(context, "Image still loading...",
                    Toast.LENGTH_SHORT).show();

            pDialog.dismiss();

        } else {

            if (pDialog != null) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }

            Toast.makeText(context, "Image Successfully Saved",
                    Toast.LENGTH_SHORT).show();


            downloadedPath.getDownloadedPath(s);

        }
    }
}
