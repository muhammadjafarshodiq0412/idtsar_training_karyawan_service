package com.trainingkaryawan.util;

public class DirectoryUtil {
    public DirectoryUtil() {
    }

    public static String getProjectDirectory() {
        // Get the current working directory of the application
        String projectDir = System.getProperty("user.dir");

        return projectDir;
    }
}
