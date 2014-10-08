package com.epam.pocmeetapp.activities;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.epam.pocmeetapp.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.widget.AdapterView.OnItemSelectedListener;

public class SearchActivity extends Activity implements OnItemSelectedListener {

    private EditText editTextSearchFieldOne;
    private EditText editTextSearchFieldTwo;
    private Button buttonSearch;
    private Button buttonDateFrom;
    private Button buttonDateTo;
    private Spinner spinner;
    private String searchCategory;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Date fromDate = new Date(System.currentTimeMillis());
    private Date toDate = new Date(System.currentTimeMillis());
    private ListView searchResultList;
    private List<String> results;
    private ArrayAdapter<String> simpleAdapter;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

        updateSearchCategory();
    }

    private void initView() {
        searchResultList = (ListView) findViewById(R.id.listViewSearch);

        results = new ArrayList<String>();
        simpleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
        searchResultList.setAdapter(simpleAdapter);
        spinner = (Spinner) findViewById(R.id.spinnerSearchCategory);
        editTextSearchFieldOne = (EditText) findViewById(R.id.edittextSearch);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonDateFrom = (Button) findViewById(R.id.buttonDateFrom);
        buttonDateTo = (Button) findViewById(R.id.buttonDateTo);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextSearchFieldOne.getText().length() > 0) {


                }

                searchParseQuerys();
            }
        });
        buttonDateFrom.setOnClickListener(new View.OnClickListener() {

            int year = 2014;
            int month = 0;
            int day = 0;

            @Override
            public void onClick(View v) {

                DatePickerDialog datepicker = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            String simpleDate = year + "-" + monthOfYear + "-" + dayOfMonth;
                            fromDate = sdf.parse(simpleDate);
                            buttonDateFrom.setText(simpleDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);

                datepicker.setTitle("Set from Date");
                datepicker.show();
            }
        });
        buttonDateTo.setOnClickListener(new View.OnClickListener() {

            int year = 2014;
            int month = 0;
            int day = 0;

            @Override
            public void onClick(View v) {

                DatePickerDialog datepicker = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            String simpleDate = year + "-" + monthOfYear + "-" + dayOfMonth;
                            toDate = sdf.parse(simpleDate);
                            buttonDateTo.setText(simpleDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);

                datepicker.setTitle("Set from Date");
                datepicker.show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_categorys, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    private void updateSearchCategory() {
        searchCategory = (String) spinner.getSelectedItem();
        Log.d(" spinner selected item: ", searchCategory);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        updateSearchCategory();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void searchParseQuerys() {
        progress =ProgressDialog.show(SearchActivity.this, "Please wait ...", "Downloading ...", true);


        ParseQuery<ParseObject> query = ParseQuery.getQuery(searchCategory);
        query.whereLessThan("Start", fromDate);
        query.whereGreaterThan("Finish", toDate);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> scoreList,com.parse.ParseException e) {
                if (e == null) {
                    progress.dismiss();
                    for (ParseObject parseObject : scoreList) {
                        simpleAdapter.add(parseObject.getString("Theme"));

                    }
                   simpleAdapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }

        });

    }


}
