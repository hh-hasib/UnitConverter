package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Email extends AppCompatActivity {

    EditText editTextSubject,editTextContent,editTextToEmail;
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        buttonSend = findViewById(R.id.btnSend);
        editTextContent = findViewById(R.id.contentMail);
        editTextSubject = findViewById(R.id.subject);
        editTextToEmail = findViewById(R.id.to_email);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject, content, to_email;
                subject = editTextSubject.getText().toString();
                content = editTextContent.getText().toString();
                to_email = editTextToEmail.getText().toString();

                if(subject.equals("") && content.equals("") && to_email.equals("")){
                    Toast.makeText(Email.this,"All fields are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendEmail(subject,content,to_email);
                }
            }
        });
    }

    public void sendEmail(String subject,String content,String to_email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to_email});
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Coose email client: "));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}