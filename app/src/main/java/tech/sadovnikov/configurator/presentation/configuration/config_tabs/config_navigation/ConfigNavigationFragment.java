package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.old.OnParameterViewGroupClickListener;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgFragment;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class ConfigNavigationFragment extends BaseCfgFragment implements ConfigNavigationView {
    public static final String TAG = ConfigNavigationFragment.class.getSimpleName();

    // UI
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    @BindView(R.id.et_latitude)
    EditText etLatitude;
    @BindView(R.id.et_base_longitude)
    EditText etBaseLongitude;
    @BindView(R.id.et_base_latitude)
    EditText etBaseLatitude;
    @BindView(R.id.et_long_deviation)
    EditText etLongDeviation;
    @BindView(R.id.et_lat_deviation)
    EditText etLatDeviation;
    @BindView(R.id.et_hdop)
    EditText etHdop;
    @BindView(R.id.et_fix_delay)
    EditText etFixDelay;

    @BindView(R.id.spin_satellite_system)
    Spinner spinSatelliteSystem;

    @BindView(R.id.btn_request_current_pos)
    Button btnRequestCurrentPos;
    @BindView(R.id.btn_show_map_current_pos)
    Button btnShowMapCurrentPos;
    @BindView(R.id.btn_request_base_pos)
    Button btnRequestBasePos;
    @BindView(R.id.btn_show_map_base_pos)
    Button btnShowMapBasePos;
    @BindView(R.id.btn_rcv_coldstart)
    Button btnRcvColdStart;

    @BindView(R.id.cb_true_pos)
    CheckBox cbTruePos;


    @BindViews({R.id.ll_base_latitude, R.id.ll_base_longitude, R.id.ll_lat_deviation, R.id.ll_long_deviation, R.id.ll_hdop, R.id.ll_fix_delay, R.id.ll_satellite_system})
    List<ViewGroup> parameterViews;
    @BindViews({R.id.et_base_latitude, R.id.et_base_longitude, R.id.et_lat_deviation, R.id.et_long_deviation, R.id.et_hdop, R.id.et_fix_delay})
    List<EditText> etParameterValueViews;

    @InjectPresenter
    ConfigNavigationPresenter configNavigationPresenter;

    OnItemSelectedListener onSpinParameterListener;


    public static ConfigNavigationFragment newInstance() {
        Bundle args = new Bundle();
        ConfigNavigationFragment fragment = new ConfigNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_config_navigation, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        setUp(view);
        return view;
    }

    @Override
    public void setUp(View view) {
        View.OnFocusChangeListener onEtParameterChangedListener = (v, hasFocus) -> {
            if (!hasFocus)
                switch (v.getId()) {
                    case R.id.et_base_latitude:
                        if (etBaseLatitude.length() != 0 && etBaseLongitude.length() != 0)
                            presenter.onParameterChanged(ParametersEntities.BASE_POS, etBaseLatitude.getText() + "," + etBaseLongitude.getText());
                        break;
                    case R.id.et_base_longitude:
                        if (etBaseLatitude.length() != 0 && etBaseLongitude.length() != 0)
                            presenter.onParameterChanged(ParametersEntities.BASE_POS, etBaseLatitude.getText() + "," + etBaseLongitude.getText());
                        break;
                    case R.id.et_lat_deviation:
                        presenter.onParameterChanged(ParametersEntities.LAT_DEVIATION, etLatDeviation.getText().toString());
                        break;
                    case R.id.et_long_deviation:
                        presenter.onParameterChanged(ParametersEntities.LONG_DEVIATION, etLongDeviation.getText().toString());
                        break;
                    case R.id.et_hdop:
                        presenter.onParameterChanged(ParametersEntities.HDOP, etHdop.getText().toString());
                        break;
                    case R.id.et_fix_delay:
                        presenter.onParameterChanged(ParametersEntities.FIX_DELAY, etFixDelay.getText().toString());
                        break;
                }
        };
        onSpinParameterListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    presenter.onParameterChanged(ParametersEntities.SATELLITE_SYSTEM, String.valueOf(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        OnParameterViewGroupClickListener onParameterViewGroupClickListener = new OnParameterViewGroupClickListener(Objects.requireNonNull(getContext()));

        for (ViewGroup vg : parameterViews)
            vg.setOnClickListener(onParameterViewGroupClickListener);
        for (EditText editText : etParameterValueViews)
            editText.setOnFocusChangeListener(onEtParameterChangedListener);
        // todo Если не нажимать на cb, то этот параметр вообще не появится в конфигурации
        // а пользователь может думать, что параметр в состоянии (0) включен в конфигурацию
        cbTruePos.setOnClickListener(v -> presenter.onParameterChanged(ParametersEntities.TRUE_POS, cbTruePos.isChecked() ? "1" : "0"));
        btnRequestBasePos.setOnClickListener(v -> configNavigationPresenter.onRequestBasePos());
        btnShowMapBasePos.setOnClickListener(v -> configNavigationPresenter.onShowBasePosInMaps(etBaseLatitude.getText().toString(), etBaseLongitude.getText().toString()));
        btnRequestCurrentPos.setOnClickListener(v -> configNavigationPresenter.onRequestCurrentPos());
        btnShowMapCurrentPos.setOnClickListener(v -> configNavigationPresenter.onShowCurrentPosInMaps(etLatitude.getText().toString(), etLongitude.getText().toString()));
        spinSatelliteSystem.setOnItemSelectedListener(onSpinParameterListener);
        btnRcvColdStart.setOnClickListener(v -> configNavigationPresenter.onRcvColdStartSend());
    }

    @Override
    public void showConfiguration(Configuration configuration) {
        Parameter basePos = configuration.getParameter(ParametersEntities.BASE_POS);
        Parameter truePos = configuration.getParameter(ParametersEntities.TRUE_POS);
        Parameter currentPos = configuration.getParameter(ParametersEntities.CURRENT_POS);
        Parameter latDeviation = configuration.getParameter(ParametersEntities.LAT_DEVIATION);
        Parameter longDeviation = configuration.getParameter(ParametersEntities.LONG_DEVIATION);
        Parameter hdop = configuration.getParameter(ParametersEntities.HDOP);
        Parameter fixDelay = configuration.getParameter(ParametersEntities.FIX_DELAY);
        Parameter satelliteSystem = configuration.getParameter(ParametersEntities.SATELLITE_SYSTEM);
        if (basePos != null) {
            String[] latLong = basePos.getValue().split(",");
            etBaseLatitude.setText(latLong[0].trim());
            etBaseLongitude.setText(latLong[1].trim());
        }
        if (truePos != null) cbTruePos.setChecked(truePos.getValue().equals("1"));
        if (currentPos != null) {
            String[] latLong = currentPos.getValue().split(",");
            etLatitude.setText(latLong[0].trim());
            etLongitude.setText(latLong[1].trim());
        }
        if (latDeviation != null) etLatDeviation.setText(latDeviation.getValue());
        if (longDeviation != null) etLongDeviation.setText(longDeviation.getValue());
        if (hdop != null) etHdop.setText(hdop.getValue());
        if (fixDelay != null) etFixDelay.setText(fixDelay.getValue());
        if (satelliteSystem != null) {
            spinSatelliteSystem.setOnItemSelectedListener(null);
            spinSatelliteSystem.setSelection(Integer.valueOf(satelliteSystem.getValue()) + 1);
            spinSatelliteSystem.setOnItemSelectedListener(spinParameterListener);
        }
    }

    @Override
    public void showPositionInMaps(String latitude, String longitude) {
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        startActivity(mapIntent);
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        Log.v(TAG, "onDetach: ");
        super.onDetach();
    }
    // ---------------------------------------------------------------------------------------------


}
