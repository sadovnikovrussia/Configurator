package tech.sadovnikov.configurator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.view.adapter.ConfigTabsRvAdapter;


/**
 * Фрагмент для отображения конфигурации устройства
 */
public class ConfigurationFragment extends Fragment {
    private static final String TAG = "ConfigurationFragment";

    // UI
    RecyclerView rvConfigTabs;

    OnConfigurationFragmentInteractionListener onConfigurationFragmentInteractionListener;

    public ConfigurationFragment() {
        Log.v(TAG, "onConstructor");
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_configuration, container, false);
        rvConfigTabs = view.findViewById(R.id.rv_config_tabs);
        rvConfigTabs.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rvConfigTabs.setAdapter(new ConfigTabsRvAdapter(onConfigurationFragmentInteractionListener));
        return view;
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof OnConfigurationFragmentInteractionListener) {
            onConfigurationFragmentInteractionListener = (OnConfigurationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigurationFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
        onConfigurationFragmentInteractionListener.OnConfigurationFragmentStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onConfigurationFragmentInteractionListener = null;
    }
    // ---------------------------------------------------------------------------------------------

    public interface OnConfigurationFragmentInteractionListener {

        void onConfigTabsRvItemClick(String text);

        void OnConfigurationFragmentStart();

    }
}
