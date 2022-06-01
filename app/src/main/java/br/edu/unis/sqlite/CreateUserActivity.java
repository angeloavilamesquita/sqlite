package br.edu.unis.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnSave;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        loadWidgets();
        hasUpdateAction();
        btnSaveOnClick();
    }

    private void hasUpdateAction() {
        if (getIntent().hasExtra("USER_SELECTED")) {
            this.user = (User) getIntent().getExtras().get("USER_SELECTED");
            populate();
        }
    }

    private void populate() {
        this.edtUser.setText(user.getUser());
        this.edtPassword.setText(user.getPassword());
        this.edtConfirmPassword.setText(user.getPassword());
    }

    private void loadWidgets() {
        this.edtUser = findViewById(R.id.create_user_edt_user);
        this.edtPassword = findViewById(R.id.create_user_edt_password);
        this.edtConfirmPassword = findViewById(R.id.create_user_edt_confirm_password);
        this.btnSave = findViewById(R.id.create_user_btn_save);
    }

    private void btnSaveOnClick() {
        this.btnSave.setOnClickListener(view -> {
            try {
                saveUser();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveUser() {
        hideKeyboard();
        if (isFieldsEmpty()) {
            Toast.makeText(this, R.string.txt_field_required, Toast.LENGTH_SHORT).show();
            this.edtUser.requestFocus();
            return;
        }
        if (!arePasswordTheSame()) {
            Toast.makeText(this, R.string.txt_create_user_password_not_the_same, Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(() -> {
            SQLiteHelper db = SQLiteHelper.getInstance(this);
            long id = 0;
            if (isUpdateAction()) {
                id = db.updateUser(this.user.getId(), edtUser.getText().toString(), edtPassword.getText().toString());
            } else {
                id = db.createUser(edtUser.getText().toString(), edtPassword.getText().toString());
            }
            if (id > 0 ) {
                runOnUiThread(() -> Toast.makeText(this, R.string.txt_create_user_created_successful, Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() ->Toast.makeText(this, R.string.txt_create_user_not_created, Toast.LENGTH_SHORT).show());
            }
        }).start();
        finish();
    }

    private boolean isUpdateAction() {
        return this.user != null;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtUser.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(edtConfirmPassword.getWindowToken(), 0);
    }

    private boolean isFieldsEmpty() {
        return this.edtUser.getText().toString().isEmpty()
            || this.edtPassword.getText().toString().isEmpty()
            || this.edtConfirmPassword.getText().toString().isEmpty();
    }

    private boolean arePasswordTheSame() {
        return this.edtPassword.getText().toString().equals(this.edtConfirmPassword.getText().toString());
    }
}