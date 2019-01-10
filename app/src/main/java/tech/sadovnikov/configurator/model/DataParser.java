package tech.sadovnikov.configurator.model;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;

import static tech.sadovnikov.configurator.entities.OldConfiguration.ANSW_NUMBER;
import static tech.sadovnikov.configurator.entities.OldConfiguration.APN;
import static tech.sadovnikov.configurator.entities.OldConfiguration.CMD_NUMBER;
import static tech.sadovnikov.configurator.entities.OldConfiguration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.entities.OldConfiguration.LOGIN;
import static tech.sadovnikov.configurator.entities.OldConfiguration.PACKETS;
import static tech.sadovnikov.configurator.entities.OldConfiguration.PASSWORD;
import static tech.sadovnikov.configurator.entities.OldConfiguration.SERVER;
import static tech.sadovnikov.configurator.entities.OldConfiguration.SMS_CENTER;

class DataParser {
    private static final String TAG = "DataParser";

    String parseMessage(String message, String parameter) {
        // Log.d(TAG, "parseMessage: " + message);
        switch (parameter) {
            case FIRMWARE_VERSION:
                return parseVersion(message);
            case PACKETS:
                return parsePackets(message);
            case SMS_CENTER:
                return parseNumber(message);
            case CMD_NUMBER:
                return parseNumber(message);
            case ANSW_NUMBER:
                return parseNumber(message);
            case APN:
                //Log.d(TAG, "parseMessage() returned: " + s);
                return parseSim(message);
            case LOGIN:
                return parseSim(message);
            case PASSWORD:
                return parseSim(message);
            case SERVER:
                return parseServer(message);
            default:
                int ravnoIndex = message.indexOf("=");
                int endIndex = message.indexOf("\r\n", ravnoIndex);
                return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
        }

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

    @NonNull
    private String parseVersion(String message) {
        int index_firmware_version = message.indexOf("Firmware version");
        int endVersionIndex = message.indexOf("\r\n", index_firmware_version);
        return message.substring(index_firmware_version + 17, endVersionIndex);
    }

}