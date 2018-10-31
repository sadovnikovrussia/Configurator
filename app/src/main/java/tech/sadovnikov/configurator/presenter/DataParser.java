package tech.sadovnikov.configurator.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DecimalFormat;

import static tech.sadovnikov.configurator.model.Configuration.ANSW_NUMBER;
import static tech.sadovnikov.configurator.model.Configuration.CMD_NUMBER;
import static tech.sadovnikov.configurator.model.Configuration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.model.Configuration.PACKETS;
import static tech.sadovnikov.configurator.model.Configuration.SMS_CENTER;

public class DataParser {
    private static final String TAG = "DataParser";

    String parseMessage(String message, String parameter) {
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
            default:
                int ravnoIndex = message.indexOf("=");
                int endIndex = message.indexOf("\r\n", ravnoIndex);
                return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
        }

    }

    private String parseNumber(String message) {
        int ravnoIndex = message.indexOf("=");
        int endIndex = message.indexOf("\r\n", ravnoIndex);
        if (message.contains("\"")){
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
        Log.d(TAG, "parseMessage: doublePercents = " + doublePercents);
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
