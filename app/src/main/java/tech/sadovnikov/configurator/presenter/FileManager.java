package tech.sadovnikov.configurator.presenter;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import tech.sadovnikov.configurator.Contract;

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
        File dir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, "Конфигурация.cfg");
        if (!file.exists()) {
            Log.e(TAG, "file does not exist");
            try {
                file.createNewFile();
                Log.d(TAG, "getFile: Создан файл по умолчанию");
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

    void saveConfiguration(Contract.Configuration configuration) {
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

    public Contract.Configuration openConfiguration(Uri data) {
        File file = new File(String.valueOf(data));
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {

                }
            } catch (IOException e) {
                Log.e(TAG, "openConfiguration: Ошибка при чтении файла", e);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "openConfiguration: Файл не найден", e);
        }
        return null;
    }

    void analyze(String line) {
        if (!line.equals("\r\n")) {
            int index;
            if ((index = line.indexOf("")) != -1) {
            }
        }
    }
}
