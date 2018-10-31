package tech.sadovnikov.configurator.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.HashMap;

import static tech.sadovnikov.configurator.model.Configuration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.model.Configuration.PACKETS;
import static tech.sadovnikov.configurator.model.Configuration.PARAMETER_NAMES;


/**
 * Класс, предназначенный для парсинга данных из лога
 */
class DataAnalyzer {
    private static final String TAG = "DataAnalyzer";

    private final static char LOG_SYMBOL = 0x7F;
    private final static int LOG_LEVEL_1 = 1;
    private static final String CMD = "CMD";
    private static final String OK = "OK";

    static final String PARAMETER_VALUE = "Data";
    static final String PARAMETER_NAME = "Parameter's name";

    static final int WHAT_COMMAND_DATA = 1;
    static final int WHAT_MAIN_LOG = 0;

    private Handler uiHandler;

    private String buffer = "";
    private String logType;

    private DataParser dataParser = new DataParser();

    DataAnalyzer(Handler handler) {
        uiHandler = handler;
    }

    void analyze(String line) {
        sendLogs(line);
        buffer = buffer + line + "\r\n";
        if (buffer.startsWith(String.valueOf(LOG_SYMBOL))) {
            int indexStartNewMessage = buffer.indexOf(LOG_SYMBOL, 1);
            if (indexStartNewMessage != -1) {
                try {
                    String message = buffer.substring(0, indexStartNewMessage);
                    buffer = buffer.substring(indexStartNewMessage);
                    String logLevel = message.substring(1, 2);
                    // TODO <Переделать определение logType>
                    logType = message.substring(2, 5);
                    if (logType.equals(CMD) & Integer.valueOf(logLevel) == LOG_LEVEL_1) {
                        Log.w(TAG, "analyze: message = " + message);
                        if (message.contains(OK)) {
                            for (String parameter : PARAMETER_NAMES) {
                                if (message.toLowerCase().contains(parameter)) {
                                    String value = dataParser.parseMessage(message, parameter);
                                    sendCommand(value, parameter);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.w(TAG, "analyze: " + logType, e);
                }
            }
        } else buffer = "";
    }

//    private String parseMessage(String message, String parameter) {
//        switch (parameter) {
//            case FIRMWARE_VERSION:
//                int index_firmware_version = message.indexOf("Firmware version");
//                int endVersionIndex = message.indexOf("\r\n", index_firmware_version);
//                return message.substring(index_firmware_version + 17, endVersionIndex);
//            case PACKETS:
//                //
//                int adrIndex = message.indexOf("ADR:");
//                int endAdrIndex = message.indexOf("\r\n", adrIndex);
//                String adr = message.substring(adrIndex + 4, endAdrIndex);
//                String[] adrNums = adr.split("-");
//                int startAdr = Integer.parseInt(adrNums[0].substring(2), 16);
//                int endAdr = Integer.parseInt(adrNums[1].substring(2), 16);
//                int volAdr = endAdr - startAdr + 1;
//                //
//                int useIndex = message.indexOf("USE:");
//                int endUseIndex = message.indexOf("\r\n", useIndex);
//                String use = message.substring(useIndex + 4, endUseIndex);
//                String[] useNums = use.split("-");
//                int startUse = Integer.parseInt(useNums[0].substring(2), 16);
//                int endUse = Integer.parseInt(useNums[1].substring(2), 16);
//                int volUse = endUse - startUse;
//                if (volUse < 0) {
//                    volUse = -volUse;
//                }
//                //
//                double doublePercents = ((double) volUse / volAdr * 100);
//                Log.d(TAG, "parseMessage: doublePercents = " + doublePercents);
//                String pattern = "##0.000";
//                DecimalFormat decimalFormat = new DecimalFormat(pattern);
//                String formattedPercents = decimalFormat.format(doublePercents).replace(",", ".");
//
//                //
//                int packetsIndex = message.lastIndexOf("PACKETS:");
//                int endPacketsIndex = message.indexOf("\r\n", packetsIndex);
//                String packets = message.substring(packetsIndex + 8, endPacketsIndex);
//                //
//                return packets + "," + formattedPercents;
//            default:
//                int ravnoIndex = message.indexOf("=");
//                int endIndex = message.indexOf("\r\n", ravnoIndex);
//                return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
//        }
////        if (message.contains("@version")) {
////            int index_firmware_version = message.indexOf("Firmware version");
////            int endIndex = message.indexOf("\r\n", index_firmware_version);
////            return message.substring(index_firmware_version + 17, endIndex);
////        } else if (message.contains("@packets?")) {
////            //
////            int adrIndex = message.indexOf("ADR:");
////            int endAdrIndex = message.indexOf("\r\n", adrIndex);
////            String adr = message.substring(adrIndex + 4, endAdrIndex);
////            String[] adrNums = adr.split("-");
////            int startAdr = Integer.parseInt(adrNums[0].substring(2), 16);
////            int endAdr = Integer.parseInt(adrNums[1].substring(2), 16);
////            int volAdr = endAdr - startAdr + 1;
////            //
////            int useIndex = message.indexOf("USE:");
////            int endUseIndex = message.indexOf("\r\n", useIndex);
////            String use = message.substring(useIndex + 4, endUseIndex);
////            String[] useNums = use.split("-");
////            int startUse = Integer.parseInt(useNums[0].substring(2), 16);
////            int endUse = Integer.parseInt(useNums[1].substring(2), 16);
////            int volUse = endUse - startUse;
////            if (volUse < 0) {
////                volUse = -volUse;
////            }
////            //
////            double doublePercents = ((double) volUse / volAdr * 100);
////            Log.d(TAG, "parseMessage: doublePercents = " + doublePercents);
////            String pattern = "##0.000";
////            DecimalFormat decimalFormat = new DecimalFormat(pattern);
////            String formattedPercents = decimalFormat.format(doublePercents).replace(",", ".");
////
////            //
////            int packetsIndex = message.lastIndexOf("PACKETS:");
////            int endPacketsIndex = message.indexOf("\r\n", packetsIndex);
////            String packets = message.substring(packetsIndex + 8, endPacketsIndex);
////            //
////            return packets + "," + formattedPercents;
////        } else {
////            int ravnoIndex = message.indexOf("=");
////            int endIndex = message.indexOf("\r\n", ravnoIndex);
////            return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
////        }
//    }

    private void sendCommand(String value, String parameter) {
        // Log.i(TAG, "sendCommand");
        Message msg = new Message();
        msg.what = WHAT_COMMAND_DATA;
        HashMap<String, Object> msgObj = new HashMap<>();
        msgObj.put(PARAMETER_VALUE, value);
        msgObj.put(PARAMETER_NAME, parameter);
        msg.obj = msgObj;
        uiHandler.sendMessage(msg);
    }

    private void sendLogs(String line) {
        Message msg = new Message();
        msg.what = WHAT_MAIN_LOG;
        msg.obj = line;
        uiHandler.sendMessage(msg);
    }

}
