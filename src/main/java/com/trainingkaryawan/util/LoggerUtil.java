package com.trainingkaryawan.util;

import static com.trainingkaryawan.constant.GeneralConstant.*;
import org.slf4j.MDC;

import java.util.Date;


public class LoggerUtil {

    private LoggerUtil() {
    }

    public static void setUniqueId(String trackingRef) {
        MDC.put(TRACKING_REF, trackingRef);
    }

    public static void removeUniqueId() {
        MDC.remove(TRACKING_REF);
    }

    public static void setUniqueIdByDate() {
        MDC.put(TRACKING_REF, DateUtil.dateToString(new Date(), YYYY_M_MDD_H_HMMSS_SSS));
    }

    public static String getTrackingRef() {
        return MDC.get(TRACKING_REF);
    }
}
