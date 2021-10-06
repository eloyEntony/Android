package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Spinner dropTo, dropFrom;
    private Button btnSubmit;
    private EditText mEdit;

    List<Currency> currencyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadData();

//        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }



    public void GetData(View view){
        btnSubmit = (Button) findViewById(R.id.button);
        mEdit = findViewById(R.id.editTextTextPersonName);



        dropTo = (Spinner) findViewById(R.id.dropTo);
        dropTo.getSelectedItem();


        dropFrom = (Spinner) findViewById(R.id.dropFrom);
        dropFrom.getSelectedItem();

        double total = 0;

        for (Currency item : currencyList) {
            if(item.getBase_ccy() == dropFrom.getSelectedItem())
                total = Double.parseDouble(mEdit.getText().toString()) / item.getBuy();
        }


        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,
                                mEdit.getText().toString() + dropTo.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        TextView totalView = (TextView)findViewById(R.id.textView3);
        totalView.setText("You get: " + String.valueOf((double)Math.round(total * 100) / 100) + " " + dropTo.getSelectedItem());

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

    public void Calculate(String type, Double money){

        double total = 0;
        double buyTO = 0;
        for (Currency item : currencyList) {
            if(item.getBase_ccy() == dropFrom.getSelectedItem())
                buyTO = item.getBuy();
        }

        switch (type){
            case "UAH":
                total = money / buyTO;
                break;
            case "USD":

                break;
            default:
                break;
        }
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        dropFrom = (Spinner) findViewById(R.id.dropFrom);
        List<String> list = new ArrayList<String>();

        for (Currency item : currencyList) {
            if(!list.contains(item.getBase_ccy()) && !list.contains(item.getCcy())){
                list.add(item.getBase_ccy());
                list.add(item.getCcy());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropFrom.setAdapter(dataAdapter);

        addItemsToDropTo(list);


    }

    public void addItemsToDropTo(List<String> list){
//        dropTo = (Spinner) findViewById(R.id.dropTo);
        List<String> list2 = new ArrayList<String>();
//
//        for (Currency item : currencyList) {
//            list2.add(item.getCcy());
//        }
//

        switch (dropFrom.getSelectedItem().toString()){
            case "UAH":
                for(String item: list){
                    if(item != "UAH")
                        list2.add(item);
                }
                break;
            case "BTC":
                for(String item: list){
                    if(item == "USD")
                        list2.add(item);
                }
                break;
            default:

                break;
        }

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropTo.setAdapter(dataAdapter2);
    }

    public void addListenerOnSpinnerItemSelection() {
        dropTo = (Spinner) findViewById(R.id.dropTo);
        dropTo.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        dropTo = (Spinner) findViewById(R.id.dropTo);
        dropFrom = (Spinner) findViewById(R.id.dropFrom);
        btnSubmit = (Button) findViewById(R.id.button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,
                        "OnClickListener : " +
                                "\nSpinner TO : "+ String.valueOf(dropTo.getSelectedItem()) +
                                "\nSpinner FROM : "+ String.valueOf(dropFrom.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }
}