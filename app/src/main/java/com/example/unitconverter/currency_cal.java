package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class currency_cal extends AppCompatActivity {

    TextView convertFromDropdownTextView,convertToDropdownTextView,fromUnit,toUnit;
    EditText amountToConvert,conversionRateText;
    ArrayList<String> arrayList;
    Dialog fromDialog;
    Dialog toDialog;
    String convertFromValue="USD",convertToValue="CAD",conversionValue;
    RelativeLayout conversionBtn;
    String[] country={"EUR", "USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF",
            "PLN", "RON", "SEK", "CHF", "ISK", "NOK", "HRK", "RUB",
            "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS",
            "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"}; //TODO


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_cal);

        convertFromDropdownTextView = findViewById(R.id.tv2_fromUnit);
        convertToDropdownTextView = findViewById(R.id.tv2_toUnit);
        conversionBtn = findViewById(R.id.btn_convert);
        conversionRateText = findViewById(R.id.et_toUnit);
        amountToConvert = findViewById(R.id.et_fromUnit);
        fromUnit = findViewById(R.id.tv_fromUnit);
        toUnit = findViewById(R.id.tv_toUnit);

        arrayList = new ArrayList<>();
        for (String i : country){
            arrayList.add(i);
        }

        convertFromDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(currency_cal.this);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(650,800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.edit_text_from);
                ListView listView = fromDialog.findViewById(R.id.list_view_from);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(currency_cal.this, android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        fromUnit.setText(adapter.getItem(i));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(i);
                    }
                });

            }
        });

        convertToDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDialog = new Dialog(currency_cal.this);
                toDialog.setContentView(R.layout.to_spinner);
                toDialog.getWindow().setLayout(650,800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.edit_text_from);
                ListView listView = toDialog.findViewById(R.id.list_view_from);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(currency_cal.this, android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        toUnit.setText(adapter.getItem(i));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(i);
                    }
                });
            }
        });
        conversionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Double amountToConvert = Double.valueOf(currency_cal.this.amountToConvert.getText().toString());
                    getConversionRate(convertFromValue,convertToValue,amountToConvert);
                }catch (Exception e){

                }
            }
        });

    }

    public String getConversionRate(String convertFrom, String convertTo, Double amountToConvert){
        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertTo+"&compact=ultra&apikey=22e91ab924eb2aa6f9a4";
        String url = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_8irD86IiLHagmLRqtvzgbCHlO6qfc29itVsdOp5H&currencies="+convertTo+"&base_currency="+convertFrom;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONObject nested = jsonObject.getJSONObject("data");
                    Double conversionRateValue = round(((Double) nested.get(convertTo)), 2);
                    conversionValue = " " + round((conversionRateValue * amountToConvert), 2);
                    conversionRateText.setText(conversionValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error);
            }
        });
        queue.add(stringRequest);
        return null;
    }

    public static double round(double value, int places){
        if(places<0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}