package com.example.unitconverter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculator extends AppCompatActivity implements View.OnClickListener{

    EditText cday,cmonth,cyear,bday,bmonth,byear;
    TextView nextmonth,nextday,aday,amonth,ayear,btnage,btnClear;

    ImageView birthdaydate,currentdate;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);

        btnage=findViewById(R.id.textViewCalculate);
        btnClear=findViewById(R.id.textViewClear);
        cday=findViewById(R.id.editTextCurrentDay);
        cmonth=findViewById(R.id.editTextCurrentMonth);
        cyear=findViewById(R.id.editTextCurrentYear);
        bday=findViewById(R.id.editTextBirthDay);
        bmonth=findViewById(R.id.editTextBirthMonth);
        byear=findViewById(R.id.editTextBirthYear);
        nextday=findViewById(R.id.textViewNextBirthdayDays);
        nextmonth=findViewById(R.id.textViewNextBirthdayMonths);
        aday=findViewById(R.id.textViewFinalDays);
        ayear=findViewById(R.id.textViewFinalYears);
        amonth=findViewById(R.id.textViewFinalMonths);
        currentdate=findViewById(R.id.imageViewCalenderFirst);
        birthdaydate=findViewById(R.id.imageViewCalenderSecond);

        birthdaydate.setOnClickListener(this);
        currentdate.setOnClickListener(this);
        btnage.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        setCurrentDate();

    }

    private String addZero(int number) {
        String n;
        if (number < 10) {
            n = "0" + number;
        } else {
            n = String.valueOf(number);
        }
        return n;
    }

    private void setCurrentDate(){
        final Calendar calendar=Calendar.getInstance();
        cyear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        cmonth.setText(addZero(calendar.get(Calendar.MONTH)+1));
        cday.setText(addZero(calendar.get(Calendar.DAY_OF_MONTH)));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
       // Date date= new Date(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1);
        String dayofweek= simpleDateFormat.format(new Date());
        cday.setText(dayofweek);
        cday.setVisibility(View.VISIBLE);
    }

    private void nextBirthday(){
        int currentd=Integer.parseInt(cday.getText().toString());
        int currentm=Integer.parseInt(cmonth.getText().toString());
        int currenty=Integer.parseInt(cyear.getText().toString());

        Calendar current = Calendar.getInstance();
        current.set(currenty,currentm,currentd);

        int birthd=Integer.parseInt(bday.getText().toString());
        int birthm=Integer.parseInt(bmonth.getText().toString());
        int birthy=Integer.parseInt(byear.getText().toString());

        Calendar birthday= Calendar.getInstance();
        birthday.set(birthy,birthm,birthd);

        long difference=birthday.getTimeInMillis()-current.getTimeInMillis();
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(difference);
        nextmonth.setText(String.valueOf(cal.get(Calendar.MONTH)));
        nextday.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
    }

    private void calculateAge() {
        int currentDay = Integer.parseInt(cday.getText().toString());
        int currentMonth = Integer.parseInt(cmonth.getText().toString());
        int currentYear = Integer.parseInt(cyear.getText().toString());

        Date now = new Date(currentYear, currentMonth, currentDay);

        int birthDay = Integer.parseInt(bday.getText().toString());
        int birthMonth = Integer.parseInt(bmonth.getText().toString());
        int birthYear = Integer.parseInt(byear.getText().toString());

        Date dob = new Date(birthYear, birthMonth, birthDay);

        if (dob.after(now)) {
            Toast.makeText(this, "Birthday can't in future", Toast.LENGTH_SHORT).show();
            return;
        }
        // days of every month
        int month[] = {31, 28, 31, 30, 31, 30, 31,
                31, 30, 31, 30, 31};

        if (birthDay > currentDay) {
            currentDay = currentDay + month[birthMonth - 1];
            currentMonth = currentMonth - 1;
        }

        if (birthMonth > currentMonth) {
            currentYear = currentYear - 1;
            currentMonth = currentMonth + 12;
        }

        // calculate date, month, year
        int calculated_date = currentDay - birthDay;
        int calculated_month = currentMonth - birthMonth;
        int calculated_year = currentYear - birthYear;

        aday.setText(addZero(calculated_date));
        amonth.setText(addZero(calculated_month));
        ayear.setText(addZero(calculated_year));
    }

        public void onClick(View view) {
        if (view.getId() == R.id.imageViewCalenderSecond) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    bday.setText(addZero(dayOfMonth));
                    bmonth.setText(addZero(monthOfYear + 1));
                    byear.setText(String.valueOf(year));
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (view == btnage) {
            if (!TextUtils.isEmpty(bday.getText()) && !TextUtils.isEmpty(bmonth.getText()) && !TextUtils.isEmpty(byear.getText())) {
                calculateAge();
                nextBirthday();
            } else {
                Toast.makeText(this, "All fields are Required", Toast.LENGTH_SHORT).show();
            }
        } else if (view == btnClear) {
            bday.setText("");
            bmonth.setText("");
            byear.setText("");
            Toast.makeText(this, "Successfully Reset", Toast.LENGTH_SHORT).show();
        }
    }


}
