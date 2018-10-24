package tech.sadovnikov.configurator.model;

import android.util.Log;

import tech.sadovnikov.configurator.Contract;

import static tech.sadovnikov.configurator.model.Configuration.FIRMWARE_VERSION;


public class RepositoryConfiguration implements Contract.RepositoryConfiguration {
    private static final String TAG = "RepositoryConfiguration";

    private Configuration uiConfiguration;

    private OnRepositoryConfigurationEventsListener onRepositoryConfigurationEventsListener;

    public RepositoryConfiguration(OnRepositoryConfigurationEventsListener presenter) {
        onRepositoryConfigurationEventsListener = presenter;
        uiConfiguration = new Configuration();
    }

    @Override
    public Configuration getUiConfiguration() {
        Log.d(TAG, "getUiConfiguration: " + uiConfiguration);
        return uiConfiguration;
    }

    /**
     * Установливает в приложении кофнигурацию, считанную из файла *.cfg
     *
     * @param configuration
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
        for (Parameter parameter : uiConfiguration.getParametersList()){
            // TODO <Добавить неустанавливаемые параметры>
            if (!parameter.getName().equals(FIRMWARE_VERSION)){
                setParameter(new Parameter(parameter.getName()));
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
    public void setParameter(String name, String value) {
        uiConfiguration.setParameter(name, value);
        Log.d(TAG, "setParameter1: Устанавливаем " + new Parameter(name, value));
        Log.i(TAG, "setParameter1: " + uiConfiguration);
        onRepositoryConfigurationEventsListener.onSetParameter(name, value);
    }

    @Override
    public void setParameter(Parameter parameter) {
        Log.d(TAG, "setParameter2: ДО: " + uiConfiguration);
        Log.d(TAG, "setParameter2: Устанавливаем " + parameter);
        uiConfiguration.setParameter(parameter);
        Log.i(TAG, "setParameter2: ПОСЛЕ: " + uiConfiguration);
        onRepositoryConfigurationEventsListener.onSetParameter(parameter);
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        Log.d(TAG, "setParameterWithoutCallback: ДО: " + uiConfiguration);
        uiConfiguration.setParameter(name, value);
        Log.d(TAG, "setParameterWithoutCallback: ПОСЛЕ: " + uiConfiguration);
    }

    @Override
    public int getConfigurationSize() {
        return uiConfiguration.getSize();
    }


    public interface OnRepositoryConfigurationEventsListener {
        void onSetParameter(String name, String value);

        void onSetParameter(Parameter parameter);
    }
}
