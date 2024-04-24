package com.trainingkaryawan.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IPUtil {

    public IPUtil() {
    }

    public static String[] getIPAddress() {
        String[] result = new String[2];
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (!address.isLoopbackAddress() && address.getHostAddress().matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                            result[0] = address.getHostAddress();
                            if (isPublicIPAddress(address.getHostAddress())) {
                                result[1] = "https://";
                            } else {
                                result[1] = "http://";
                            }
                            return result;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isPublicIPAddress(String ipAddress) {
        // Check if the IP address is a public IPv4 address range
        // This is just a simple example, actual validation may require more thorough checks
        return !ipAddress.startsWith("10.") &&
                !ipAddress.startsWith("192.168.") &&
                !ipAddress.equals("127.0.0.1"); // Exclude loopback address
    }
}
