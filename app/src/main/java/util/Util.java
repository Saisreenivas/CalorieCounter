package util;

import java.text.DecimalFormat;

/**
 * Created by Sai sreenivas on 1/30/2017.
 */

public class Util {

    public static String formatNumber(int value){
        DecimalFormat formatter = new DecimalFormat("##,##,###");
        String formatted = formatter.format(value);
        return formatted;
    }
}
