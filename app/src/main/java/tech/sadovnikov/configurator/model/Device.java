package tech.sadovnikov.configurator.model;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public interface Device {
    void connect();

    void disconnect();

    PublishSubject<LogMessage> getLogs();

    void getParameter(ParametersEntities parameterEntity, AnswerCallback answerCallback);

    void setParameter(Parameter parameter, AnswerCallback answerCallback);


    interface AnswerCallback {
        void onAnswerOk(Parameter parameter);

        void onAnswerError(LogMessage message);
    }
}
