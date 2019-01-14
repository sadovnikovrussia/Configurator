package tech.sadovnikov.configurator.model.data;

import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public interface RemoteDevice {
    void getParameter(ParametersEntities parameterEntity, AnswerCallback answerCallback);

    interface AnswerCallback {
        void onAnswerOk(Parameter parameter);

        void onAnswerError(LogMessage message);
    }
}
