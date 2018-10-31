package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.util.ArrayList;

import tech.sadovnikov.configurator.Contract;

import static tech.sadovnikov.configurator.model.Configuration.BASE_POS;
import static tech.sadovnikov.configurator.model.Configuration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.model.Configuration.PACKETS;
import static tech.sadovnikov.configurator.model.Configuration.UPOWER;


public class RepositoryConfiguration implements Contract.RepositoryConfiguration {
    private static final String TAG = "RepositoryConfiguration";

    private Configuration uiConfiguration;

    private OnRepositoryConfigurationEventsListener onRepositoryConfigurationEventsListener;

    public RepositoryConfiguration(OnRepositoryConfigurationEventsListener presenter) {
        onRepositoryConfigurationEventsListener = presenter;
        uiConfiguration = new Configuration();
    }

    @Override
    public void setParameter(Parameter parameter) {
        Log.i(TAG, "setParameter: ДО: " + uiConfiguration);
        Log.i(TAG, "setParameter: Устанавливаем " + parameter);
        uiConfiguration.setParameter(parameter);
        Log.i(TAG, "setParameter: ПОСЛЕ: " + uiConfiguration);
        onRepositoryConfigurationEventsListener.onSetParameter(parameter);
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        Log.i(TAG, "setParameterWithoutCallback: ДО: " + uiConfiguration);
        Log.i(TAG, "setParameterWithoutCallback: Устанавливаем " + new Parameter(name, value));
        uiConfiguration.setParameter(name, value);
        Log.i(TAG, "setParameterWithoutCallback: ПОСЛЕ: " + uiConfiguration);
    }

    /**
     * Установливает в приложении кофнигурацию, считанную из файла *.cfg
     *
     * @param configuration - Конфигурация
     */
    @Override
    public void setUiConfiguration(Configuration configuration) {
        resetConfiguration();
        for (Parameter parameter : configuration.getParametersList()) {
            setParameter(parameter);
        }
        Log.d(TAG, "setUiConfiguration: " + this.uiConfiguration);
    }

    private void resetConfiguration() {
        Log.d(TAG, "resetConfiguration: Ресетим");
        for (Parameter parameter : uiConfiguration.getParametersList()) {
            // TODO <Добавить неустанавливаемые параметры>
            String name = parameter.getName();
            if (!name.equals(FIRMWARE_VERSION) && !name.equals(UPOWER) && !name.equals(BASE_POS) && !name.equals(PACKETS)) {
                setParameter(new Parameter(name));
            }
        }
    }

    @Override
    public Configuration getConfigurationForSetAndSave() {
        Log.d(TAG, "getConfigurationForSetAndSave: " + uiConfiguration);
        return uiConfiguration.getConfigurationForSetAndSave();
    }

    @Override
    public String getParameterValue(String name) {
        return uiConfiguration.getParameterValue(name);
    }

    @Override
    public ArrayList<String> getCommandListForReadConfiguration() {
        return uiConfiguration.getCommandListForReadConfiguration();
    }

    @Override
    public ArrayList<String> getCommandListForSetConfiguration() {
        return uiConfiguration.getCommandListForSetConfiguration();
    }

    public interface OnRepositoryConfigurationEventsListener {
        void onSetParameter(Parameter parameter);
    }
}
