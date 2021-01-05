package ly.bithive.hsavemeandroid.util;

import static ly.bithive.hsavemeandroid.util.COMMON.*;

public class Utils {
    private Utils() {
    }

    public static String getItemLink(int id){

        switch (id){
            case DR :return GET_DOCTOR_URL;
            case DV:return GET_DEVICES_URL;
            case CL:return GET_CLINKS_URL;
            case TS:return GET_TESTS_URL;
            case SP:return GET_SPECIALTIES_URL;
            //case 5:return GET_CLINKS_URL;break;
            //case 2: parseDevices(item);break;
            //  case 3: parseClinks(item);break;
            // case 4: parseDevices(item);break;
            // case 5: parseDevices(item);break;
            // case 3: parseData(response,1);break;
            // case 4: parseData(response,1);break;
            default:return GET_CLINKS_URL;
        }
    }
    public static String getClinkItemLink(int id){

        switch (id){
            case DR :return GET_DOCTOR_URL;
            case DV:return GET_DEVICES_URL;
            case CL:return GET_CLINKS_URL;
            case TS:return GET_TESTS_URL;
            case SP:return GET_SPECIALTIES_URL;
            //case 5:return GET_CLINKS_URL;break;
            //case 2: parseDevices(item);break;
            //  case 3: parseClinks(item);break;
            // case 4: parseDevices(item);break;
            // case 5: parseDevices(item);break;
            // case 3: parseData(response,1);break;
            // case 4: parseData(response,1);break;
            default:return GET_CLINKS_URL;
        }
    }
//
//    @SuppressLint("NewApi")
//    public static void setTabColor(TabHost tabHost) {
//        int max = tabHost.getTabWidget().getChildCount();
//        for (int i = 0; i < max; i++) {
//            View view = tabHost.getTabWidget().getChildAt(i);
//            TextView tv = (TextView) view.findViewById(android.R.id.title);
//            tv.setTextColor(view.isSelected() ? Color.parseColor("#ED8800") : Color.GRAY);
//            view.setBackgroundResource(R.color.black);
//        }
//    }

}
