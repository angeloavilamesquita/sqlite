package br.edu.unis.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class ListUsersActivity extends AppCompatActivity {

    private SQLiteHelper db;
    private ListUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        db = SQLiteHelper.getInstance(this);
        List<User> users = db.all();

        RecyclerView rv = findViewById(R.id.list_users_rv_users);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ListUserAdapter.RecyclerViewClickListener listener = (view, position) -> {
            Intent intent = new Intent(getApplicationContext(), CreateUserActivity.class);
            User userSelected = users.get(position);
            intent.putExtra("USER_SELECTED", userSelected);
            startActivity(intent);
        };
        adapter = new ListUserAdapter(users, listener);
        rv.setAdapter(adapter);
    }

    private static class ListUserAdapter extends RecyclerView.Adapter<ListUserViewHolder> {
        private final List<User> users;
        private RecyclerViewClickListener listener;

        public ListUserAdapter(List<User> users, RecyclerViewClickListener listener) {
            this.users = users;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ListUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
            return new ListUserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListUserViewHolder holder, int position) {
            User user = this.users.get(position);
            holder.bind(user, listener);
        }

        @Override
        public int getItemCount() {
            return this.users.size();
        }

        public interface RecyclerViewClickListener {
            public void onClick(View view, int position);
        }
    }

    private static class ListUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtUserId;
        TextView txtUser;
        ListUserAdapter.RecyclerViewClickListener listener;

        public ListUserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtUserId = itemView.findViewById(R.id.item_user_id);
            this.txtUser = itemView.findViewById(R.id.item_user_user);
            itemView.setOnClickListener(this);
        }

        public void bind(User user, ListUserAdapter.RecyclerViewClickListener listener) {
            this.txtUserId.setText(Long.toString(user.getId()));
            this.txtUser.setText(user.getUser());
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            this.listener.onClick(view, getAdapterPosition());
        }
    }
}