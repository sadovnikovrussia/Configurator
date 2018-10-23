package tech.sadovnikov.configurator.model;

import android.util.Log;

import tech.sadovnikov.configurator.Contract;


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

    @Override
    public void setUiConfiguration(Configuration configuration) {
        for (Parameter parameter : configuration.getParametersArrayList()){
            if (uiConfiguration.contains(parameter)){
                uiConfiguration.setParameter(parameter);
            }
        }
        Log.d(TAG, "setUiConfiguration: " + this.uiConfiguration);
        onRepositoryConfigurationEventsListener.onSetUiConfiguration();
    }

    @Override
    public Configuration getConfigurationForSetAndSave() {
        Log.d(TAG, "getConfigurationForSetAndSave: " + uiConfiguration);
        return uiConfiguration.getConfigurationForSetAndSave();
    }

    @Override
    public String getSettingCommand(int index) {
        return uiConfiguration.getSettingCommand(index);
    }

    @Override
    public String getReadingCommand(int index) {
        return uiConfiguration.getReadingCommand(index);
    }

    @Override
    public String getParameterValue(String name) {
        return uiConfiguration.getParameterValue(name);
    }

    @Override
    public void setParameter(String name, String value) {
        uiConfiguration.setParameter(name, value);
        onRepositoryConfigurationEventsListener.onSetParameter(name,value);
    }

    @Override
    public void setParameter(Parameter parameter) {
        Log.d(TAG, "setParameter: ДО: " + uiConfiguration);
        uiConfiguration.setParameter(parameter);
        Log.d(TAG, "setParameter: ПОСЛЕ: " + uiConfiguration);
        // TODO <Нужна обработка?>
    }

    @Override
    public int getConfigurationSize() {
        return uiConfiguration.getSize();
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        uiConfiguration.setParameterWithoutCallback(name, value);
    }

    public interface OnRepositoryConfigurationEventsListener {
        void onSetParameter(String name, String value);

        void onSetUiConfiguration();
    }
}
