/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jasperreportprinter;

// import net.sf.jasperreports.engine.*;
// import net.sf.jasperreports.engine.export.*;

import java.util.*;
import org.apache.commons.collections.MapUtils;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.util.stream.Collectors;

/**
 *
 * @author Luxolo Matoti
 */
public class JasperReportingComponent {
    public static Map getReportParams(String[] args, Boolean debug_mode) {
        Map<String, Object> params = new HashMap();
        StringTokenizer tokeHeizer;
        String key="";
        String stringValue;
        int intValue;
        for(String arg : args) {
            tokeHeizer = new StringTokenizer(arg, "=");
            if(tokeHeizer.hasMoreTokens()) {
                key = tokeHeizer.nextToken();
            }
            if(tokeHeizer.hasMoreTokens()) {
                stringValue = tokeHeizer.nextToken();
/*
 * Extension:
 * If a parameter value contains '|parameter_type:(date/double/....)'
 * extract it and cast to type when putting into params 
 */
                StringTokenizer neizer = new StringTokenizer(stringValue, "|");
                stringValue = neizer.nextToken();
                
                String type = null;
                if(neizer.hasMoreTokens()) {                     
                    type = neizer.nextToken();                    
                }
    
                if(type != null && type.trim().equals("date")) {
                    if (debug_mode) {
                      System.out.println("arg : " + key + " | DATE = " + stringValue + ", type = " + type);
                    }
                    params.put(key, java.sql.Timestamp.valueOf(stringValue.trim()));
                } else if(type != null && type.trim().equals("boolean")) {
                    if (debug_mode) {
                      System.out.println("arg : " + key + " | BOOLEAN = " + stringValue + ", type = " + type);
                    }
                    params.put(key, Boolean.parseBoolean(stringValue.trim()));
                } else if(type != null && type.trim().equals("intarray")) {
                    if (debug_mode) {
                      System.out.println("arg : " + key + " | INTARRAY = " + stringValue + ", type = " + type);
                    }
                    String[] stringArray = stringValue.trim().split(",");
                    int[] intArray = new int[stringArray.length];
                    for (int i = 0; i < stringArray.length; i++) {
                       String numberAsString = stringArray[i];
                       intArray[i] = Integer.parseInt(numberAsString);
                    }
                    params.put(key, intArray);
                } else if(type != null && type.trim().equals("integer")) {
                    if (debug_mode) {
                      System.out.println("arg : " + key + " | INTEGER = " + stringValue + ", type = " + type);
                    }
                    params.put(key, Integer.parseInt(stringValue));
                } else if(type != null && type.trim().equals("string")) {
                    if (debug_mode) {
                      System.out.println("arg : " + key + " | STRING = " + stringValue + ", type = " + type);
                    }
                    params.put(key, stringValue.trim());
                } else {
                    try {
                        intValue = Integer.parseInt(stringValue);
                        if (debug_mode) {
                          System.out.println("arg : " + key + " | INTEGER = " + intValue);
                        }
                        params.put(key, intValue);
                    } catch(NumberFormatException nfe) {
                        if (debug_mode) {
                          System.out.println("arg : " + key + " | STRING = " + stringValue);
                        }
                        params.put(key, stringValue.trim());                    
                    }
                }
            }
        }
        if (debug_mode) {
            MapUtils.debugPrint(System.out, "Params sent to Jasper", params);
        }
        return params;
    }
}
