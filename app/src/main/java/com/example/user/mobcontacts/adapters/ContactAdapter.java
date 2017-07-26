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
import com.example.user.mobcontacts.callbacks.OpenDialogCallback;
import com.example.user.mobcontacts.models.Contact;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>  {


    private List<Contact> contactList;
    private Context context;
    private OpenDialogCallback dialogCallback;

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
    public void onBindViewHolder(ContactHolder holder, final int position) {

        final Contact contact=contactList.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
        holder.age.setText(String.valueOf(contact.getAge()));

        if(contact.getImage()==1){
            holder.avatar.setImageResource(R.drawable.male);
        }
        else{
            holder.avatar.setImageResource(R.drawable.female);
        }
        holder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialogCallback.openDialog(position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder{


        TextView name;
        TextView phone;
        TextView age;
        ImageView avatar;
        RelativeLayout itemLayout;

        public ContactHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.contact_name);
            phone=(TextView)itemView.findViewById(R.id.contact_phone);
            avatar =(ImageView)itemView.findViewById(R.id.contact_avatar);
            age=(TextView)itemView.findViewById(R.id.contact_age);
            itemLayout=(RelativeLayout)itemView.findViewById(R.id.item_layout);
        }
    }

    public void setDialogCallback(OpenDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }
}
