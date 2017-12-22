/*
 * Copyright (c)
 *
 * Date: 24/11/2017
 *
 * Author: Chun Gao & Mike Zhang
 *
 */

package com.datastax.support.Util;

import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chun Gao on 23/11/2017
 */

public final class Inspector {

    private static final Logger logger = LogManager.getLogger(Inspector.class);

    private static final String IP_PATTERN = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

    public static boolean foundWindowsOS() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    public static boolean foundIPAddress(String path) {
        Pattern pattern = Pattern.compile(IP_PATTERN);
        Matcher matcher = pattern.matcher(path);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    public static String getIPAddress(String path) {
        String ip = "";
        Pattern pattern = Pattern.compile(IP_PATTERN);
        Matcher matcher = pattern.matcher(path);
        while (matcher.find()) {
            ip = matcher.group(0);
        }
        return ip;
    }

    public static String getFileID (File file) {
        if (foundIPAddress(file.getAbsolutePath())) {
            return getIPAddress(file.getAbsolutePath());
        } else if (foundOpsCenter(file.getAbsolutePath())) {
            return StrFactory.OPSCENTERD;
        } else {
            return "Cannot find file ID information";
        }
    }

    public static boolean foundOpsCenter (String path) {
        if (path.contains(StrFactory.OPSCENTERD)) {
            return true;
        }
        return false;
    }

    public static String[] splitBySpace (String input) {
        return input.split("\\s+");
    }

    public static String[] splitByComma (String input) {
        return input.split(",");
    }

    public static String[] splitByColon(String input){

        return input.split(":");
    }

    public static String[] splitBySlash(String input){

        return input.split("/");
    }

    public static String epochtoDate(String input)
    {
        long epoch = Long.parseLong(input);
        Date generation = new Date(epoch * 1000L);
        return generation.toString();

    }

    public static String secToTime(int sec) {
        int seconds = sec % 60;
        int minutes = sec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            if( hours >= 24) {
                int days = hours / 24;
                return String.format("%d days %02d hours %02d mins %02d seconds", days,hours%24, minutes, seconds);
            }
            return String.format("%02d hours %02d mins %02d seconds", hours, minutes, seconds);
        }
        return String.format("%02d mins %02d seconds", minutes, seconds);
    }
}