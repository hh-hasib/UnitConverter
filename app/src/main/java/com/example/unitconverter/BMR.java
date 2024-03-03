package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BMR extends AppCompatActivity {

    CardView cardBtn;
    EditText feet, inch, weight, age;
    TextView result;
    LinearLayout maleLayout, femaleLayout;
    ImageView maleImage, femaleImage;

    double h=0,f = 0, i = 0, w = 0, a = 0, bmr = 0;
    String user = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr);
        cardBtn = findViewById(R.id.cardBtn);
        feet = findViewById(R.id.edFeet);
        inch = findViewById(R.id.edIns);
        weight = findViewById(R.id.edKg);
        age = findViewById(R.id.edAge);
        result = findViewById(R.id.textView);
        maleLayout = findViewById(R.id.male);
        femaleLayout = findViewById(R.id.female);
        maleImage = findViewById(R.id.male_img);
        femaleImage = findViewById(R.id.female_img);

        maleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                femaleLayout.setBackgroundColor(0);
                user = "Male";
            }
        });
        femaleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femaleLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                maleLayout.setBackgroundColor(0);
//                femaleImage.setColorFilter(getResources().getColor(R.color.purple_200));
//                maleImage.setColorFilter(getResources().getColor(R.color.grey));
                user = "Female";

            }
        });
        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1=feet.getText().toString();
                String str2=inch.getText().toString();
                String str3=weight.getText().toString();
                String str4=age.getText().toString();

                if (user.equals("0")){
                    Toast.makeText(BMR.this,"Select your Gender",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(str4)){
                    age.setError("Select Age");
                    age.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(str3)){
                    weight.setError("Select Weight");
                    weight.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(str1)){
                    feet.setError("Select Feet");
                    feet.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(str2)){
                    inch.setError("Select Inch");
                    inch.requestFocus();
                    return;
                }
                else{
                    calculate();
                }
            }
        });

    }
    private void calculate(){
        f=Double.parseDouble(feet.getText().toString());
        i=Double.parseDouble(inch.getText().toString());
        w=Double.parseDouble(weight.getText().toString());
        a=Double.parseDouble(age.getText().toString());

        h = (double) ((f*0.3048+ i*0.0254)*100);

        if (user.equals("Male")){
            bmr=((10*w)+(6.25*h)-(5*a)+5);
            result.setText(Double.toString(bmr));
        }
        if (user.equals("Female")){
            bmr=((10*w)+(6.25*h)-(5*a)-161);
            result.setText(Double.toString(bmr));
        }
    }

}