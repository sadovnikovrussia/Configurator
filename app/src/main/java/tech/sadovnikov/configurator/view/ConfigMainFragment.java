package tech.sadovnikov.configurator.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import tech.sadovnikov.configurator.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigMainFragment extends Fragment {


    public ConfigMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config_main, container, false);
    }

}
