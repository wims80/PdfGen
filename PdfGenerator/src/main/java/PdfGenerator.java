import java.io.*;

import org.odftoolkit.odfdom.doc.*;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class PdfGenerator {
    public static void main(String[] args) {
        PdfGenerator pg = new PdfGenerator();
        pg.generateODT();
        pg.generatePdf();
    }

    public void generatePdf() {
        try {
            InputStream in = PdfGenerator.class.getClassLoader().getResourceAsStream("Generated_odf.odt");

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
                    in, TemplateEngineKind.Freemarker);
        } catch (Exception e) {
            System.out.println("Error! " + e.toString());
        }
    }

    public void generateODT() {
        try {
            OdfTextDocument otd = OdfTextDocument.newTextDocument();
            otd.addText("First line\n");
            otd.addText("Second line\n");
            otd.save(new File("Generated_odf1.odt"));
            otd.close();
        } catch (Exception e) {
            System.out.println("Error generating ODT: " + e.toString());
        }
    }
}