package com.example.budgetsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BudgetDialog.BudgetDialogListener {
    //References to buttons and other controls on layout
    Button btn_observe, btn_add, bttn_budget;
    EditText et_title, et_price;
    TextView tv_daily, tv_weekly, tv_dailyAmount, tv_weeklyAmount;
    ListView lv_purchaseList;
    ArrayAdapter purchaseArrayAdapter;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_observe = findViewById(R.id.btn_observe);
        et_title = findViewById(R.id.et_title);
        et_price = findViewById(R.id.et_price);
        tv_daily = findViewById(R.id.tv_daily);
        tv_weekly = findViewById(R.id.tv_weekly);
        bttn_budget = findViewById(R.id.bttn_budget);
        tv_dailyAmount = findViewById(R.id.tv_dailyAmount);
        tv_weeklyAmount = findViewById(R.id.tv_weeklyAmount);
        lv_purchaseList = findViewById(R.id.lv_purchaseList);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<PurchaseModel> dayPurchases = dataBaseHelper.getEverythingFromCurrentDay();
        List<String> stringPurchases = null;

        stringPurchases = new ArrayList<>();
        for (int i = 0; i < dayPurchases.size(); i++) {
            System.out.println(i);
            String s = String.valueOf(dayPurchases.get(i));
            String title = s.substring(s.indexOf("title=") + 7, s.indexOf("date") - 3);
            String price = s.substring(s.indexOf("price=") + 6, s.indexOf("}"));
            System.out.println("====================");
            System.out.println(title);
            System.out.println(price);
            System.out.println("====================");

            String fullString = "$" + price + " " + title;


            stringPurchases.add(fullString);

        }


        purchaseArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringPurchases);
        lv_purchaseList.setAdapter(purchaseArrayAdapter);


        tv_daily.setText("Daily Expenditure: $" + dataBaseHelper.getDailyExpense());
        tv_weekly.setText("Weekly Expenditure: $" + dataBaseHelper.getWeeklyExpense());


        //Button listen
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PurchaseModel purchaseModel;

                try {
                    purchaseModel = new PurchaseModel(-1, et_title.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                    Toast.makeText(MainActivity.this, purchaseModel.toString(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Creating Customer", Toast.LENGTH_SHORT).show();
                    purchaseModel = new PurchaseModel(-1, "error", 0);

                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean s = dataBaseHelper.addOne(purchaseModel);

                Toast.makeText(MainActivity.this, "Success" + s, Toast.LENGTH_SHORT).show();

                //Update text value for weekly and daily
                tv_daily.setText("Daily Expenditure: $" + dataBaseHelper.getDailyExpense());

                tv_weekly.setText("Weekly Expenditure: $" + dataBaseHelper.getWeeklyExpense());

                List<PurchaseModel> dayPurchases = dataBaseHelper.getEverythingFromCurrentDay();
                List<String> stringPurchases = null;

                stringPurchases = new ArrayList<>();
                for (int i = 0; i < dayPurchases.size(); i++) {
                    System.out.println(i);
                    String str = String.valueOf(dayPurchases.get(i));
                    String title = str.substring(str.indexOf("title=") + 7, str.indexOf("date") - 3);
                    String price = str.substring(str.indexOf("price=") + 6, str.indexOf("}"));
                    System.out.println("====================");
                    System.out.println(title);
                    System.out.println(price);
                    System.out.println("====================");

                    String fullString = "$" + price + " " + title;


                    stringPurchases.add(fullString);

                }


                purchaseArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringPurchases);
                lv_purchaseList.setAdapter(purchaseArrayAdapter);


            }
        });

        bttn_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }


        });


        btn_observe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<PurchaseModel> dayPurchases = dataBaseHelper.getEverythingFromCurrentDay();
                List<String> stringPurchases = null;

                stringPurchases = new ArrayList<>();
                for (int i = 0; i < dayPurchases.size(); i++) {
                    System.out.println(i);
                    String s = String.valueOf(dayPurchases.get(i));
                    String title = s.substring(s.indexOf("title=") + 7, s.indexOf("date") - 3);
                    String price = s.substring(s.indexOf("price=") + 6, s.indexOf("}"));
                    System.out.println("====================");
                    System.out.println(title);
                    System.out.println(price);
                    System.out.println("====================");

                    String fullString = "$" + price + " " + title;


                    stringPurchases.add(fullString);

                }


                purchaseArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringPurchases);
                lv_purchaseList.setAdapter(purchaseArrayAdapter);


                //Toast.makeText( MainActivity.this, dayPurchases.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        lv_purchaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<PurchaseModel> dayPurchases = dataBaseHelper.getEverythingFromCurrentDay();



                System.out.println(position);


                //PurchaseModel clickedPurchase = (PurchaseModel) parent.getItemAtPosition(position);
                //PurchaseModel clickedPurchase = (PurchaseModel) parent.getItemAtPosition(position);

                try {
                    dataBaseHelper.deleteOne((PurchaseModel) dayPurchases.get(position));
                }
                catch (Exception e)
                {

                }
                List<String> stringPurchases = null;

                stringPurchases = new ArrayList<>();
                for (int i = 0; i < dayPurchases.size(); i++) {
                    System.out.println(i);
                    String s = String.valueOf(dayPurchases.get(i));
                    String title = s.substring(s.indexOf("title=") + 7, s.indexOf("date") - 3);
                    String price = s.substring(s.indexOf("price=") + 6, s.indexOf("}"));
                    System.out.println("====================");
                    System.out.println(title);
                    System.out.println(price);
                    System.out.println("====================");

                    String fullString = "$" + price + " " + title;


                    stringPurchases.add(fullString);

                }


                purchaseArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringPurchases);
                lv_purchaseList.setAdapter(purchaseArrayAdapter);
                tv_daily.setText("Daily Expenditure: $" + dataBaseHelper.getDailyExpense());

                tv_weekly.setText("Weekly Expenditure: $" + dataBaseHelper.getWeeklyExpense());


            }

        });


    }




    public void openDialog(){
        BudgetDialog budgetDialog = new BudgetDialog();
        budgetDialog.show(getSupportFragmentManager(), "Budget Dialog");
    }


    public void applyBudget(String budget)
    {
        int intArray[];
        intArray = computeBudget(budget);
        tv_dailyAmount.setText("Daily amount left $" + String.valueOf(intArray[0]));
        tv_weeklyAmount.setText("Weekly amount left $" + String.valueOf(intArray[1]));


    }
    public int[] computeBudget(String budget) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        System.out.println(budget);

        int weeklyAmountLeft = Integer.parseInt(budget) - dataBaseHelper.getWeeklyExpense();
        System.out.println(Integer.parseInt(budget) + " - " + dataBaseHelper.getDailyExpense());
        int dailyAmountLeft = (Integer.parseInt(budget) / 7) - dataBaseHelper.getDailyExpense();
        System.out.println((Integer.parseInt(budget) / 7) + " - " + (Integer.parseInt(budget) / 7));


        System.out.println("Weekly Left:" + String.valueOf(weeklyAmountLeft));
        System.out.println("Daily Left: "+ String.valueOf(dailyAmountLeft));

        //tv_dailyAmount.setText("Daily amount left $" + String.valueOf(dailyAmountLeft));
        //tv_weeklyAmount.setText("Weekly amount left $" + String.valueOf(weeklyAmountLeft));

        int[] intArray = new int[]{dailyAmountLeft, weeklyAmountLeft};
        return intArray;




    }



}