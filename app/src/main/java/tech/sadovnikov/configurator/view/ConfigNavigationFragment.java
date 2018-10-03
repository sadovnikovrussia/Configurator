package tech.sadovnikov.configurator.view;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.sadovnikov.configurator.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigNavigationFragment extends Fragment {


    public ConfigNavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config_navigation, container, false);
    }

}
