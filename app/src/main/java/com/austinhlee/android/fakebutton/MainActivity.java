package com.austinhlee.android.fakebutton;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Context mContext;
    private Button mButton;
    private EditText mNameSpaceEditText;
    private ButtonService mService;

    public static int MAIN_ACTIVITY_RC = 2;
    public static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mButton = (Button)findViewById(R.id.search_button);
        mNameSpaceEditText = (EditText)findViewById(R.id.candidate_editText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fake-button.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(ButtonService.class);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateList();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create:
                Intent intent = new Intent(mContext, CreateUserActivity.class);
                startActivityForResult(intent, MAIN_ACTIVITY_RC);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == MAIN_ACTIVITY_RC){
            if (resultCode == RESULT_CANCELED){
                Toast.makeText(mContext, "Must fill out all fields", Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RESULT_OK){
                User user = new User(data.getStringExtra(CreateUserActivity.NAME_KEY),
                        data.getStringExtra(CreateUserActivity.EMAIL_KEY),
                        data.getStringExtra(CreateUserActivity.CANDIDATE_KEY));
                sendCreateUserRequest(user);
                updateList();
            }
        }
    }

    private void sendCreateUserRequest(final User user){
        Call<User> createUserCall = mService.createUser(user);
        createUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 409) {
                    Toast.makeText(mContext, "A user with that email already exists!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, Integer.toString(response.code()));
                } else if (response.code() == 401){
                    Toast.makeText(mContext, "Must specify a namespace!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Successfully created " + response.body().getName() + " at namespace " + response.body().getCandidate(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, response.body().getName() + ", " + response.body().getCandidate());
                    int id = (response.body().getId());
                    user.setId(id);
                    updateList();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, "Could not add user", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateList(){
        Call<List<User>> userCall = mService.listUser(mNameSpaceEditText.getText().toString());
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> examples = response.body();
                UserListAdapter userListAdapter = new UserListAdapter(mContext, examples);
                mRecyclerView.setAdapter(userListAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Could not retrieve list of users!");
                Toast.makeText(mContext, "Could not retrieve list of users!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
