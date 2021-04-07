package com.example.movieappschool.logic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieappschool.R;

public class CustomPicker {
    private View picker;
    private int value;
    private int min;
    private int max;
    private ImageView increaseView;
    private TextView valueView;
    private ImageView decreaseView;
    private PickerListener listener;

    public CustomPicker(View picker, int min, int max, PickerListener listener) {
        this.picker = picker;
        this.value = 0;
        this.min = min;
        this.max = max;
        this.listener = listener;
        setPicker();
    }

    public int getValue() {
        return value;
    }

    private void setPicker() {
        increaseView = picker.findViewById(R.id.picker_increase);
        increaseView.setOnClickListener(v -> {
            increaseValue();
        });

        valueView = picker.findViewById(R.id.picker_value);

        decreaseView = picker.findViewById(R.id.picker_decrease);
        decreaseView.setOnClickListener(v -> {
            decreaseValue();
        });
    }

    private void increaseValue() {
        if (value < max) {
            value++;
            updateValueView();
        }
    }

    private void decreaseValue() {
        if (value > min) {
            value--;
            updateValueView();
        }
    }

    private void updateValueView() {
        valueView.setText(String.valueOf(value));
        listener.onChange(value);
    }

    public interface PickerListener {
        void onChange(int value);
    }
}
