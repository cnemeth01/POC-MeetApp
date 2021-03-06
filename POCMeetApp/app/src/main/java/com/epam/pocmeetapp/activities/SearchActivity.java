package com.epam.pocmeetapp.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.epam.pocmeetapp.pojos.MeetUp;
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

    public static final int SEARCH_DATE_TO = 1;
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
    private String[] message;
    private List<MeetUp> myScoreList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

        updateSearchCategory();
    }

    private void initView() {
        searchResultList = (ListView) findViewById(R.id.listViewSearch);
        String simpleDateTo = "2015-12-24";
        String simpleDateFrom = "2010-12-24";
        try {
            toDate = sdf.parse(simpleDateTo);
            fromDate = sdf.parse(simpleDateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        results = new ArrayList<String>();
        simpleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        searchResultList.setAdapter(simpleAdapter);

        searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            int push = 0;

            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

//           This query find all Comments objects which are connected to a concrete MeetUp

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Comments");
                query.whereEqualTo("parent", myScoreList.get(position));
                query.findInBackground(new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                        message = new String[parseObjects.size()];
                        int i = 0;
                        for (ParseObject object : parseObjects) {
                            message[i] = (object.getString("comment"));
                            i++;
                        }

                        createDialogWithbButtons();
                    }
                });

            }
        });
        spinner = (Spinner) findViewById(R.id.spinnerSearchCategory);
        editTextSearchFieldOne = (EditText) findViewById(R.id.edittextSearch);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonDateFrom = (Button) findViewById(R.id.buttonDateFrom);
        buttonDateFrom.setText(simpleDateFrom);
        buttonDateTo = (Button) findViewById(R.id.buttonDateTo);
        buttonDateTo.setText(simpleDateTo);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchParseQuerys();
            }
        });
        buttonDateFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createDatePickerDialog(0);
            }
        });
        buttonDateTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createDatePickerDialog(SEARCH_DATE_TO);
            }

        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_categorys, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void createDialogWithbButtons() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(("Comments:"));
        ab.setItems(message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.content.DialogInterface.OnClickListener mylistener = new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DialogInterface.BUTTON_POSITIVE == which) {
                    dialog.dismiss();
                }

            }
        };
        ab.setPositiveButton("Ok", mylistener);
        ab.create().show();
    }

    private void createDatePickerDialog(final int serchtype) {

        int year = 2014;
        int month = 0;
        int day = 0;

        DatePickerDialog datepicker = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    String simpleDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    if (serchtype == SEARCH_DATE_TO) {
                        toDate = sdf.parse(simpleDate);
                        buttonDateTo.setText(simpleDate);

                    } else {
                        fromDate = sdf.parse(simpleDate);
                        buttonDateFrom.setText(simpleDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, day);

        datepicker.setTitle("Set from Date");
        datepicker.show();
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
        progress = ProgressDialog.show(SearchActivity.this, "Please wait ...", "Downloading ...", true);

        Log.d("SEARCH", "FROM" + fromDate.toString());
        Log.d("SEARCH", "TO" + toDate.toString());

        ParseQuery<MeetUp> query = ParseQuery.getQuery(MeetUp.class);

        if (editTextSearchFieldOne.getText().length() > 0) {
            Log.d("Filter :", "." + editTextSearchFieldOne.getText().toString() + ".");
            query.whereContains("Title", editTextSearchFieldOne.getText().toString().trim());
        }

        query.whereGreaterThan("Start", fromDate);
        query.whereLessThan("Start", toDate);

        myScoreList = new ArrayList<MeetUp>();

        query.findInBackground(new FindCallback<MeetUp>() {
            @Override
            public void done(List<MeetUp> scoreList, com.parse.ParseException e) {
                if (e == null) {
                    if (scoreList != null) {
                        Log.d("score", scoreList.size() + " results.");
                        myScoreList.addAll(scoreList);
                    } else {
                        Log.d("score", "No results!");
                    }
                    progress.dismiss();
                    simpleAdapter.clear();
                    System.out.println(scoreList.toString());
                    results.clear();
                    for (MeetUp parseObject : scoreList) {
                        results.add(parseObject.getObjectId());
                        simpleAdapter.add(parseObject.getMeetUpTitle());


                    }
                    simpleAdapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }

        });

    }


}
