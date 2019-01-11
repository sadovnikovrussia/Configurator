package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.util.ArrayList;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.old.OldConfiguration;
import tech.sadovnikov.configurator.old.OldParameter;

import static tech.sadovnikov.configurator.old.OldConfiguration.CURRENT_POS;
import static tech.sadovnikov.configurator.old.OldConfiguration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.old.OldConfiguration.PACKETS;
import static tech.sadovnikov.configurator.old.OldConfiguration.UPOWER;


public class RepositoryConfiguration implements Contract.RepositoryConfiguration {
    private static final String TAG = "RepositoryConfiguration";

    private OldConfiguration uiOldConfiguration;

    private OnRepositoryConfigurationEventsListener onRepositoryConfigurationEventsListener;

    public RepositoryConfiguration(OnRepositoryConfigurationEventsListener presenter) {
        onRepositoryConfigurationEventsListener = presenter;
        uiOldConfiguration = new OldConfiguration();
    }

    @Override
    public void setParameter(OldParameter oldParameter) {
        Log.i(TAG, "setParameter: ДО: " + uiOldConfiguration);
        Log.i(TAG, "setParameter: Устанавливаем " + oldParameter);
        uiOldConfiguration.setParameter(oldParameter);
        Log.i(TAG, "setParameter: ПОСЛЕ: " + uiOldConfiguration);
        onRepositoryConfigurationEventsListener.onSetParameter(oldParameter.getName(), oldParameter.getValue());
    }

    @Override
    public void setParameter(String name, String value) {
        OldParameter oldParameter = new OldParameter(name, value);
        Log.i(TAG, "setParameter: ДО: " + uiOldConfiguration);
        Log.i(TAG, "setParameter: Устанавливаем " + oldParameter);
        uiOldConfiguration.setParameter(oldParameter);
        Log.i(TAG, "setParameter: ПОСЛЕ: " + uiOldConfiguration);
        onRepositoryConfigurationEventsListener.onSetParameter(name, value);
    }

    @Override
    public void setParameterFromUi(String name, String value) {
        OldParameter oldParameter = new OldParameter(name, value);
        Log.i(TAG, "setParameter: ДО: " + uiOldConfiguration);
        uiOldConfiguration.setParameter(oldParameter);
        Log.i(TAG, "setParameterFromUi: Устанавливаем " + oldParameter);
        Log.i(TAG, "setParameter: ПОСЛЕ: " + uiOldConfiguration);
    }

    /**
     * Установливает в приложении кофнигурацию, считанную из файла *.cfg
     *
     * @param oldConfiguration - Конфигурация
     */
    @Override
    public void setUiOldConfiguration(OldConfiguration oldConfiguration) {
        Log.d(TAG, "setUiOldConfiguration: ");
        resetConfiguration();
        for (OldParameter oldParameter : oldConfiguration.getParametersList()) {
            setParameter(oldParameter);
        }
        Log.d(TAG, "setUiOldConfiguration: " + this.uiOldConfiguration);
    }

    private void resetConfiguration() {
        Log.d(TAG, "resetConfiguration: Ресетим");
        for (OldParameter oldParameter : uiOldConfiguration.getParametersList()) {
            String name = oldParameter.getName();
            if (!name.equals(FIRMWARE_VERSION) && !name.equals(UPOWER) && !name.equals(CURRENT_POS) && !name.equals(PACKETS)) {
                setParameter(new OldParameter(name));
            }
        }
    }

    @Override
    public OldConfiguration getConfigurationForSave() {
        OldConfiguration oldConfigurationForSetAndSave = uiOldConfiguration.getConfigurationForSave();
        Log.d(TAG, "getConfigurationForSave() returned: " + oldConfigurationForSetAndSave);
        return oldConfigurationForSetAndSave;
    }

    @Override
    public String getParameterValue(String name) {
        return uiOldConfiguration.getParameterValue(name);
    }

    @Override
    public ArrayList<String> getCommandListForReadConfiguration() {
        return uiOldConfiguration.getCommandListForReadConfiguration();
    }

    @Override
    public ArrayList<String> getCommandListForSetConfiguration() {
        return uiOldConfiguration.getCommandListForSetConfiguration();
    }


    // ---------------------------------------------------------------------------------------------
    public interface OnRepositoryConfigurationEventsListener {

        void onSetParameter(String name, String value);
    }
}
