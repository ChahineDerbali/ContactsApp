package com.example.contactapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AlertDialog;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnContactListener {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<HashMap<String, String>> contactList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);

        contactList = dbHelper.getAllContacts();

        adapter = new ContactAdapter(contactList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAddContact).setOnClickListener(v -> {
            showContactDialog(null);
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterContacts(newText);
                return false;
            }
        });
    }

    private void filterContacts(String query) {
        List<HashMap<String, String>> filteredList = dbHelper.getContactsByName(query);

        adapter.updateList(filteredList);
    }

    @Override
    public void onModifyClick(int position) {
        HashMap<String, String> contact = contactList.get(position);

        showContactDialog(contact);
    }

    @Override
    public void onDeleteClick(int position) {
        HashMap<String, String> contact = contactList.get(position);

        dbHelper.deleteContact(new Contact(
                Integer.parseInt(contact.get("id")),
                contact.get("name"),
                contact.get("phone")
        ));

        contactList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void showContactDialog(HashMap<String, String> contact) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null);

        EditText editName = dialogView.findViewById(R.id.editName);
        EditText editPhone = dialogView.findViewById(R.id.editPhone);

        if (contact != null) {
            editName.setText(contact.get("name"));
            editPhone.setText(contact.get("phone"));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String phone = editPhone.getText().toString().trim();

                    if (name.isEmpty() || phone.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill in both fields.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (contact == null) {
                            addContact(name, phone);
                        } else {
                            modifyContact(contact, name, phone);
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void addContact(String name, String phone) {
        dbHelper.addContact(new Contact(name, phone));

        HashMap<String, String> newContact = new HashMap<>();
        newContact.put("name", name);
        newContact.put("phone", phone);
        contactList.add(newContact);
        adapter.notifyItemInserted(contactList.size() - 1);
    }

    private void modifyContact(HashMap<String, String> contact, String name, String phone) {
        dbHelper.updateContact(new Contact(
                Integer.parseInt(contact.get("id")),
                name,
                phone
        ));

        contact.put("name", name);
        contact.put("phone", phone);
        adapter.notifyDataSetChanged();
    }
}
