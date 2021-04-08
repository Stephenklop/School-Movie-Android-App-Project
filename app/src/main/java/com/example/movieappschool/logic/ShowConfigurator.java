package com.example.movieappschool.logic;

import android.graphics.drawable.StateListDrawable;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Show;

import java.util.ArrayList;
import java.util.List;

public class ShowConfigurator {
    private AppCompatActivity activityView;
    private List<Show> shows;
    private ConfiguratorListener configuratorListener;
    private RadioGroup dateRadioGroup;
    private RadioGroup timeRadioGroup;
    private String selectedDate;
    private String selectedTime;

    public ShowConfigurator(AppCompatActivity activityView, List<Show> shows, ConfiguratorListener configuratorListener) {
        this.activityView = activityView;
        this.shows = shows;
        this.configuratorListener = configuratorListener;
        dateRadioGroup = activityView.findViewById(R.id.order_datetime_date_picker);
        timeRadioGroup = activityView.findViewById(R.id.order_datetime_time_picker);
        if (!shows.isEmpty()) {
            selectedDate = Formatter.dateToShortTextDate(shows.get(0).getDate());
            selectedTime = shows.get(0).getTime();
        }
    }

    public void setUpConfigurator() {
        showDateOptions();
        showTimeOptions(selectedDate);
        addSetOnClickListener();
        //configuratorListener.onChange(findShow(selectedDate, selectedTime));
    }

    private void showDateOptions() {
        List<String> loadedDates = new ArrayList<>();

        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);

            // Avoid double dates showing up
            if (loadedDates.stream().noneMatch(o -> o.equals(show.getDate()))) {
                RadioButton radioButton = new RadioButton(activityView);
                radioButton.setButtonDrawable(new StateListDrawable());
                radioButton.setWidth(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, activityView.getResources().getDisplayMetrics())));
                radioButton.setHeight(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, activityView.getResources().getDisplayMetrics())));
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setBackgroundResource(R.drawable.radio_button_state);
                radioButton.setText(Formatter.dateToShortTextDate(show.getDate()));
                radioButton.setTextColor(activityView.getResources().getColor(R.color.tint1, activityView.getTheme()));
                radioButton.setPadding(15, 0, 15, 0);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                dateRadioGroup.addView(radioButton);
                loadedDates.add(show.getDate());

                if (i == 0) {
                    radioButton.setChecked(true);
                }
            }
        }
    }

    private void showTimeOptions(String date) {
        timeRadioGroup.removeAllViews();

        boolean firstButtonChecked = false;

        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);

            if (Formatter.dateToShortTextDate(show.getDate()).equals(date)) {
                RadioButton radioButton = new RadioButton(activityView);
                radioButton.setButtonDrawable(new StateListDrawable());
                radioButton.setWidth(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, activityView.getResources().getDisplayMetrics())));
                radioButton.setHeight(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, activityView.getResources().getDisplayMetrics())));
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setBackgroundResource(R.drawable.radio_button_state);
                radioButton.setText(show.getTime());
                radioButton.setTextColor(activityView.getResources().getColor(R.color.tint1, activityView.getTheme()));
                radioButton.setPadding(15, 0, 15, 0);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                timeRadioGroup.addView(radioButton);

                if (!firstButtonChecked) {
                    firstButtonChecked = true;
                    radioButton.setChecked(true);
                }
            }
        }
    }

    private Show findShow(String date, String time) {
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);

            if (Formatter.dateToShortTextDate(show.getDate()).equals(date) && show.getTime().equals(time)) {
                return show;
            }
        }

        return null;
    }

    private void addSetOnClickListener() {
        dateRadioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton dateBtn = (RadioButton) activityView.findViewById(checkedId);
            selectedDate = dateBtn.getText().toString();
            Log.d("SELECTED DATE", selectedDate);
            showTimeOptions(selectedDate);
            configuratorListener.onChange(findShow(selectedDate, selectedTime));
        });

        timeRadioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton timeBtn = (RadioButton) activityView.findViewById(checkedId);
            selectedTime = timeBtn.getText().toString();
            Log.d("SELECTED TIME", selectedTime);
            configuratorListener.onChange(findShow(selectedDate, selectedTime));
        });
    }

    public interface ConfiguratorListener {
        void onChange(Show show);
    }
}
