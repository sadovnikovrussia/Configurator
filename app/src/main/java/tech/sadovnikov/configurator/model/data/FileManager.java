package tech.sadovnikov.configurator.model.data;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.inject.Inject;

import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

/**
 * Класс, предназначенный для работы с файлом конфигурации (открытие, сохранение)
 */
public class FileManager {
    private static final String TAG = FileManager.class.getSimpleName();

    @Inject
    public FileManager() {
    }

    // TODO <Добавить эти проверки при чтении/записи файла>
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    // TODO <Отрефакторить код с учетом возвращаемых значений createFile() и обработать события>
    private File getCfgFile(String fileName) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Log.d(TAG, "getCfgFile: dir = " + dir.getAbsolutePath());
        File file = new File(dir, fileName + ".cfg");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Log.d(TAG, "getCfgFile: Создан файл, file dir = " + file.getAbsolutePath());
                } else {
                    Log.d(TAG, "getCfgFile: Данный файл уже существует и будет перезаписан, file dir = " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!file.isFile()) {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private File getLogFile(String fileName) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Log.d(TAG, "getCfgFile: dir = " + dir.getAbsolutePath());
        File file = new File(dir, fileName + ".txt");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Log.d(TAG, "getLogFile: Создан файл, file dir = " + file.getAbsolutePath());
                } else {
                    Log.d(TAG, "getLogFile: Данный файл уже существует и будет перезаписан, file dir = " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!file.isFile()) {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public void saveConfiguration(Configuration configuration, String fileName, SaveCfgCallback callback) {
        File file = getCfgFile(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            List<String> cmdListForSetOrSave = configuration.getCmdListForSaving();
            try {
                for (String cmd : cmdListForSetOrSave) {
                    outputStreamWriter.write(cmd + "\r\n");
                }
                callback.onSuccess(fileName);
            } catch (IOException e) {
                callback.onError(e);
                e.printStackTrace();
            }
            try {
                outputStreamWriter.close();
                fileOutputStream.close();
            } catch (IOException e) {
                callback.onError(e);
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            callback.onError(e);
            e.printStackTrace();
        }
    }

    // TODO <Доделать проверки валидности пути и тд. (.cfg?), добавить выброс исключений>
    public void openConfiguration(String path, OpenCfgCallback callback) {
        Log.d(TAG, "openConfiguration: ");
        FileReader fileReader;
        BufferedReader bufferedReader;
        Configuration configuration;
        File file;
        String cfgName;
        if (!path.startsWith("/storage") && path.contains("/storage")) {
            String newPath = path.substring(path.indexOf("/storage"));
            file = new File(newPath);
        } else {
            file = new File(path);
        }
        cfgName = file.getName();
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            configuration = new Configuration();
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    int indexEquals = line.indexOf("=");
                    if (indexEquals != -1) {
                        String value;
                        String name;
                        name = line.substring(0, indexEquals).trim().toUpperCase();
                        for (ParametersEntities parameterEntity : ParametersEntities.values()) {
                            if (parameterEntity.getName().equals(name)) {
                                value = line.substring(indexEquals + 1).trim();
                                if (value.length() != 0) {
                                    Parameter parameter = Parameter.of(parameterEntity, value);
                                    configuration.setParameter(parameter);
                                }
                                break;
                            }
                        }
                    }
                }
                callback.onSuccess(cfgName, configuration);
            } catch (IOException e) {
                callback.onError(cfgName, e);
                Log.e(TAG, "openConfiguration: Ошибка при чтении файла", e);
            }
        } catch (FileNotFoundException e) {
            callback.onError(cfgName, e);
            Log.e(TAG, "openConfiguration: Файл не найден", e);
        }
    }

    public void saveLog(final List<LogMessage> mainLogList, final String fileName, final SaveLogCallback callback) {
        File file = getLogFile(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            try {
                for (LogMessage logMessage : mainLogList) {
                    outputStreamWriter.write(logMessage.convertToOriginal());
                }
                callback.onSuccess(fileName);
            } catch (IOException e) {
                callback.onError(e);
                e.printStackTrace();
            }
            try {
                outputStreamWriter.close();
                fileOutputStream.close();
            } catch (IOException e) {
                callback.onError(e);
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            callback.onError(e);
            e.printStackTrace();
        }
    }


    public interface SaveCfgCallback {
        void onSuccess(String fileName);

        void onError(Exception e);
    }

    public interface OpenCfgCallback {
        void onSuccess(String cfgName, Configuration configuration);

        void onError(String cfgName, Exception e);
    }

    public interface SaveLogCallback {
        void onSuccess(String fileName);

        void onError(Exception e);
    }
}
