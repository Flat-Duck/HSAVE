package ly.bithive.hsavemeandroid.util;


//import java.security.PublicKey;

public class COMMON {
    public static String STORAGE_URL = "http://192.168.43.18/saveme/public/";
    public static String BASE_URL = "http://192.168.43.18/saveme/public/api/";
    public static String CHECK_CONNECTION_URL = BASE_URL + "connection_check";
    public static String CHECK_UPDATE_URL = BASE_URL + "update_check";
    public static String GET_UPDATE_URL = BASE_URL + "clinks";
    public static String GET_DOCTOR_URL = BASE_URL + "doctors";
    public static String GET_CLINKS_URL = BASE_URL + "clinks";
    public static String GET_DEVICES_URL = BASE_URL + "devices";
    public static String GET_TESTS_URL = BASE_URL + "tests";
    public static String GET_SPECIALTIES_URL = BASE_URL + "specialties";

    public static String GET_ClINK_TESTS_URL =   "tests";
    public static String GET_ClINK_SPECIALTIES_URL =   "specialties";
    public static String GET_ClINK_APPOINT_URL =   "appointments";


    public final static int CL = 1, DR = 2, SP = 3, DV = 4, TS = 5, LO = 6,AP=7;

}
