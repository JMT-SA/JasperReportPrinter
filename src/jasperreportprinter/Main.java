package jasperreportprinter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Map;
/**
 *
 * @author Luxolo Matoti
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws java.lang.NoClassDefFoundError {
        // TODO code application logic here

        try {
            String debug_mode = System.getenv("DEBUG");
            
            Map reportParams = JasperReportingComponent.getReportParams(args, false);
            String mode = (String)reportParams.get("MODE");
            if (debug_mode != null) {
                System.out.println("Jasper debug: Mode is " + mode);
            }

            if(mode!=null && mode.equals("GENERATE")) {
                JasperReportGenerator jrGenerator = new JasperReportGenerator();
                jrGenerator.generateReport(args, debug_mode != null);
            } else if(mode!=null && mode.equals("PRINT")) {
                JasperReportPrinter jrPrinter = new JasperReportPrinter();
                jrPrinter.printReport(args, debug_mode != null);
            } else {
                System.out.println("Jasper error: Please specify the MODE[GENERATE/PRINT]");
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Jasper error: " + e.getMessage());
        } catch (java.lang.NoClassDefFoundError e) {
            e.printStackTrace();
            System.out.println("Jasper error: Class not defined: " + e.getMessage());
        }
    }
}
