/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jasperreportprinter;


import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;

import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;

/**
 *
 * @author Luxolo
 */
public class JasperReportPrinter extends JmtJasperReportingComponent{
    public void printReport(String[] args) {
        try {
            String srcReportName = args[0] + "/" + args[1]+ ".jasper";
            Map reportParams = getReportParams(args);

    //        try {
                    Class driver = Class.forName("org.postgresql.Driver");
                    Connection conn = DriverManager.getConnection(args[3]);

        //          Fill the report passing in a JDBC connection object to use for executing the report internal SQL query
                JasperPrint print = JasperFillManager.fillReport(srcReportName, reportParams, conn);

                PrinterJob job = PrinterJob.getPrinterJob();
    //
                /* Create an array of PrintServices */
                PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
                int selectedService = -1;

                /* Scan found services to see if anyone suits our needs */
                for(int i = 0; i < services.length;i++){
    //                if(services[i].getName().toUpperCase().contains("\\\\ace\\jmt_printer")){
                    if(services[i].getName().equals(args[2])){
                    /*If the service is named as what we are querying we select it */
    //                    System.out.println("Printer Found: " + args[2]);
                    selectedService = i;
                    }
                }

                if(selectedService == -1) {
                    System.out.println("JMT Jasper error: Printer not found - " + args[2]);
                    System.exit(1);
                }
                job.setPrintService(services[selectedService]);
                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
    //            MediaSizeName mediaSizeName = MediaSize.;//findMedia(4,4,MediaPrintableArea.INCH);
    //            printRequestAttributeSet.add(MediaSizeName.ISO_A4);
                printRequestAttributeSet.add(MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4).getMediaSizeName());
                printRequestAttributeSet.add(new Copies(1));
                
                JRPrintServiceExporter exporter;
                exporter = new JRPrintServiceExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                
                /* We set the selected service and pass it as a paramenter */
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);

                exporter.exportReport();
        } catch (PrinterException ex) {
            System.out.println("JMT Jasper error: " + ex.getMessage());
        } catch (JRException ex) {
            System.out.println("JMT Jasper error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("JMT Jasper error: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("JMT Jasper error: " + ex.getMessage());
        }
    }
}
