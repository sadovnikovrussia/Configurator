package tech.sadovnikov.configurator.presenter;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.model.Parameter;

/**
 * Класс, предназначенный для работы с файлом конфигурации (открытие, сохранение)
 */
public class FileManager {
    private static final String TAG = "FileManager";

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public File getFile() {
        // Get the directory for the user's public download directory.
        // File dir = Environment.getExternalStorageDirectory();
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Log.d(TAG, "getFile: dir = " + dir.getAbsolutePath());
        File file = new File(dir, "Configuration.cfg");
        if (!file.exists()) {
            Log.d(TAG, "File does not exist");
            try {
                file.createNewFile();
                Log.d(TAG, "getFile: Создан файл по умолчанию, dir = " + file.getAbsolutePath());
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

    void saveConfiguration(Configuration configuration) {
        File file = getFile();
        // Log.d(TAG, "saveConfiguration: " + String.valueOf(file.isDirectory()) + ", " +  String.valueOf(file.isFile()));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            try {
                for (int i = 0; i < configuration.getSize(); i++) {
                    outputStreamWriter.write(configuration.getSettingCommand(i) + "\r\n");
                }
                // Log.d(TAG, "saveConfiguration: ok");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStreamWriter.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    Configuration openConfiguration(String path) {
        Log.d(TAG, "openConfiguration: path = " + path);
        FileReader fileReader;
        BufferedReader bufferedReader;
        Configuration configuration = Configuration.getEmptyConfiguration();
        File file;
        if (!path.startsWith("/storage") && path.contains("/storage")) {
            String newPath = path.substring(path.indexOf("/storage"));
            Log.d(TAG, "openConfiguration: newPath = " + newPath);
            file = new File(newPath);
        } else {
            file = new File(path);
        }
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    int indexOfRavno = line.indexOf("=");
                    String value = "";
                    if (indexOfRavno != -1) {
                        value = line.substring(indexOfRavno + 1).replaceAll("\\s", "");
                    }
                    String name = line.substring(0, indexOfRavno).trim();
                    Parameter parameter = new Parameter(name, value);
                    // Log.d(TAG, "openConfiguration: Parameter name =" + name + ", " + "value = " + value);
                    Log.d(TAG, "openConfiguration: read Parameter: " + parameter);
                    // TODO <>
                    configuration.addParameter(parameter);
                }
            } catch (IOException e) {
                Log.e(TAG, "openConfiguration: Ошибка при чтении файла", e);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "openConfiguration: Файл не найден", e);
        }
        Log.d(TAG, "openConfiguration() returned: " + configuration);
        return configuration;
    }

    void analyze(String line) {
        if (!line.equals("\r\n")) {
            int index;
            if ((index = line.indexOf("")) != -1) {
            }
        }
    }
}
