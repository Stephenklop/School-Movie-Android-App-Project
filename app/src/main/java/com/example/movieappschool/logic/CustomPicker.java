package com.example.movieappschool.logic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieappschool.R;

public class CustomPicker {
    private final View picker;
    private final int min;
    private final int max;
    private final PickerListener listener;
    private int value;
    private TextView valueView;

    public CustomPicker(View picker, int min, int max, PickerListener listener) {
        this.picker = picker;
        this.value = 0;
        this.min = min;
        this.max = max;
        this.listener = listener;
        setup();
    }

    private void setup() {
        ImageView increaseView = picker.findViewById(R.id.picker_increase);
        increaseView.setOnClickListener(v -> increaseValue());

        valueView = picker.findViewById(R.id.picker_value);

        ImageView decreaseView = picker.findViewById(R.id.picker_decrease);
        decreaseView.setOnClickListener(v -> decreaseValue());
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
