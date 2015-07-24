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
            Map reportParams = JmtJasperReportingComponent.getReportParams(args);
            String mode = (String)reportParams.get("MODE");

            if(mode!=null && mode.equals("GENERATE")) {
                JasperReportGenerator jrGenerator = new JasperReportGenerator();
                jrGenerator.generateReport(args);
            } else if(mode!=null && mode.equals("PRINT")) {
                JasperReportPrinter jrPrinter = new JasperReportPrinter();
                jrPrinter.printReport(args);
            } else {
                System.out.println("JMT Jasper error: Please specify the MODE[GENERATE/PRINT]");
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("JMT Jasper error: " + e.getMessage());
        } catch (java.lang.NoClassDefFoundError e) {
            e.printStackTrace();
            System.out.println("JMT Jasper error: " + e.getMessage());
        }
    }
}
