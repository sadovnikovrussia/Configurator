package tech.sadovnikov.configurator.model;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;

import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

import static tech.sadovnikov.configurator.old.OldConfiguration.APN;
import static tech.sadovnikov.configurator.old.OldConfiguration.LOGIN;
import static tech.sadovnikov.configurator.old.OldConfiguration.PASSWORD;

public class CmdLogMessageAnalyzer {
    private static final String TAG = CmdLogMessageAnalyzer.class.getSimpleName();

    Parameter parseMessage(String messageBody) {
        ParametersEntities[] parametersEntities = ParametersEntities.values();

        for (ParametersEntities entity : parametersEntities) {
            int index = messageBody.indexOf(entity.getName());
            if (index != -1) {
                return getParameterFromMessage(messageBody, entity, index);
            }
        }

//        if (logType.equals(LOG_TYPE_CMD) & Integer.valueOf(logLevel) == LOG_LEVEL_1) {
//            Log.w(TAG, "analyzeLine: message = " + nativeMessage);
//            if (nativeMessage.contains(OK)) {
//                for (String parameter : PARAMETER_NAMES) {
//                    if (nativeMessage.toLowerCase().contains(parameter)) {
//                        String value = dataParser.parseMessage(nativeMessage, parameter);
//                        if (value != null) sendCommand(value, parameter);
//                    }
//                }
//            }
//        }
//
//        // Log.d(TAG, "parseMessage: " + message);
//        switch (parameter) {
//            case FIRMWARE_VERSION:
//                return parseVersion(message);
//            case PACKETS:
//                return parsePackets(message);
//            case SMS_CENTER:
//                return parseNumber(message);
//            case CMD_NUMBER:
//                return parseNumber(message);
//            case ANSW_NUMBER:
//                return parseNumber(message);
//            case APN:
//                //Log.d(TAG, "parseMessage() returned: " + s);
//                return parseSim(message);
//            case LOGIN:
//                return parseSim(message);
//            case PASSWORD:
//                return parseSim(message);
//            case SERVER:
//                return parseServer(message);
//            default:
//                int ravnoIndex = message.indexOf("=");
//                int endIndex = message.indexOf("\r\n", ravnoIndex);
//                return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
//        }

        return null;
    }

    private Parameter getParameterFromMessage(String messageBody, ParametersEntities entity, int startIndex) {
        String value;
        int endIndex;
        switch (entity) {
            case FIRMWARE_VERSION:
                endIndex = messageBody.indexOf("\r\n", startIndex);
                value = messageBody.substring(startIndex + entity.getName().length() + 1, endIndex);
                break;
            default:
                int equalsIndex = messageBody.indexOf("=");
                endIndex = messageBody.indexOf("\r\n", equalsIndex);
                value = messageBody.substring(equalsIndex + 1, endIndex).trim();
                break;
        }
        return Parameter.of(entity, value);
    }

    private Parameter parseVersion(String messageBody, int index) {
        return null;
    }

    private String parseServer(String message) {
        if (!message.contains(LOGIN) && !message.contains(APN) && !message.contains(PASSWORD)) {
            int ravnoIndex = message.indexOf("=");
            int endIndex = message.indexOf("\r\n", ravnoIndex);
            return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
        } else return null;
    }

    private String parseSim(String message) {
        int ravnoIndex = message.indexOf("=");
        int endIndex = message.indexOf("\r\n", ravnoIndex);
        String s = message.substring(ravnoIndex + 1, endIndex).trim();
        if (message.contains("?")) {
            return s;
        } else {
            switch (s) {
                case "\"\"":
                    return "\"Cellular operator defaults\"";
                case "''":
                    return "\"\"";
                default:
                    return "\"" + s + "\"";
            }
        }
    }

    private String parseNumber(String message) {
        int ravnoIndex = message.indexOf("=");
        int endIndex = message.indexOf("\r\n", ravnoIndex);
        if (message.contains("\"")) {
            return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "").replaceAll("\"", "");
        }
        return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
    }

    @NonNull
    private String parsePackets(String message) {
        //
        int adrIndex = message.indexOf("ADR:");
        int endAdrIndex = message.indexOf("\r\n", adrIndex);
        String adr = message.substring(adrIndex + 4, endAdrIndex);
        String[] adrNums = adr.split("-");
        int startAdr = Integer.parseInt(adrNums[0].substring(2), 16);
        int endAdr = Integer.parseInt(adrNums[1].substring(2), 16);
        int volAdr = endAdr - startAdr + 1;
        //
        int useIndex = message.indexOf("USE:");
        int endUseIndex = message.indexOf("\r\n", useIndex);
        String use = message.substring(useIndex + 4, endUseIndex);
        String[] useNums = use.split("-");
        int startUse = Integer.parseInt(useNums[0].substring(2), 16);
        int endUse = Integer.parseInt(useNums[1].substring(2), 16);
        int volUse = endUse - startUse;
        if (volUse < 0) {
            volUse = -volUse;
        }
        //
        double doublePercents = ((double) volUse / volAdr * 100);
        // Log.d(TAG, "parseMessage: doublePercents = " + doublePercents);
        String pattern = "##0.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String formattedPercents = decimalFormat.format(doublePercents).replace(",", ".");

        //
        int packetsIndex = message.lastIndexOf("PACKETS:");
        int endPacketsIndex = message.indexOf("\r\n", packetsIndex);
        String packets = message.substring(packetsIndex + 8, endPacketsIndex);
        //
        return packets + "," + formattedPercents;
    }

//    @NonNull
//    private Parameter parseVersion(LogMessage message) {
//        int index_firmware_version = message.getBody().indexOf("Firmware version");
//        int endVersionIndex = message.indexOf("\r\n", index_firmware_version);
//        return message.substring(index_firmware_version + 17, endVersionIndex);
//    }

}
