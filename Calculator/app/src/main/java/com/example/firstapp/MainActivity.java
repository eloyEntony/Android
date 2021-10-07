package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView totalCalc;

    private Double firstOperant;
    private Double lastOperant;
    private String operant = "";
    private Boolean ca = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textData);
        totalCalc = findViewById(R.id.textView);
        //                Toast.makeText(this, firstOperant.toString(), Toast.LENGTH_LONG).show();

    }

    public void ClickBtn(View view){

//        MainActivity intasnce = this;
//        NetworkService.getInstance()
//                .getJSONApi()
//                .getPostWithID(1)
//                .enqueue(new Callback<Post>() {
//                    @Override
//                    public void onResponse(retrofit2.Call<Post> call, Response<Post> response) {
//                        Post post = response.body();
//                        Toast.makeText(intasnce,post.getBody(), Toast.LENGTH_LONG).show();
//                    }
//                    @Override
//                    public void onFailure(retrofit2.Call<Post> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });

        Button btn = (Button) view;
        String type = btn.getText().toString();

        switch (type){
            case "+":
            case "-":
            case "*":
            case "/":
                operant = type;
                //CheckOperant(type);
                firstOperant = Double.parseDouble(textView.getText().toString());
                AddTotal(type);

                break;
            case "=":
                lastOperant = Double.parseDouble(textView.getText().toString());
//                totalCalc.append(textView.getText() + "=");
                ca = true;
                Calc(operant);
                break;
            case "C":
                Clear();
                break;
            case "CE":
                textView.setText("");
                break;
            default:
                textView.setText(textView.getText() + type);
                break;
        }
    }

    private void Calc (String type){
        switch (type){
            case "+":
                totalCalc.setText("");
                textView.setText((firstOperant += lastOperant).toString());
                break;
            case "-":
                totalCalc.setText("");
                textView.setText((firstOperant -= lastOperant).toString());
                break;
            case "*":
                totalCalc.setText("");
                textView.setText((firstOperant *= lastOperant).toString());
                break;
            case "/":
                if(lastOperant==0){
                    textView.setText("Can't divide by zero");
                }
                else{
                    totalCalc.setText("");
                    textView.setText((firstOperant /= lastOperant).toString());
                }
                break;
            default:
                break;
        }
    }

    private void CheckOperant(String type){


        lastOperant = Double.parseDouble(textView.getText().toString());
        Calc(type);
        firstOperant = Double.parseDouble(textView.getText().toString());
        lastOperant=null;


//        if(operant == type && ca==false){
//            lastOperant = Double.parseDouble(textView.getText().toString());
//            Calc(type);
//            firstOperant = Double.parseDouble(textView.getText().toString());
//            lastOperant=null;
//        }
//
//        if(type!=operant){
//            Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
//        }
//        if(operant!="" && type!=operant){
//            String tmp = totalCalc.getText().toString();
//            totalCalc.setText((tmp.substring(0, tmp.length() - 1)));
//        }
//        else{
//            firstOperant = Double.parseDouble(textView.getText().toString());
//        }
    }

    private void Clear(){
        textView.setText("");
        totalCalc.setText("");
        operant="";
        firstOperant =null;
        lastOperant =null;
    }

    private void AddTotal(String type){
        totalCalc.append(textView.getText() + type);
        operant = type;
        textView.setText("");
    }



}