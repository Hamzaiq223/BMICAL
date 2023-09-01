package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class BMICalculator extends AppCompatActivity  implements View.OnClickListener {

    CardView weightCardView;
    CardView ageCardView,height_cardView;
    TextView weightCounterText, ageCounterText, height_title_text,tvKG,tvLBS,tvCM,tvFeet,height_counter_text;
    FloatingActionButton weightBtnInc, ageBtnInc;
    FloatingActionButton weightBtnDec, ageBtnDec;
    int weightCounter = 50;
    int ageCounter = 25;
    String countWeight, countAge;
    NumberPicker feetPicker, inchPicker;
    int feetValue = 5 , inchValue = 4;
    Button calculateBtn;
    String heightValue;
    DecimalFormat decimalFormat;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        // Initialize AdMob
        MobileAds.initialize(this);

        // Load banner ad
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        weightCardView = findViewById(R.id.weight_cardView);
        ageCardView = findViewById(R.id.age_cardView);
        weightCounterText = findViewById(R.id.weight_counter_text);
        ageCounterText = findViewById(R.id.age_counter_text);
        weightBtnInc = findViewById(R.id.weight_btn_inc);
        weightBtnDec = findViewById(R.id.weight_btn_dec);
        ageBtnInc = findViewById(R.id.age_btn_inc);
        ageBtnDec = findViewById(R.id.age_btn_dec);
        tvKG = findViewById(R.id.tvKG);
        tvLBS = findViewById(R.id.tvLBS);
        tvCM = findViewById(R.id.tvCM);
        tvFeet = findViewById(R.id.tvFeet);
        height_counter_text = findViewById(R.id.height_counter_text);
        height_cardView = findViewById(R.id.height_cardView);

        calculateBtn = findViewById(R.id.calculate_btn);
        counterInit();
        decimalFormat = new DecimalFormat(".#");
        weightCardView.setOnClickListener(this);
        ageCardView.setOnClickListener(this);
        weightBtnInc.setOnClickListener(this);
        weightBtnDec.setOnClickListener(this);
        ageBtnInc.setOnClickListener(this);
        ageBtnDec.setOnClickListener(this);
        tvFeet.setOnClickListener(this);
        tvCM.setOnClickListener(this);
        tvKG.setOnClickListener(this);
        tvLBS.setOnClickListener(this);
        height_counter_text.setOnClickListener(this);


//        feetPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                feetValue = newVal;
//                heightValueIs();
//
//            }
//        });
//
//        inchPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                inchValue = newVal;
//                heightValueIs();
//            }
//        });
        calculateBtn.setOnClickListener(v -> calculateBmi());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.weight_cardView:
                break;
            case R.id.weight_btn_inc:
                if(weightCounter < 700)
                    weightCounter++;
                countWeight = Integer.toString(weightCounter);
                weightCounterText.setText(countWeight);
                break;
            case R.id.weight_btn_dec:
                if(weightCounter > 0)
                    weightCounter--;
                countWeight = Integer.toString(weightCounter);
                weightCounterText.setText(countWeight);
                break;
            case R.id.age_cardView:
                break;
            case R.id.age_btn_inc:
                if(ageCounter < 150)
                    ageCounter++;
                countAge = Integer.toString(ageCounter);
                ageCounterText.setText(countAge);
                break;
            case R.id.age_btn_dec:
                if(ageCounter > 1)
                    ageCounter--;
                countAge = Integer.toString(ageCounter);
                ageCounterText.setText(countAge);
                break;
            case R.id.tvLBS:
                weightCounterText.setText("132");
                weightCounter = 132;
                tvLBS.setTextColor(ContextCompat.getColor(this, R.color.app_color));
                tvKG.setTextColor(ContextCompat.getColor(this,R.color.grey));
                break;
            case R.id.tvCM:
                height_counter_text.setText("152");
                break;
            case R.id.tvFeet:
                break;
            case R.id.tvKG:
                weightCounterText.setText("50");
                weightCounter = 50;
                tvLBS.setTextColor(ContextCompat.getColor(this, R.color.grey));
                tvKG.setTextColor(ContextCompat.getColor(this,R.color.app_color));
                break;

            case R.id.height_counter_text:
                showCenteredDialog();
                break;

        }
    }

    private void counterInit() {
        countWeight = Integer.toString(weightCounter);
        weightCounterText.setText(countWeight);
        countAge = Integer.toString(ageCounter);
        ageCounterText.setText(countAge);

        heightValueIs();
    }
    public void heightValueIs()
    {
        if(inchValue == 0){
            heightValue = feetValue + " feet ";
//            height_title_text.setText(heightValue);
        }
        else
            heightValue = feetValue + " feet " + inchValue +" inches";
//        height_title_text.setText(heightValue);
    }
    public void calculateBmi(){
        double heightInInches = feetValue * 12 + inchValue;
        double heightInMetres = heightInInches / 39.37;
        double heightInMetreSq = heightInMetres * heightInMetres;
        double bmi = weightCounter / heightInMetreSq;
        String bmiValue = decimalFormat.format(bmi);
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("bmiVal",bmiValue);
        startActivity(intent);
    }
    private void showCenteredDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.centered_dialog, null);
        builder.setView(dialogView);

        feetPicker = dialogView.findViewById(R.id.feet_picker);
        inchPicker = dialogView.findViewById(R.id.inch_picker);
        height_title_text = dialogView.findViewById(R.id.height_title_text);

        feetPicker.setMinValue(1);
        feetPicker.setMaxValue(8);
        inchPicker.setMinValue(0);
        inchPicker.setMaxValue(11);
        feetPicker.setValue(5);
        inchPicker.setValue(4);


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}