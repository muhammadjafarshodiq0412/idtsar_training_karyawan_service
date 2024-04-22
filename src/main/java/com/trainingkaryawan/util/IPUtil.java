package com.trainingkaryawan.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {

    public IPUtil() {
    }

    public static String getIPAddress() {
        try {
            // Get the local host address
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            // Handle the exception if the host address cannot be determined
            e.printStackTrace();
            return null;
        }
    }
}
