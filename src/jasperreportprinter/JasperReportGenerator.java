/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jasperreportprinter;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Luxolo
 */
public class JasperReportGenerator extends JmtJasperReportingComponent{
    public void generateReport(String[] args) {

        Map reportParams = getReportParams(args);

        String srcReportName = args[0] + "/" + args[1]+ ".jasper";
        String outFileName = (String)reportParams.get("OUT_FILE_NAME");
        String outFileType = (String)reportParams.get("OUT_FILE_TYPE");

        try {
                Class driver = Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(args[3]);
                
            //Fill the report passing in a JDBC connection object to use for executing the report internal SQL query
            JasperPrint print = JasperFillManager.fillReport(srcReportName, reportParams, conn);

            JRExporter exporter = null;
            if(outFileType!=null && outFileType.equals("PDF")) {
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName + ".pdf");
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            } else if(outFileType!=null && outFileType.equals("CSV")) {
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName + ".csv");
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            } else if(outFileType!=null && outFileType.equals("XLS")) {
                exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName + ".xls");
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            } else if(outFileType!=null && outFileType.equals("RTF")) {
                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName + ".rtf");
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            }


            // Export the PDF/SCV/EXCEL file
            exporter.exportReport();

        } catch (SQLException ex) {
            System.out.println("JMT Jasper error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("JMT Jasper error: " + ex.getMessage());
        } catch (JRException e) {
            System.out.println("JMT Jasper error: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("JMT Jasper error: " + e.getMessage());        
        }
    }
}
