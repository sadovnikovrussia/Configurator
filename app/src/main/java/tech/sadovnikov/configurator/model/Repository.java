package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.Contract;


public class Repository implements Contract.Repository {
    private static final String TAG = "Repository";

    Configuration uiConfiguration;

    private OnRepositoryEventsListener onRepositoryEventsListener;

    public Repository(OnRepositoryEventsListener presenter) {
        onRepositoryEventsListener = presenter;
        uiConfiguration = new Configuration();
    }

    @Override
    public Contract.Configuration getConfigurationForSetAndSave() {
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
        onRepositoryEventsListener.onSetParameter(name,value);
    }

    @Override
    public int getSize() {
        return uiConfiguration.getSize();
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        uiConfiguration.setParameterWithoutCallback(name, value);
    }

    public interface OnRepositoryEventsListener {
        void onSetParameter(String name, String value);
    }
}
