package com.example.user.mobcontacts.adapters;



import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactAdapter  {


    class ContactHolder extends RecyclerView.ViewHolder{


        TextView name;
        TextView phone;
        ImageView avater;

        public ContactHolder(View itemView) {
            super(itemView);
        }
    }
}
