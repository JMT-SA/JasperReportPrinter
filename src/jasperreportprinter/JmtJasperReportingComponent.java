/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jasperreportprinter;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Luxolo Matoti
 */
public class JmtJasperReportingComponent {
    public static Map getReportParams(String[] args) {
        Map params = new HashMap();
        StringTokenizer tokeHeizer;
        String key="",stringValue=null;
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
    
                if(type != null && type.toString().trim().equals("date")) {
//                    System.out.println("KHI : " + key + "     VALUE : " + stringValue + "            type = " + type);                    
                    params.put(key, java.sql.Timestamp.valueOf(stringValue.trim()));
                } else if(type != null && type.toString().trim().equals("boolean")) {
//                    System.out.println("KHI : " + key + "     VALUE : " + stringValue + "            type = " + type);                    
                    params.put(key, Boolean.parseBoolean(stringValue.trim()));
                } else if(type != null && type.toString().trim().equals("integer")) {
//                    System.out.println("KHI : " + key + "     VALUE : " + stringValue + "            type = " + type);                    
                    params.put(key, Integer.parseInt(stringValue));
                } else if(type != null && type.toString().trim().equals("string")) {
//                    System.out.println("KHI : " + key + "     VALUE : " + stringValue + "            type = " + type);                    
                    params.put(key, stringValue.trim());
                } else {
                    try {
                        intValue = Integer.parseInt(stringValue);
    //                    System.out.println("key = " + key + "  |  intValue = " + intValue);
                        params.put(key, intValue);
                    } catch(NumberFormatException nfe) {
//                        System.out.println("key = " + key + "  |  stringValue = " + stringValue);
                        params.put(key, stringValue.trim());                    
                    }
                }
            }
        }
        return params;
    }
}
