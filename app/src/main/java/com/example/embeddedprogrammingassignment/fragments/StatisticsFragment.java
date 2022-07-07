package com.example.embeddedprogrammingassignment.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersData;
import com.example.embeddedprogrammingassignment.fragments.statistics.DailyCases;
import com.example.embeddedprogrammingassignment.fragments.statistics.GraphMarkerView;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.singleton.SingletonBundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatisticsFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Integer> dailyCasesProgress;
    ProgressBar progMon, progTue, progWed, progThurs, progFri, progSat, progSun;
    TextView dailyCasesTv, dailyIncreaseTv, dailyRecoveredCaseTv, dailyRecoveredRateTv, dailyDeathsTv, totalDeathsTv, totalMalaysiaTv, dailyMalaysiaTv, casePerPeopleTv;

    List<String> daysList = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");

    Bundle bundle;
    WorldometersData worldometersData;
    ArrayList<Integer> pastWeekCases = new ArrayList<>();
    static ArrayList<String> pastWeekDayLabels = new ArrayList<>();
    private ArrayList<Entry> total_cases_data = new ArrayList<>();
    private ArrayList<String> total_cases_label = new ArrayList<>();
    static Boolean isFirstTime=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        bundle = SingletonBundle.getBundle();
        progressBarHooker(view);

        Log.i("@StatisticFragment","here");
        worldometersData = Parcels.unwrap(bundle.getParcelable("worldometerData"));
        pastWeekCases = bundle.getIntegerArrayList("pastWeekCases");
        pastWeekDayLabels = bundle.getStringArrayList("pastWeekDayLabels");
        total_cases_data = bundle.getParcelableArrayList("total_cases_data");
        total_cases_label = bundle.getStringArrayList("total_cases_label");

        Log.i("@StatisticFragment worldometersData",worldometersData.toString());
        Log.i("@StatisticFragment pastWeekCases",pastWeekCases.toString());
        Log.i("@StatisticFragment pastWeekDayLabels",pastWeekDayLabels.toString());
        Log.i("@StatisticFragment total_cases_data",total_cases_data.toString());
        Log.i("@StatisticFragment total_cases_label",total_cases_label.toString());

        getCasesFromWorldometerAPI(worldometersData);
        getDailyCasesFromAPI(pastWeekCases, pastWeekDayLabels);
        showTotalCaseChart(view);

        // Inflate the layout for this fragment
        return view;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void getCasesFromWorldometerAPI(WorldometersData worldometersData) {
        dailyRecoveredCaseTv.setText(worldometersData.getTodayRecovered());
        float recoveryRate = (Float.parseFloat(worldometersData.getRecovered()) / Float.parseFloat(worldometersData.getCases())) * 100.00f;
        dailyRecoveredRateTv.setText(String.format("%.02f", recoveryRate) + "%");
        dailyDeathsTv.setText(("(+") + worldometersData.getTodayDeaths()+" today)");
        totalDeathsTv.setText(worldometersData.getDeaths());

        totalMalaysiaTv.setText(worldometersData.getCases());
        casePerPeopleTv.setText(worldometersData.getOneCasePerPeople());
    }

    private void getDailyCasesFromAPI(List<Integer> pastWeekCases, List<String> pastWeekLabels) {

        DailyCases dailyCases = new DailyCases();

        if(isFirstTime){
            for(int i=0; i<pastWeekLabels.size(); i++){
                pastWeekLabels.set(i, pastWeekLabels.get(i).substring(0, pastWeekLabels.get(i).length()-10));
            }
        }

        String day = LocalDate.parse(pastWeekLabels.get(pastWeekLabels.size()-1)).getDayOfWeek().toString();

        int dayIndex = daysList.indexOf(day);

        if(isFirstTime){
            for(int i=0; i < pastWeekCases.size()-1; i++) {
                pastWeekCases.set(i, pastWeekCases.get(i+1) - pastWeekCases.get(i));
            }
        }

        pastWeekCases = pastWeekCases.subList(0,pastWeekCases.size()-1);

        int tempDayIndex = dayIndex;
        int tempNumIndex = pastWeekCases.size()-1;
        boolean inflated = false;
        int[] orderedDailyCases = new int[7];
        while(!inflated) {
            orderedDailyCases[tempDayIndex] = pastWeekCases.get(tempNumIndex);
            tempDayIndex -= 1;
            tempNumIndex -= 1;
            if(tempDayIndex==-1)
                tempDayIndex=6;
            if(tempDayIndex==dayIndex)
                inflated = true;
        }

        pastWeekCases = new ArrayList<Integer>();
        for (int i : orderedDailyCases)
            pastWeekCases.add(i);

        dailyCases.setDailyValues(pastWeekCases);
        dailyCasesProgress = dailyCases.setProgressValues();

        setProgressBar(dailyCasesProgress, dailyCases, day);

        isFirstTime=false;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void setProgressBar(List<Integer> list, DailyCases dailyCases, String day) {
        int maxProgress = Collections.max(list);
        List<ProgressBar> progressBarList = Arrays.asList(progMon, progTue, progWed, progThurs, progFri, progSat, progSun);
        int dayIndex = daysList.indexOf(day);
        for (int i = 0; i < dailyCasesProgress.size(); i++) {
            if(i == dayIndex) {
                progressBarList.get(i).setProgressDrawable(getResources().getDrawable(R.drawable.progress_vertical_selected));
                int previousDay = 0;
                if(dayIndex==0)
                    previousDay = dailyCases.getDailyCasesList().get(list.size()-1);
                else
                    previousDay = dailyCases.getDailyCasesList().get(i-1);
                int change = calculateChange(previousDay, dailyCases.getDailyCasesList().get(i));
                if (change < 0)
                    dailyIncreaseTv.setText(change+"%↓");
                else
                    dailyIncreaseTv.setText(change+"%↑");
                dailyCasesTv.setText(dailyCases.getDailyCasesList().get(i).toString());
                dailyMalaysiaTv.setText(dailyCases.getDailyCasesList().get(i).toString());
            }
            else
                progressBarList.get(i).setProgressDrawable(getResources().getDrawable(R.drawable.progress_vertical_unselected));
            progressBarList.get(i).setProgress(Math.round(list.get(i)));
            progressBarList.get(i).setMax(maxProgress);
        }
    }

    private int calculateChange(int previousDayCases, Integer todayCases) {
        return ((todayCases-previousDayCases) * 100)/previousDayCases;
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
        dailyRecoveredCaseTv = view.findViewById(R.id.tvStatsTodayRecovered);
        dailyRecoveredRateTv = view.findViewById(R.id.tvStatsRecoveryRate);
        dailyDeathsTv = view.findViewById(R.id.tvStatsDeathToday);
        totalDeathsTv = view.findViewById(R.id.tvStatsDeathTotal);
        totalMalaysiaTv = view.findViewById(R.id.tvStatsTotalMalaysia);
        dailyMalaysiaTv = view.findViewById(R.id.tvStatsDailyMalaysia);
        casePerPeopleTv = view.findViewById(R.id.tvStatsCasePerPeople);
    }

    private void showTotalCaseChart(View view) {
        // Hooker
        LineChart lineChart = view.findViewById(R.id.statisticsLineChart);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        LineDataSet lineDataSet = new LineDataSet(total_cases_data, "Daily Cases");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(R.color.purple_title);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setValueTextSize(10f);

        iLineDataSets.add(lineDataSet);

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
        xAxis.setEnabled(false);
        leftAxis.setEnabled(false);
        xAxis.setSpaceMin(6f);
        xAxis.setSpaceMax(6f);
        xAxis.setLabelCount(total_cases_label.size());
        xAxis.setCenterAxisLabels(true);
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



        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}