package com.tool.bmicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class BMICalculator extends AppCompatActivity  implements View.OnClickListener {

    CardView weightCardView;
    private AppOpenAd appOpenAd;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    CardView ageCardView,height_cardView;
    TextView weightCounterText, ageCounterText, height_title_text,tvKG,tvLBS,tvCM,tvFeet,height_counter_text;
    FloatingActionButton weightBtnInc, ageBtnInc;
    FloatingActionButton weightBtnDec, ageBtnDec;
    int weightCounter = 50;
    int ageCounter = 25;
    String countWeight, countAge;
    NumberPicker feetPicker, inchPicker;
    int feetValue = 5 , inchValue = 0;
    Button calculateBtn;
    String heightValue,heightDialog = "CM",weightDialog = "KG";
    DecimalFormat decimalFormat;

    private AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        // Initialize AdMob
        MobileAds.initialize(this);
        MobileAds.initialize(this, initializationStatus -> {
            // Initialization completed, you can now load and display ads
        });


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
        weightCounterText.setOnClickListener(this);

        calculateBtn.setOnClickListener(view -> {
            if(weightDialog.equals("KG") && heightDialog.equals("FEET"))
            {
                calculateBmi();
            }
            else if(weightDialog.equals("KG") && heightDialog.equals("CM"))
            {
                calculateBMI(Double.parseDouble(weightCounterText.getText().toString()),Double.parseDouble(height_counter_text.getText().toString()));
            }
            else if((weightDialog.equals("LBS") && heightDialog.equals("CM")))
            {
                bmiCal(Double.parseDouble(weightCounterText.getText().toString()),Double.parseDouble(height_counter_text.getText().toString()));
            }
            else{
                double heightCM = feetToCentimeters(Double.parseDouble(height_counter_text.getText().toString()));

                bmiCal(Double.parseDouble(weightCounterText.getText().toString()),heightCM);

            }
        });

    }


    // Method to load the App Open Ad
    private void loadAppOpenAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        AppOpenAd.load(this, "ca-app-pub-3940256099942544/3419835294", adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    // Method to show the App Open Ad if available
    private void showAppOpenAdIfAvailable() {
        if (appOpenAd != null) {
            appOpenAd.show(this);
        } else {
            // App open ad not available, handle accordingly
        }
    }
    // Implement the App Open Ad Load Callback
    private void initializeAppOpenAdLoadCallback() {
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                appOpenAd = ad;
                showAppOpenAdIfAvailable();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle ad loading failure
            }
        };
    }
    public double feetToCentimeters(double heightInFeet) {
        // Define a constant for the number of centimeters in a foot
        final double CM_PER_FOOT = 30.48;

        // Convert feet to centimeters
        double heightInCentimeters = heightInFeet * CM_PER_FOOT;

        return heightInCentimeters;
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

            case R.id.weight_counter_text:
                showWeightDialog(weightDialog.equals("KG") ? true : false);

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
                weightDialog = "LBS";
                tvLBS.setTextColor(ContextCompat.getColor(this, R.color.app_color));
                tvKG.setTextColor(ContextCompat.getColor(this,R.color.dark_grey));
                break;
            case R.id.tvCM:
                height_counter_text.setText("152");
                heightDialog = "CM";
                tvCM.setTextColor(ContextCompat.getColor(this, R.color.app_color));
                tvFeet.setTextColor(ContextCompat.getColor(this,R.color.dark_grey));
                break;
            case R.id.tvFeet:
                height_counter_text.setText("5.0");
                heightDialog = "FEET";
                tvFeet.setTextColor(ContextCompat.getColor(this, R.color.app_color));
                tvCM.setTextColor(ContextCompat.getColor(this,R.color.dark_grey));
                break;
            case R.id.tvKG:
                weightCounterText.setText("50");
                weightCounter = 50;
                weightDialog = "KG";
                tvLBS.setTextColor(ContextCompat.getColor(this, R.color.dark_grey));
                tvKG.setTextColor(ContextCompat.getColor(this,R.color.app_color));
                break;

            case R.id.height_counter_text:
                showCenteredDialog(heightDialog.equals("CM") ? true : false);
                break;


        }
    }

    private void counterInit() {
        countWeight = Integer.toString(weightCounter);
        weightCounterText.setText(countWeight);
        countAge = Integer.toString(ageCounter);
        ageCounterText.setText(countAge);

    }

    public void calculateBmi(){
        double heightInInches = feetValue * 12 + inchValue;
        double heightInMetres = heightInInches / 39.37;
        double heightInMetreSq = heightInMetres * heightInMetres;
        double bmi = weightCounter / heightInMetreSq;
        String bmiValue = decimalFormat.format(bmi);
        Intent intent = new Intent(this, com.tool.bmicalculator.ResultActivity.class);
        intent.putExtra("bmiVal",bmiValue);
        startActivity(intent);
    }
    public void  calculateBMI(double weightInKg, double heightInCm) {
        // Convert height from cm to meters
        double heightInMeters = heightInCm / 100.0;

        // Calculate BMI using the formula: BMI = weight (kg) / (height (m) * height (m))
        double bmi = weightInKg / (heightInMeters * heightInMeters);
        String bmiValue = decimalFormat.format(bmi);
        Intent intent = new Intent(this, com.tool.bmicalculator.ResultActivity.class);
        intent.putExtra("bmiVal",bmiValue);
        startActivity(intent);
    }
    public  void bmiCal(double weightInPounds, double heightInCm) {
        // Convert weight from pounds to kilograms
        double weightInKg = weightInPounds * 0.45359237;

        // Convert height from cm to meters
        double heightInMeters = heightInCm / 100.0;

        // Calculate BMI using the formula: BMI = weight (kg) / (height (m) * height (m))
        double bmi = weightInKg / (heightInMeters * heightInMeters);

        String bmiValue = decimalFormat.format(bmi);
        Intent intent = new Intent(this, com.tool.bmicalculator.ResultActivity.class);
        intent.putExtra("bmiVal",bmiValue);
        startActivity(intent);

    }


    private void showCenteredDialog(Boolean check) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.centered_dialog, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnOk = dialogView.findViewById(R.id.btnOK);
        TextView tvAverageHeight = dialogView.findViewById(R.id.tvAverageHeight);
        CardView cvCM = dialogView.findViewById(R.id.cm_cardView);
        CardView cvFeet = dialogView.findViewById(R.id.feet_cardView);
        CardView cvInch = dialogView.findViewById(R.id.inch_cardView);
        NumberPicker cmPicker = dialogView.findViewById(R.id.cm_picker);
        feetPicker = dialogView.findViewById(R.id.feet_picker);
        inchPicker = dialogView.findViewById(R.id.inch_picker);
        height_title_text = dialogView.findViewById(R.id.height_title_text);

        tvAverageHeight.setText(check ? "Average Height : 152 cm" : "Average Height : 5.0 ft");
        cvFeet.setVisibility(check ? View.GONE : View.VISIBLE);
        cvInch.setVisibility(check ? View.GONE : View.VISIBLE);
        cvCM.setVisibility(check ? View.VISIBLE : View.GONE);


        btnOk.setOnClickListener(view -> {
             dialog.dismiss();
             height_counter_text.setText(check ? String.format("%s", cmPicker.getValue()) : String.format("%s.%s", feetPicker.getValue(), inchPicker.getValue()));
             if(!check)
             {
                 feetValue = feetPicker.getValue();
                 inchValue = inchPicker.getValue();
                 Log.d("Height in Feet",feetValue+" "+inchValue);
             }
        });

        btnCancel.setOnClickListener(view -> {
             dialog.dismiss();
        });


        if(check){
            cmPicker.setMinValue(30);
            cmPicker.setMaxValue(300);
            cmPicker.setValue(Integer.parseInt(height_counter_text.getText().toString()));
        }
        else{

            String input = height_counter_text.getText().toString();
            String[] parts = input.split("\\.");

            feetPicker.setMinValue(1);
            feetPicker.setMaxValue(8);
            inchPicker.setMinValue(0);
            inchPicker.setMaxValue(11);

            int ft = Integer.parseInt(parts[0]);
            int inch = Integer.parseInt(parts[1]);

            feetPicker.setValue(ft);
            inchPicker.setValue(inch);

        }

        dialog.show();
    }

    private void showWeightDialog(Boolean check) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.weight_dialog, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnOk = dialogView.findViewById(R.id.btnOK);
        TextView tvAverageHeight = dialogView.findViewById(R.id.tvAverageHeight);

        CardView cvKG = dialogView.findViewById(R.id.kg_cardView);
        CardView cvLbs = dialogView.findViewById(R.id.lbs_cardView);
        NumberPicker kgPicker = dialogView.findViewById(R.id.kg_picker);
        NumberPicker lbsPicker = dialogView.findViewById(R.id.lbs_picker);
        height_title_text = dialogView.findViewById(R.id.height_title_text);

        tvAverageHeight.setText(check ? "Average Weight : 60 kg" : "Average Weight : 132 lbs");

        cvKG.setVisibility(check ? View.VISIBLE : View.GONE);
        cvLbs.setVisibility(check ? View.GONE : View.VISIBLE);

        btnOk.setOnClickListener(view -> {
            dialog.dismiss();
            weightCounterText.setText(check ? String.format("%s", kgPicker.getValue()) : String.format("%s", lbsPicker.getValue()));
            weightCounter = check ? kgPicker.getValue() : lbsPicker.getValue();

        });

        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });


        if(check){
            kgPicker.setMinValue(10);
            kgPicker.setMaxValue(300);
            kgPicker.setValue(Integer.parseInt(weightCounterText.getText().toString()));
        }
        else{

            lbsPicker.setMinValue(22);
            lbsPicker.setMaxValue(660);

            lbsPicker.setValue(Integer.parseInt(weightCounterText.getText().toString()));

        }

        dialog.show();
    }
}