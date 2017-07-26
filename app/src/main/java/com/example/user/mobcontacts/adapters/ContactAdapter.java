package com.example.user.mobcontacts.adapters;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.models.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>  {


    private List<Contact> contactList;
    private Context context;

    public ContactAdapter(List<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.contacts_rv_item,parent,false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {

        Contact contact=contactList.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
        holder.avatar.setImageResource(contact.getImage());

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder{


        TextView name;
        TextView phone;
        ImageView avatar;
        RelativeLayout itemLayout;

        public ContactHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.contact_name);
            phone=(TextView)itemView.findViewById(R.id.contact_phone);
            avatar =(ImageView)itemView.findViewById(R.id.contact_avatar);
            itemLayout=(RelativeLayout)itemView.findViewById(R.id.item_layout);
        }
    }
}
