package tech.sadovnikov.configurator.model;

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

import tech.sadovnikov.configurator.model.data.configuration.Configuration;
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
    private File getFile(String fileName) {
        // Get the directory for the user's public download directory.
        // File dir = Environment.getExternalStorageDirectory();
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Log.d(TAG, "getFile: dir = " + dir.getAbsolutePath());
        File file = new File(dir, fileName + ".cfg");
        if (!file.exists()) {
            // LogList.d(TAG, "File does not exist");
            try {
                if (file.createNewFile()) {
                    Log.d(TAG, "getFile: Создан файл, file dir = " + file.getAbsolutePath());
                } else {
                    Log.d(TAG, "getFile: Данный файл уже существует и будет перезаписан, file dir = " + file.getAbsolutePath());
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
        File file = getFile(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            List<String> cmdListForSetOrSave = configuration.getCmdListForSetOrSave();
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
            Log.d(TAG, "openConfiguration: newPath = " + newPath);
            file = new File(newPath);
        } else {
            Log.d(TAG, "openConfiguration: path = " + path);
            file = new File(path);
        }
        cfgName = file.getName();
        Log.d(TAG, "openConfiguration: " + cfgName);
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            configuration = new Configuration();
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Log.d(TAG, "openConfiguration: 1" + line);
                    line = line.toUpperCase();
                    int indexEquals = line.indexOf("=");
                    if (indexEquals != -1) {
                        String value;
                        String name;
                        name = line.substring(0, indexEquals).trim().toUpperCase();
                        Log.d(TAG, "openConfiguration: 2" + name);
                        for (ParametersEntities parameterEntity : ParametersEntities.values()){
                            if (parameterEntity.getName().equals(name)){
                                value = line.substring(indexEquals + 1).trim();
                                if (value.length() != 0) {
                                    Parameter parameter = Parameter.of(parameterEntity, value);
                                    configuration.setParameter(parameter);
                                    Log.d(TAG, "openConfiguration: 4" + configuration);
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

    public interface SaveCfgCallback {
        void onSuccess(String fileName);

        void onError(Exception e);
    }

    public interface OpenCfgCallback {
        void onSuccess(String cfgName, Configuration configuration);

        void onError(String cfgName, Exception e);

    }
}
