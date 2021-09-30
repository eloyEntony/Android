package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner1, spinner2;
    private Button btnSubmit;

    List<Currency> currencyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadData();

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }



    public void GetData(View view){

    }

    public void LoadData(){
        MainActivity intasnce = this;
        NetworkService.getInstance()
                .getJSONApi()
                .getCurrencyList()
                .enqueue(new Callback<List<Currency>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<Currency>> call, Response<List<Currency>> response) {
                        currencyList = response.body();
                        Toast.makeText(intasnce, currencyList.get(0).getBase_ccy(), Toast.LENGTH_LONG).show();
                        addItemsOnSpinner2();

                    }
                    @Override
                    public void onFailure(retrofit2.Call<List<Currency>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();

        for (Currency item : currencyList) {
            list.add(item.getBase_ccy());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list2 = new ArrayList<String>();

        for (Currency item : currencyList) {
            list2.add(item.getCcy());
        }
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter2);

    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }
}