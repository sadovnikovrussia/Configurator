package tech.sadovnikov.configurator.ui.configuration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;


public class ConfigurationFragment extends MvpFragment<ConfigurationMvp.View, ConfigurationPresenter> implements ConfigurationMvp.View {
    private static final String TAG = ConfigurationFragment.class.getSimpleName();

    // UI
    @BindView(R.id.rv_config_tabs)
    RecyclerView rvConfigTabs;

    FragmentComponent fragmentComponent;
    @Inject
    ConfigTabsRvAdapter adapter;
    @Inject
    LinearLayoutManager linearLayoutManager;


    OnConfigurationFragmentInteractionListener onConfigurationFragmentInteractionListener;

    public ConfigurationFragment() {
        Log.v(TAG, "onConstructor");
    }

    @NonNull
    @Override
    public ConfigurationPresenter createPresenter() {
        return new ConfigurationPresenter();
    }

    public static ConfigurationFragment newInstance() {
        Log.v(TAG, "newInstance: ");
        Bundle args = new Bundle();
        ConfigurationFragment fragment = new ConfigurationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_configuration, container, false);
        ButterKnife.bind(this, view);
        initDaggerAndInject();
        setUp();
        return view;
    }

    private void initDaggerAndInject() {
        fragmentComponent = DaggerFragmentComponent
                .builder()
                .fragmentModule(new FragmentModule(this))
                .build();
        fragmentComponent.injectConfigurationFragment(this);
    }

    private void setUp() {
        rvConfigTabs.setLayoutManager(linearLayoutManager);
        rvConfigTabs.setAdapter(adapter);
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onStart");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
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
    }
    // ---------------------------------------------------------------------------------------------

    public interface OnConfigurationFragmentInteractionListener {

        void onConfigTabsRvItemClick(String text);

        void OnConfigurationFragmentStart();

    }
}
