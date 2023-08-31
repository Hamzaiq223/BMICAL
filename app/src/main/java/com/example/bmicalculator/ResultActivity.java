package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ResultActivity extends AppCompatActivity {

    TextView bmiValue, bmiCategory,bmiTips, tvNormalWeight;
    String category;
    String bmiValOutput;
    Button calculateAgainBtn;
    String[] bmiTipsArray;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize AdMob
        MobileAds.initialize(this);

        bmiValue = findViewById(R.id.bmi_value);
        bmiCategory = findViewById(R.id.bmi_category);
        bmiTips = findViewById(R.id.bmi_tips);
        bmiTipsArray = getResources().getStringArray(R.array.tips_array);
        calculateAgainBtn = findViewById(R.id.calculate_again_btn);
        tvNormalWeight = findViewById(R.id.tvNormalWeight);
        bmiValOutput = getIntent().getStringExtra("bmiVal");
        bmiValue.setText(bmiValOutput);
        findCategory();
        categoryTips();
        calculateAgainBtn.setOnClickListener(v -> onBackPressed());

        // Load banner ad
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void categoryTips() {
        double result = Double.parseDouble(bmiValOutput);
        if(result < 15){
            bmiTips.setText(bmiTipsArray[0]);
        }
        else if(result >= 15 && result <= 16){
            bmiTips.setText(bmiTipsArray[0]);
        }
        else if(result >= 16 && result <= 18.5){
            bmiTips.setText(bmiTipsArray[1]);
        }
        else if(result >= 18.5 && result <= 25){
            bmiTips.setText(bmiTipsArray[2]);
        }
        else if(result >= 25 && result <= 30){
            bmiTips.setText(bmiTipsArray[3]);
        }
        else if(result >=30 && result <= 35){
            bmiTips.setText(bmiTipsArray[4]);
        }
        else if(result >= 35 && result <= 50){
            bmiTips.setText(bmiTipsArray[4]);
        }
        else
            bmiTips.setText(bmiTipsArray[4]);

    }

    private void findCategory() {
        double result = Double.parseDouble(bmiValOutput);
        if(result < 15){
            category = "Very Severely Underweight";
            bmiCategory.setText(category);
        }
        else if(result >= 15 && result <= 16){
            category = "Severely Underweight";
            bmiCategory.setText(category);
        }
        else if(result >= 16 && result <= 18.5){
            category = "Underweight";
            bmiCategory.setText(category);
        }
        else if(result >= 18.5 && result <= 25){
            category = "Normal (Healthy weight)";
            bmiCategory.setText(category);
        }
        else if(result >= 25 && result <= 30){
            category = "Overweight";
            bmiCategory.setText(category);
        }
        else if(result >=30 && result <= 35){
            category = "Moderately Obese";
            bmiCategory.setText(category);
        }
        else if(result >= 35 && result <= 50){
            bmiCategory.setText(category);
            category = "Severely Obese";
        }
        else
            category = "Very Severely Obese";
        bmiCategory.setText(category);

    }


}