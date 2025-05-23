# 📱 Contact App

A sleek and user-friendly **Android application** designed to help you manage your contacts efficiently. Built using **SQLite** for local data storage and **RecyclerView** for smooth, dynamic lists, this app lets you **add**, **edit**, **delete**, and **search** contacts seamlessly.


## ✨ Key Features

- **📇 Contact List Display**  
  View all your contacts in a clean, scrollable list powered by RecyclerView for optimal performance and smooth animations.

- **➕ Add New Contacts**  
  Easily add new contacts by entering their name and phone number.

- **✏️ Edit Existing Contacts**  
  Modify contact details anytime with a simple and intuitive dialog.

- **🗑️ Delete Contacts**  
  Remove unwanted contacts quickly and effortlessly.

- **🔍 Search Contacts**  
  Instantly filter contacts by name with the built-in search bar for quick access.

- **💾 Persistent Storage**  
  All contacts are saved locally using an SQLite database, ensuring your data stays safe even when offline.


## 🛠️ How It Works

1. **Startup:**  
   The app initializes by loading all saved contacts from the SQLite database.

2. **Browsing Contacts:**  
   Contacts are displayed in a RecyclerView, allowing smooth scrolling and dynamic updates.

3. **Adding a Contact:**  
   Tap the **floating action button (FAB)** to open a dialog where you can input a contact's name and phone number.

4. **Editing a Contact:**  
   Tap on any contact in the list to open an editable dialog pre-filled with the contact's current information.

5. **Deleting a Contact:**  
   Use the delete option from the contact item to remove it from the database and list instantly.

6. **Searching Contacts:**  
   Use the search bar at the top to filter contacts by name in real time as you type.


## 🔧 Technical Details

- **Language:** Java  
- **UI Components:**  
  - RecyclerView for contact list display  
  - AlertDialog for add/edit contact forms  
  - SearchView for filtering contacts  

- **Database:** SQLite  
  - CRUD operations handled via a custom `DatabaseHelper` class  
  - Contacts stored with ID, name, and phone number  

- **Architecture:**  
  - `MainActivity` manages UI and user interactions  
  - `ContactAdapter` binds contact data to RecyclerView items  
  - `Contact` model class represents individual contact data  


## 🚀 Summary

This Contact App offers a simple yet powerful way to manage personal contacts on Android devices. By combining efficient local data storage with an intuitive and responsive UI, users can maintain their contacts effortlessly. Whether adding new contacts, updating existing ones, or quickly searching through the list, this app provides a smooth and enjoyable experience.
