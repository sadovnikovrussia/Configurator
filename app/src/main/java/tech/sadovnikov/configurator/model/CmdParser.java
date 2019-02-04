package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.text.DecimalFormat;

import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

import static tech.sadovnikov.configurator.utils.ParametersEntities.APN;
import static tech.sadovnikov.configurator.utils.ParametersEntities.LOGIN;
import static tech.sadovnikov.configurator.utils.ParametersEntities.PASSWORD;
import static tech.sadovnikov.configurator.utils.ParametersEntities.SERVER;

class CmdParser {
    private static final String TAG = CmdParser.class.getSimpleName();

    static Parameter getParameterFromMessage(LogMessage cmdMessage) {
        ParametersEntities[] parametersEntities = ParametersEntities.values();
        String messageBody = cmdMessage.getBody();
        for (ParametersEntities entity : parametersEntities) {
            if ((entity == SERVER) && (messageBody.toUpperCase().contains(APN.getName()) || messageBody.toUpperCase().contains(LOGIN.getName()) || messageBody.toUpperCase().contains(PASSWORD.getName()))) {
                continue;
            }
            int index = messageBody.toUpperCase().indexOf(entity.getName());
            if (index != -1) {
                return parseMessageBody(messageBody, entity, index);
            }
        }
        return null;
    }

    private static Parameter parseMessageBody(String messageBody, ParametersEntities entity, int startIndex) {
        String value;
        int endIndex;
        switch (entity) {
            case FIRMWARE_VERSION:
                endIndex = messageBody.indexOf("\r\n", startIndex);
                value = messageBody.substring(startIndex + entity.getName().length() + 1, endIndex);
                break;
            case SERVER:
                value = parseServer(messageBody);
                break;
            case SMS_CENTER:
                value = parseNumber(messageBody);
                break;
            case CMD_NUMBER:
                value = parseNumber(messageBody);
                break;
            case ANSW_NUMBER:
                value = parseNumber(messageBody);
                break;
            case PACKETS:
                value = parsePackets(messageBody);
                break;
            case APN:
                value = parseSim(messageBody);
                break;
            case LOGIN:
                value = parseSim(messageBody);
                break;
            case PASSWORD:
                value = parseSim(messageBody);
                break;
            default:
                value = parseDefaultParameter(messageBody);
                break;
        }
        return Parameter.of(entity, value);
    }

    private static String parseServer(String messageBody) {
        Log.d(TAG, "parseServer: ");
        int indexEquals;
        int endIndex;
        String value;
        indexEquals = messageBody.indexOf("=");
        endIndex = messageBody.indexOf("\r\n", indexEquals);
        value = messageBody.substring(indexEquals + 1, endIndex).trim();
        return value;
    }

    private static String parseNumber(String messageBody) {
        int indexEquals;
        int endIndex;
        indexEquals = messageBody.indexOf("=");
        endIndex = messageBody.indexOf("\r\n", indexEquals);
        String value = messageBody.substring(indexEquals + 1, endIndex);
        if (messageBody.contains("\""))
            value = messageBody.substring(indexEquals + 1, endIndex).replaceAll("\"", "").trim();
        return value;
    }

    private static String parsePackets(String messageBody) {
        String value;
        int adrIndex = messageBody.indexOf("ADR:");
        int endAdrIndex = messageBody.indexOf("\r\n", adrIndex);
        String adr = messageBody.substring(adrIndex + 4, endAdrIndex);
        String[] adrNums = adr.split("-");
        long startAdr = Long.parseLong(adrNums[0].substring(2), 16);
        long endAdr = Long.parseLong(adrNums[1].substring(2), 16);
        long volAdr = endAdr - startAdr + 1;
        //
        int useIndex = messageBody.indexOf("USE:");
        int endUseIndex = messageBody.indexOf("\r\n", useIndex);
        String use = messageBody.substring(useIndex + 4, endUseIndex);
        String[] useNums = use.split("-");
        long startUse = Long.parseLong(useNums[0].substring(2), 16);
        long endUse = Long.parseLong(useNums[1].substring(2), 16);
        long volUse = endUse - startUse;
        if (volUse < 0) {
            volUse = -volUse;
        }
        //
        double doublePercents = ((double) volUse / volAdr * 100);
        String pattern = "##0.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String formattedPercents = decimalFormat.format(doublePercents).replace(",", ".");
        //
        int packetsIndex = messageBody.lastIndexOf("PACKETS:");
        int endPacketsIndex = messageBody.indexOf("\r\n", packetsIndex);
        String packets = messageBody.substring(packetsIndex + 8, endPacketsIndex);
        //
        value = packets + "," + formattedPercents;
        return value;
    }

    private static String parseDefaultParameter(String messageBody) {
        int equalsIndex = messageBody.indexOf("=");
        int endIndex = messageBody.indexOf("\r\n", equalsIndex);
        return messageBody.substring(equalsIndex + 1, endIndex).trim();
    }

    private static String parseSim(String message) {
        Log.d(TAG, "parseSim: ");
        int indexEquals = message.indexOf("=");
        int endIndex = message.indexOf("\r\n", indexEquals);
        String s = message.substring(indexEquals + 1, endIndex).trim();
        if (message.contains("?")) {
            return s;
        } else {
            switch (s) {
                case "\"\"":
                    return "\"Cellular operator defaults\"";
                default:
                    return "\"" + s + "\"";
            }
        }
    }

}
