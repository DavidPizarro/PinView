package com.dpizarro.libraries.pinview;

import com.dpizarro.pinview.library.PinView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * User: David Pizarro de Jesús
 */
public class ConfigFragment extends Fragment {

    private PinView mPinView;
    private String[] titles;
    private String[] titlesAux;

    public static ConfigFragment newInstance() {
        return new ConfigFragment();
    }

    public ConfigFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.config_fragment, container, false);
        mPinView = (PinView) view.findViewById(R.id.pinView);
        setListeners(view);
        setInitTitles();

        mPinView.setOnCompleteListener(new PinView.OnCompleteListener() {
            @Override
            public void onComplete(boolean completed, String pinResults) {
                Toast.makeText(getActivity(), "Completed: " + completed + "\nValue: " + pinResults,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setInitTitles() {
        titles = getResources().getStringArray(R.array.titles);
        titlesAux = new String[4];
        System.arraycopy(titles, 0, titlesAux, 0, 4);
    }

    private void setListeners(View view) {

        CheckBox cbPassword = (CheckBox) view.findViewById(R.id.cbPassword);
        CheckBox cbDelete = (CheckBox) view.findViewById(R.id.cbDelete);
        CheckBox cbCustomBox = (CheckBox) view.findViewById(R.id.cbCustomBox);
        CheckBox cbKeyboard = (CheckBox) view.findViewById(R.id.cbKeyboard);

        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPinView.setMaskPassword(isChecked);
            }
        });

        cbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPinView.setDeleteOnClick(isChecked);
            }
        });

        cbCustomBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPinView.setNativePinBox(!isChecked);
                if (isChecked) {
                    mPinView.setCustomDrawablePinBox(R.drawable.pin_box);
                }
            }
        });

        cbKeyboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPinView.setKeyboardMandatory(isChecked);
            }
        });

        Button btRandomValues = (Button) view.findViewById(R.id.btRandomValues);
        btRandomValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Option with setters
                 */

                //Random split
                mPinView.setSplit(Utils.getRandomSplit(getActivity()));

                //Random colors
                mPinView.setColorTitles(Utils.getRandomColor());
                mPinView.setColorTextPinBoxes(Utils.getRandomColor());
                mPinView.setColorSplit(Utils.getRandomColor());

                //Random sizes
                mPinView.setSizeSplit(Utils.getRandomSize(getActivity()));
                mPinView.setTextSizePinBoxes(Utils.getRandomSize(getActivity()));
                mPinView.setTextSizeTitles(Utils.getRandomSize(getActivity()));

            }
        });

        SeekBar seekBar = (SeekBar) view.findViewById(R.id.sbNumberBoxes);
        final int step = getResources().getInteger(R.integer.step);
        int max = getResources().getInteger(R.integer.max);
        final int min = getResources().getInteger(R.integer.min);
        seekBar.setMax((max - min) / step);
        seekBar.setProgress(2);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                            boolean fromUser) {
                        if (fromUser) {
                            int value = min + (progress * step);
                            if (value != mPinView.getNumberPinBoxes()) {
                                mPinView.setPin(value);

                                titlesAux = new String[value];
                                System.arraycopy(titles, 0, titlesAux, 0, value);
                                mPinView.setTitles(titlesAux);
                            }
                        }
                    }
                }
        );
    }
}
