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
import java.util.Calendar;
import java.util.Date;

import tech.sadovnikov.configurator.entities.Configuration;
import tech.sadovnikov.configurator.entities.Parameter;

import static tech.sadovnikov.configurator.entities.Configuration.PARAMETER_NAMES_LIST;

/**
 * Класс, предназначенный для работы с файлом конфигурации (открытие, сохранение)
 */
public class FileManager {
    private static final String TAG = "FileManager";

    FileManagerListener listener;

    public FileManager(FileManagerListener fileManagerListener) {
        listener = fileManagerListener;
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
        Date currentTime = Calendar.getInstance().getTime();
        String date = currentTime.toString();
        Log.d(TAG, "getFile: dir = " + dir.getAbsolutePath());
        File file = new File(dir, fileName + ".cfg");
        if (!file.exists()) {
            // Log.d(TAG, "File does not exist");
            try {
                if (!file.createNewFile()){
                    Log.d(TAG, "getFile: Создан файл по умолчанию, file dir = " + file.getAbsolutePath());
                } else {
                    Log.d(TAG, "getFile: Данный файл уже существует, file dir = " + file.getAbsolutePath());
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

    void saveConfiguration(Configuration configuration, String fileName) {
        File file = getFile(fileName);
        // Log.d(TAG, "saveConfiguration: " + String.valueOf(file.isDirectory()) + ", " +  String.valueOf(file.isFile()));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            try {
                for (int i = 0; i < configuration.getSize(); i++) {
                    outputStreamWriter.write(configuration.getSettingCommand(i) + "\r\n");
                }
                // Log.d(TAG, "saveConfiguration: ok");
                listener.onSaveConfigurationSuccess(fileName+".cfg");
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

    // TODO <Доделать проверки валидности пути и тд. (.cfg?), добавить выброс исключений>
    Configuration openConfiguration(String path) {
        Log.d(TAG, "openConfiguration: ");
        FileReader fileReader;
        BufferedReader bufferedReader;
        Configuration configuration = Configuration.getEmptyConfiguration();
        File file;
        if (!path.startsWith("/storage") && path.contains("/storage")) {
            String newPath = path.substring(path.indexOf("/storage"));
            Log.d(TAG, "openConfiguration: newPath = " + newPath);
            file = new File(newPath);
        } else {
            Log.d(TAG, "openConfiguration: path = " + path);
            file = new File(path);
        }
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    int indexOfRavno = line.indexOf("=");
                    String value;
                    String name;
                    if (indexOfRavno != -1) {
                        name = line.substring(0, indexOfRavno).trim().toLowerCase();
                        if (PARAMETER_NAMES_LIST.contains(name)) {
                            value = line.substring(indexOfRavno + 1).trim();
                            Parameter parameter = new Parameter(name, value);
                            Log.d(TAG, "openConfiguration: read Parameter: " + parameter);
                            configuration.addParameter(parameter);
                            // Log.d(TAG, "openConfiguration: Parameter name =" + name + ", " + "value = " + value);
                        }
                    }
                }

            } catch (IOException e) {
                Log.e(TAG, "openConfiguration: Ошибка при чтении файла", e);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "openConfiguration: Файл не найден", e);
        }
        Log.i(TAG, "openConfiguration() returned: " + configuration);
        return configuration;
    }

    interface FileManagerListener{

        void onSaveConfigurationSuccess(String fileName);
    }
}
