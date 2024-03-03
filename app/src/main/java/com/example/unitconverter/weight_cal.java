package com.example.unitconverter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class weight_cal extends AppCompatActivity {

    CardView cv_fromUnit, cv_toUnit, cv_convert;
    RelativeLayout mCLayout;
    String fromUnit = "Kilogram";
    String toUnit = "Kilogram";
    TextView tv_fromUnit, tv_toUnit;
    EditText et_fromUnit, et_toUnit;
    final String[] values = new String[]{
            "Kilogram",
            "Gram",
            "Exa Gram",
            "Peta Gram",
            "Tera Gram", "Giga Gram"
    };
    private RecyclerView rvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_cal);

        cv_fromUnit = findViewById(R.id.fromUnit);
        cv_toUnit = findViewById(R.id.toUnit);
        cv_convert = findViewById(R.id.cv_convert);

        mCLayout = findViewById(R.id.temp_relativeLayout);

        tv_fromUnit = findViewById(R.id.tv_fromUnit);
        tv_toUnit = findViewById(R.id.tv_toUnit);

        tv_fromUnit.setText(values[0]);
        tv_toUnit.setText(values[0]);

        et_fromUnit = findViewById(R.id.et_fromUnit);
        et_toUnit = findViewById(R.id.et_toUnit);

        rvHistory = findViewById(R.id.rvHistory);

        cv_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempInput = et_fromUnit.getText().toString();
                if (tempInput.equals("")) {
                    et_fromUnit.setError("Please enter some value");
                    return;
                }
                    if (tv_fromUnit.getText().toString().equals(values[0])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(kilogramToGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(kilogramToExaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(kilogramToPetaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(kilogramToTeraGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(kilogramToGigaGram(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[1])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(gramToKiloGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(gramToExaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(gramToPetaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(gramToTeraGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(gramToGigaGram(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[2])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(exaGramToKiloGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(exaGramToGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(exaGramToPetaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(exaGramToTeraGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(exaGramToGigaGram(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[3])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(petaGramToKiloGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(petaGramToExaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(petaGramToExaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(petaGramToTeraGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(petaGramToGigaGram(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[4])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(teraGramToKiloGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(teraGramToGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(teraGramToExaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(teraGramToPetaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(tempInput);
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(teraGramToGigaGram(Double.parseDouble(tempInput)));
                        }
                    } else if (tv_fromUnit.getText().toString().equals(values[5])) {
                        if (tv_toUnit.getText().toString().equals(values[0])) {
                            et_toUnit.setText(gigaGramToKiloGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[1])) {
                            et_toUnit.setText(gigaGramToGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[2])) {
                            et_toUnit.setText(gigaGramToExaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[3])) {
                            et_toUnit.setText(gigaGramToPetaGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[4])) {
                            et_toUnit.setText(gigaGramToTeraGram(Double.parseDouble(tempInput)));
                        } else if (tv_toUnit.getText().toString().equals(values[5])) {
                            et_toUnit.setText(tempInput);
                        }
                    }

                String fromValue = String.valueOf(et_fromUnit.getText());
                String toValue = String.valueOf(et_toUnit.getText());
                String fromUnit = String.valueOf(tv_fromUnit.getText());
                String toUnit = String.valueOf(tv_toUnit.getText());
                saveHistory(fromUnit, fromValue, toUnit, toValue);
            }
        });


        cv_toUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(weight_cal.this);
                builder.setTitle("choose Unit");

                final String[] flowers = new String[]{
                        "Kilogram",
                        "Gram",
                        "Exa Gram",
                        "Peta Gram",
                        "Tera Gram", "Giga Gram"
                };

                builder.setSingleChoiceItems(
                        flowers, // Items list
                        -1, // Index of checked item (-1 = no selection)
                        new DialogInterface.OnClickListener() // Item click listener
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Get the alert dialog selected item's text
                                String selectedItem = Arrays.asList(flowers).get(i);
                                toUnit = selectedItem;
                                tv_toUnit.setText(toUnit);

                            }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Just dismiss the alert dialog after selection
                        // Or do something now
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();

                // Finally, display the alert dialog
                dialog.show();

            }
        });

        cv_fromUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(weight_cal.this);
                builder.setTitle("choose Unit");

                final String[] flowers = new String[]{
                        "Kilogram",
                        "Gram",
                        "Exa Gram",
                        "Peta Gram",
                        "Tera Gram", "Giga Gram"
                };

                builder.setSingleChoiceItems(
                        flowers, // Items list
                        -1, // Index of checked item (-1 = no selection)
                        new DialogInterface.OnClickListener() // Item click listener
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Get the alert dialog selected item's text
                                String selectedItem = Arrays.asList(flowers).get(i);
                                fromUnit = selectedItem;
                                tv_fromUnit.setText(fromUnit);

                            }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Just dismiss the alert dialog after selection
                        // Or do something now
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();

                // Finally, display the alert dialog
                dialog.show();

            }
        });

        downloadHistory();
    }

    private void saveHistory(String fromUnit, String fromValue, String toUnit, String toValue) {

        try {
            toValue = toValue.substring(0, toValue.indexOf(".") + 6);
        } catch (Exception ignored) {
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String userId = auth.getUid();

        if (userId == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("history")
                .child(userId).child("weight");

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM hh:mma");
        String time = formatter.format(ldt);

        Map<String, String> map = new HashMap<>();
        map.put("fromUnit", fromUnit);
        map.put("fromValue", fromValue);
        map.put("toUnit", toUnit);
        map.put("toValue", toValue);
        map.put("time", time);

        ref.push().setValue(map);
    }

    private void downloadHistory() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String userId = auth.getUid();

        if (userId == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("history")
                .child(userId).child("weight");
        Query query = ref.limitToFirst(100);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;

                List<SingleHistory> list = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String fromUnit = String.valueOf(ds.child("fromUnit").getValue());
                    String toUnit = String.valueOf(ds.child("toUnit").getValue());
                    String fromValue = String.valueOf(ds.child("fromValue").getValue());
                    String toValue = String.valueOf(ds.child("toValue").getValue());
                    String time = String.valueOf(ds.child("time").getValue());
                    String key = String.valueOf(ds.getKey());

                    SingleHistory history = new SingleHistory(key, fromUnit, toUnit, fromValue, toValue, time);
                    list.add(history);
                }

                HistoryAdapter adapter = new HistoryAdapter();
                rvHistory.setAdapter(adapter);
                adapter.submitList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Kilogram
    private String kilogramToGram(double kilogram) {
        double gram = kilogram * 1000;
        return String.valueOf(gram);
    }

    private String kilogramToExaGram(double kilogram) {
        double exaGram = kilogram * 1.0E-15;
        return String.valueOf(exaGram);
    }

    private String kilogramToPetaGram(double kilogram) {
        double petaGram = kilogram * 1.0E-12;
        return String.valueOf(petaGram);
    }

    private String kilogramToTeraGram(double kilogram) {
        double teraGram = kilogram * 1.0E-9;
        String res = String.valueOf(teraGram);

        try{
            String left = res.substring(0,res.indexOf(".")+3);

            if(res.contains("E")){
                String right = res.substring(res.indexOf("E"));
                return left+right;
            }
            return left;
        }
        catch (Exception ignored){
            return res;
        }
    }

    private String kilogramToGigaGram(double kilogram) {
        double gigaGram = kilogram / 1000000;
        return String.valueOf(gigaGram);
    }

    //Gram
    private String gramToExaGram(double gram) {
        double exaGram = gram * 1.0E-18;
        return String.valueOf(exaGram);
    }

    private String gramToPetaGram(double gram) {
        double petaGram = gram * 1.0E-15;
        return String.valueOf(petaGram);
    }

    private String gramToTeraGram(double gram) {
        double teraGram = gram * 1.0E-12;
        return String.valueOf(teraGram);
    }

    private String gramToGigaGram(double gram) {
        double gigaGram = gram * 1.0E-9;
        return String.valueOf(gigaGram);
    }

    private String gramToKiloGram(double gram) {
        double kiloGram = gram * 0.001;
        return String.valueOf(kiloGram);
    }

    //Exa Gram
    private String exaGramToPetaGram(double exaGram) {
        double petaGram = exaGram * 1000;
        return String.valueOf(petaGram);
    }

    private String exaGramToTeraGram(double exaGram) {
        double teraGram = exaGram * 1000000;
        return String.valueOf(teraGram);
    }

    private String exaGramToGigaGram(double exaGram) {
        double gigaGram = exaGram * 1e+9;
        return String.valueOf(gigaGram);
    }

    private String exaGramToKiloGram(double exaGram) {
        double kiloGram = exaGram * 1.0E+15;
        return String.valueOf(kiloGram);
    }

    private String exaGramToGram(double exaGram) {
        double gram = exaGram * 1.0E+18;
        return String.valueOf(gram);
    }

    //Peta Gram
    private String petaGramToTeraGram(double petaGram) {
        double teraGram = petaGram * 1000;
        return String.valueOf(teraGram);
    }

    private String petaGramToGigaGram(double petaGram) {
        double gigaGram = petaGram * 1e+6;
        return String.valueOf(gigaGram);
    }

    private String petaGramToKiloGram(double petaGram) {
        double kiloGram = petaGram * 1e+12;
        return String.valueOf(kiloGram);
    }

    private String petaGramToGram(double petaGram) {
        double Gram = petaGram * 1e+15;
        return String.valueOf(Gram);
    }

    private String petaGramToExaGram(double petaGram) {
        double exaGram = petaGram / 1000;
        return String.valueOf(exaGram);
    }

    //Tera Gram
    private String teraGramToGigaGram(double teraGram) {
        double gigaGram = teraGram * 1000;
        return String.valueOf(gigaGram);
    }

    private String teraGramToKiloGram(double teraGram) {
        double kiloGram = teraGram * 1e+9;
        return String.valueOf(kiloGram);
    }

    private String teraGramToGram(double teraGram) {
        double Gram = teraGram * 1e+12;
        return String.valueOf(Gram);
    }

    private String teraGramToExaGram(double teraGram) {
        double exaGram = teraGram / 1e+6;
        return String.valueOf(exaGram);
    }

    private String teraGramToPetaGram(double teraGram) {
        double petaGram = teraGram / 1000;
        return String.valueOf(petaGram);
    }

    //Giga Gram
    private String gigaGramToKiloGram(double gigaGram) {
        double kiloGram = gigaGram * 1e+6;
        return String.valueOf(kiloGram);
    }

    private String gigaGramToGram(double gigaGram) {
        double Gram = gigaGram * 1e+9;
        return String.valueOf(Gram);
    }

    private String gigaGramToExaGram(double gigaGram) {
        double exaGram = gigaGram / 1e+9;
        return String.valueOf(exaGram);
    }

    private String gigaGramToPetaGram(double gigaGram) {
        double petaGram = gigaGram / 1e+6;
        return String.valueOf(petaGram);
    }

    private String gigaGramToTeraGram(double gigaGram) {
        double teraGram = gigaGram / 1000;
        return String.valueOf(teraGram);
    }

}