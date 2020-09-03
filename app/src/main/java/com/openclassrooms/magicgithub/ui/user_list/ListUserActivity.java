package com.openclassrooms.magicgithub.ui.user_list;

import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.magicgithub.base.BaseActivity;
import com.openclassrooms.magicgithub.R;
import com.openclassrooms.magicgithub.model.User;

public class ListUserActivity extends BaseActivity implements UserListAdapter.Listener {

    // FOR DESIGN ---
    RecyclerView recyclerView;
    FloatingActionButton fab;

    // FOR DATA ---
    private UserListAdapter adapter;

    // OVERRIDE ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        configureFab();
        configureRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    // CONFIGURATION ---
    private void configureRecyclerView() {
        recyclerView = findViewById(R.id.activity_list_user_rv);
        adapter = new UserListAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void configureFab() {
        fab = findViewById(R.id.activity_list_user_fab);
        fab.setOnClickListener(view -> {

            // Save previous list size before attempting to add a new User
            int oldSize = getUserRepository().getUsers().size();

            getUserRepository().generateRandomUser();

            if(getUserRepository().getUsers().size() > oldSize){
                // If a new user is successfully added, update recyclerview
                loadData();
            }
            else {
                // Otherwise warn user with a Toast
                Toast.makeText(this, getResources().getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        adapter.updateList(getUserRepository().getUsers());
    }

    // ACTIONS ---
    @Override
    public void onClickDelete(User user) {
        Log.d(ListUserActivity.class.getName(), "User tries to delete a item.");
        getUserRepository().deleteUser(user);
        loadData();
    }

}
