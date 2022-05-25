package br.edu.unis.sqlite;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loadWidgets();
    }

    public void loadWidgets() {
        FloatingActionButton fab = findViewById(R.id.dashboard_fab_new_user);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(DashboardActivity.this, CreateUserActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard_menu_logout:
                finish();
                break;
            case R.id.dashboard_list_users:
                startActivity(new Intent(this, ListUsersActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}