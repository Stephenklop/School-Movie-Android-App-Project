package com.example.movieappschool.logic;

import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.domain.Show;

import java.util.ArrayList;
import java.util.List;

public class ShowConfigurator {
    private final List<Show> shows;
    private final AppCompatActivity activity;
    private final ConfiguratorListener listener;
    private final RadioGroup datesRadioGroup;
    private final RadioGroup timesRadioGroup;
    private String selectedDate;
    private Show selectedShow;

    public ShowConfigurator(List<Show> shows, AppCompatActivity activity, ConfiguratorListener listener) {
        this.shows = shows;
        this.activity = activity;
        this.listener = listener;
        this.datesRadioGroup = activity.findViewById(R.id.order_datetime_date_picker);
        this.timesRadioGroup = activity.findViewById(R.id.order_datetime_time_picker);
        setup();
    }

    private void setup() {
        setShowDates();
        setShowTimes(selectedDate);
        setDatesRadioGroup();
        setTimesRadioGroup();
    }

    private void setShowDates() {
        List<String> loadedDates = new ArrayList<>();

        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);

            // Avoid duplicate dates showing up.
            if (loadedDates.stream().noneMatch(o -> o.equals(show.getDate()))) {
                RadioButton radioButton = new RadioButton(activity);
                radioButton.setButtonDrawable(new StateListDrawable());
                radioButton.setWidth(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, activity.getResources().getDisplayMetrics())));
                radioButton.setHeight(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, activity.getResources().getDisplayMetrics())));
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setBackgroundResource(R.drawable.radio_button_state);
                radioButton.setText(Formatter.dateToShortTextDate(show.getDate()));
                radioButton.setTextColor(activity.getResources().getColor(R.color.tint1, activity.getTheme()));
                radioButton.setPadding(15, 0, 15, 0);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                radioButton.setTag(show.getDate());

                datesRadioGroup.addView(radioButton);
                loadedDates.add(show.getDate());

                if (i == 0) {
                    radioButton.setChecked(true);
                    selectedDate = (String) radioButton.getTag();
                }
            }
        }
    }

    private void setShowTimes(String date) {
        timesRadioGroup.removeAllViews();

        boolean firstButtonChecked = false;

        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);

            if (show.getDate().equals(date)) {
                RadioButton radioButton = new RadioButton(activity);
                radioButton.setButtonDrawable(new StateListDrawable());
                radioButton.setWidth(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, activity.getResources().getDisplayMetrics())));
                radioButton.setHeight(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, activity.getResources().getDisplayMetrics())));
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setBackgroundResource(R.drawable.radio_button_state);
                radioButton.setText(show.getTime());
                radioButton.setTextColor(activity.getResources().getColor(R.color.tint1, activity.getTheme()));
                radioButton.setPadding(15, 0, 15, 0);
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                radioButton.setTag(show);

                timesRadioGroup.addView(radioButton);

                if (!firstButtonChecked) {
                    firstButtonChecked = true;
                    radioButton.setChecked(true);

                    selectedShow = show;
                    // Notify listener of show
                    //listener.onChange(show);
                }
            }
        }
    }

    private void setDatesRadioGroup() {
        datesRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton dateBtn = activity.findViewById(checkedId);
            selectedDate = (String) dateBtn.getTag();
            setShowTimes(selectedDate);
        });
    }

    private void setTimesRadioGroup() {
        timesRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton timeBtn = activity.findViewById(checkedId);
            selectedDate = timeBtn.getText().toString();
            selectedShow = (Show) timeBtn.getTag();
            listener.onChange(selectedShow);
        });
    }

    public Show getSelectedShow() {
        return selectedShow;
    }
}
