package com.example.contactapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<HashMap<String, String>> contactList;
    private OnContactListener onContactListener;

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView modifyButton, deleteButton;
        public ConstraintLayout contactLayout;

        public ContactViewHolder(View view, final OnContactListener onContactListener) {
            super(view);
            name = view.findViewById(R.id.contactName);
            phone = view.findViewById(R.id.contactPhone);
            modifyButton = view.findViewById(R.id.modifyButton);
            deleteButton = view.findViewById(R.id.deleteButton);
            contactLayout = view.findViewById(R.id.contactLayout);
            
            modifyButton.setOnClickListener(v -> {
                if (onContactListener != null) {
                    onContactListener.onModifyClick(getAdapterPosition());
                }
            });

            deleteButton.setOnClickListener(v -> {
                if (onContactListener != null) {
                    onContactListener.onDeleteClick(getAdapterPosition());
                }
            });

            contactLayout.setOnClickListener(v -> {
                contactLayout.setSelected(!contactLayout.isSelected());
            });
        }
    }

    public ContactAdapter(List<HashMap<String, String>> contactList, OnContactListener onContactListener) {
        this.contactList = contactList;
        this.onContactListener = onContactListener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new ContactViewHolder(view, onContactListener);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        HashMap<String, String> contact = contactList.get(position);
        holder.name.setText(contact.get("name"));
        holder.phone.setText(contact.get("phone"));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void updateList(List<HashMap<String, String>> newContactList) {
        contactList = newContactList;
        notifyDataSetChanged();
    }

    public interface OnContactListener {
        void onModifyClick(int position);
        void onDeleteClick(int position);
    }
}

