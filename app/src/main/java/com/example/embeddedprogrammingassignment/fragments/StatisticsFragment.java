package com.example.embeddedprogrammingassignment.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.apiclient.Covid19.Covid19Data;
import com.example.embeddedprogrammingassignment.apiclient.Covid19.Covid19DataController;
import com.example.embeddedprogrammingassignment.apiclient.Covid19.Covid19DataRepository;
import com.example.embeddedprogrammingassignment.apiclient.Covid19.Covid19DataService;
import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersData;
import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersDataController;
import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersDataService;
import com.example.embeddedprogrammingassignment.fragments.statistics.DailyCases;
import com.example.embeddedprogrammingassignment.fragments.statistics.GraphMarkerView;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.PointValueLabel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Entry> total_cases_data = new ArrayList<>();
    private List<String> total_cases_label = new ArrayList<>();
    List<Integer> dailyCasesProgress;
    ProgressBar progMon, progTue, progWed, progThurs, progFri, progSat, progSun;
    TextView dailyCasesTv, dailyIncreaseTv;
    WorldometersData worldometersData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        progressBarHooker(view);

        //can implement yesterday today filter
        WorldometersDataService worldometersDataService = new WorldometersDataService();
        WorldometersDataController worldometersDataController = new WorldometersDataController(worldometersDataService);
        Call<WorldometersData> worldometerCall = worldometersDataController.findAll(true);

        worldometerCall.enqueue(new Callback<WorldometersData>() {
            @Override
            public void onResponse(Call<WorldometersData> call, Response<WorldometersData> response) {
                WorldometersData responseBody = response.body();

                Log.i("CovidDatafromApi","onResponse: status code " + response.code());

                Log.i("CovidDatafromApi","Response body: " + responseBody);

                worldometersData = responseBody;
                Log.i("CovidDatafromApi","worldometers data " + worldometersData.getTodayCases());
            }

            @Override
            public void onFailure(Call<WorldometersData> call, Throwable t) {
                Log.i("CovidDatafromApi","onFailure: " + t.getMessage());
            }
        });

        Covid19DataService covid19DataService = new Covid19DataService();
        Covid19DataController covid19DataController = new Covid19DataController(covid19DataService);
        Call<List<Covid19Data>> covid19Call = covid19DataController.findAll("2022-01-01T00:00:00Z",LocalDate.now()+"T00:00:00Z");

        covid19Call.enqueue(new Callback<List<Covid19Data>>() {
            @Override
            public void onResponse(Call<List<Covid19Data>> call, Response<List<Covid19Data>> response) {
                List<Covid19Data> responseBody = response.body();

                Log.i("CovidDatafromApi","onResponse: status code " + response.code());

                Log.i("CovidDatafromApi","Response body: " + responseBody.get(responseBody.size()-1));

                List<Covid19Data> temp = responseBody.subList(responseBody.size()-9,responseBody.size()-1);
                List<Integer> pastWeek = new ArrayList<>();
                temp.forEach(object -> {pastWeek.add(Integer.parseInt(object.getConfirmed()));
                    Log.i("CovidDatafromApi",object.getConfirmed());
                });
                test(pastWeek);
            }

            @Override
            public void onFailure(Call<List<Covid19Data>> call, Throwable t) {

            }
        });

        Log.i("CovidDatafromApi","worldometers data at statistic frag");




        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("stats/total_cases");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        PointValueLabel pvl = ds.getValue(PointValueLabel.class);
                        total_cases_data.add(new Entry(pvl.getxValue(), pvl.getyValue()));
                        total_cases_label.add(pvl.getxLabel());
                    }
                    showTotalCaseChart(view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void test(List<Integer> daily) {
        DailyCases dailyCases = new DailyCases();

        Log.d("test",daily.toString());

        for(int i=1; i < daily.size(); i++){
            int temp = daily.get(0);
            daily.set(i,daily.get(i) - daily.get(0));
            daily.set(0,daily.get(i) + temp);

            Log.d("test","i"+i+daily.get(i) + "daily" + daily.get(i-1));
        }

        daily=daily.subList(1,daily.size());
        dailyCases.setDailyValues(daily);
        dailyCasesProgress = dailyCases.setProgressValues();
        Log.d("Kotlin set daily cases @ Statistics Fragment", dailyCasesProgress.toString());

        setProgressBar(dailyCasesProgress, dailyCases);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void setProgressBar(List<Integer> list, DailyCases dailyCases) {
        int maxProgress = Collections.max(list);
        String day = LocalDate.now().getDayOfWeek().name();
        List<String> daysList = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        List<ProgressBar> progressBarList = Arrays.asList(progMon, progTue, progWed, progThurs, progFri, progSat, progSun);
        int index = daysList.indexOf(day);
        for (int i = 0; i < dailyCasesProgress.size(); i++) {
            if(i == index) {
                progressBarList.get(i).setProgressDrawable(getResources().getDrawable(R.drawable.progress_vertical_selected));
                int previousDay = 0;
                if(index==0)
                    previousDay = dailyCases.getDailyCasesList().get(list.size()-1);
                else
                    previousDay = dailyCases.getDailyCasesList().get(i-1);
                int change = calculateChange(previousDay, dailyCases.getDailyCasesList().get(i));
                if (change < 0)
                    dailyIncreaseTv.setText(change+"%↓");
                else
                    dailyIncreaseTv.setText(change+"%↑");
                dailyCasesTv.setText(dailyCases.getDailyCasesList().get(i).toString());
            }
            else
                progressBarList.get(i).setProgressDrawable(getResources().getDrawable(R.drawable.progress_vertical_unselected));
            progressBarList.get(i).setProgress(Math.round(list.get(i)));
            progressBarList.get(i).setMax(maxProgress);
            Log.d("Progress to int", ""+Math.round(list.get(i)));
        }
    }

    private int calculateChange(int previousDayCases, Integer todayCases) {
        int percentageChange = ((todayCases-previousDayCases) * 100)/previousDayCases;
        return percentageChange;
    }

    private void progressBarHooker(View view) {
        progMon = view.findViewById(R.id.progressStatMon);
        progTue = view.findViewById(R.id.progressStatTue);
        progWed = view.findViewById(R.id.progressStatWed);
        progThurs = view.findViewById(R.id.progressStatThur);
        progFri = view.findViewById(R.id.progressStatFri);
        progSat = view.findViewById(R.id.progressStatSat);
        progSun = view.findViewById(R.id.progressStatSun);
        dailyCasesTv = view.findViewById(R.id.tvStatsNewDailyCases);
        dailyIncreaseTv = view.findViewById(R.id.tvStatsDailyIncreasePercentage);
    }

    private void showTotalCaseChart(View view) {
        // Hooker
        LineChart lineChart = view.findViewById(R.id.statisticsLineChart);

        // Axis Line
        XAxis xAxis = lineChart.getXAxis();
        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();

        // XAxis customization
        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
        xAxis.setPosition(position);
        xAxis.setAxisLineWidth(1.5f);
        List<String> xAxisLabels = total_cases_label;

        Log.i("Database label", xAxisLabels.toString());
        Log.i("Database data", total_cases_data.toString());

        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisLabels));
        xAxis.setGranularity(1f);
        xAxis.setEnabled(true);
        xAxis.setSpaceMin(0.5f);
        xAxis.setSpaceMax(0.5f);
        lineChart.getDescription().setEnabled(false);
        rightAxis.setEnabled(false);
        rightAxis.setGranularity(1f);
        leftAxis.setGranularity(1f);

        // Hide grid lines
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        GraphMarkerView mv = new GraphMarkerView(requireContext(), R.layout.layout_graph_marker_view, xAxisLabels);
        lineChart.setMarker(mv);

        LineDataSet lineDataSet = new LineDataSet(total_cases_data, "Daily Cases");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(R.color.purple_title);
        lineDataSet.setLineWidth(2f);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}