package com.austinhlee.android.fakebutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateUserActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mCandidateEditText;
    private Button mSubmitButton;

    public static String NAME_KEY = "com.austinhlee.android.fakebutton.NAME_KEY";
    public static String EMAIL_KEY = "com.austinhlee.android.fakebutton.EMAIL_KEY";
    public static String CANDIDATE_KEY = "com.austinhlee.android.fakebutton.CANDIDATE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mNameEditText = (EditText)findViewById(R.id.name_editText);
        mEmailEditText = (EditText)findViewById(R.id.email_editText);
        mCandidateEditText = (EditText)findViewById(R.id.candidate_editText);

        mSubmitButton = (Button)findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mCandidateEditText.getText()) || TextUtils.isEmpty(mNameEditText.getText()) || TextUtils.isEmpty(mEmailEditText.getText())){
                    setResult(RESULT_CANCELED);
                    finish();
                }
                else {
                    Intent replyIntent = new Intent();
                    replyIntent.putExtra(NAME_KEY, mNameEditText.getText().toString());
                    replyIntent.putExtra(EMAIL_KEY, mEmailEditText.getText().toString());
                    replyIntent.putExtra(CANDIDATE_KEY, mCandidateEditText.getText().toString());
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }
}
