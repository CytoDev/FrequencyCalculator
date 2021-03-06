package io.cytodev.freqcalc.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import io.cytodev.freqcalc.R;
import io.cytodev.freqcalc.activities.MainActivity;

/**
 * io.cytodev.freqcalc.fragments "Frequency Calculator"
 * 2016/01/25 @ 12:47
 *
 * @author Roel Walraven <mail@cytodev.io>
 */
public class HertzFragment extends Fragment {
    private static final String TAG = HertzFragment.class.getSimpleName();

    private View hertz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "Called onCreateView");

        this.hertz = inflater.inflate(R.layout.layout_hertz, container, false);

        initUI();

        return hertz;
    }

    private void initUI() {
        final MainActivity mainActivity = (MainActivity) getActivity();
        final EditText     hz           = hertz.findViewById(R.id.freq_input_hertz);

        final TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String identifier = "";

                if(mainActivity != null && !mainActivity.stop) {
                    try {
                        if(s.hashCode() == hz.getText().hashCode()) {
                            identifier = "hz";

                            mainActivity.freqcalc.calculate(identifier, Double.parseDouble(hz.getText().toString()));
                        }
                    } catch(Exception e) {
                        mainActivity.freqcalc.calculate("hz", 0.000);
                    }

                    mainActivity.updateValues(identifier);
                }
            }
        };

        hz.addTextChangedListener(tw);
    }

}
