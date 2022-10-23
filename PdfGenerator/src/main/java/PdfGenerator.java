import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.ITemplateEngine;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.velocity.internal.VelocityTemplateEngine;


public class PdfGenerator {
  public static void main(String[] args) {
    PdfGenerator pg = new PdfGenerator();
    //pg.generatePdf(args[0]);
    pg.convert2();
  }

  public void generatePdf(String fileName) {
    System.out.println("DEBUG: IN generateSpecPdf2(): START");
    try {

      InputStream in = new FileInputStream(fileName);
      System.out.println("DEBUG: In generateSpecPdf2(): AFTER InputStream");
      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, "ReportId", TemplateEngineKind.Freemarker);
      FieldsMetadata metadata = report.createFieldsMetadata();
      metadata.load("tilbud", Tilbud.class, true);

      System.out.println("DEBUG: Setting context");
      IContext context = report.createContext();
      context.put("calculatorUseDate", java.time.LocalDate.now().format(DateTimeFormatter
          .ofLocalizedDate(FormatStyle.LONG)));

      BilInput bi = new BilInput();
      bi.setMellomnavn("Wim");
      bi.setEtternavn("Sjøholm");
      bi.setFoedselsnr("27/05/80");
      bi.setPostnr("0482");

      bi.setRegistreringsnummer("EC58238");
      bi.setTyverialarm("false");
      bi.setGjenfinning("false");

      bi.setLeasing("false");
      bi.setForsteBil("false");
      bi.setBonus("70:fire_aar");
      bi.setDekning("kasko");
      bi.setKjoerelengde("12000");
      bi.setAlderYngsteFoerer("21");
      bi.setPantIBil("false");
      bi.setAntallBilskader("0");
      bi.setParkeringsforhold("egen_laast_garasje");
      bi.setDagensSelskap("ingen");

      context.putMap(convertObjectToMap(bi));

      List<Tilbud> tilbud = new ArrayList<Tilbud>();
      tilbud.add(new Tilbud("Utrygg", "Bilforsikring", "Info 1", "5000", "12000", "1337"));
      tilbud.add(new Tilbud("Else", "Bil", "Info 2", "5000", "12000", "420"));
      tilbud.add(new Tilbud("SlukketBrann", "Bil", "Info 3", "5000", "12000", "6969"));
      //context.putMap(convertObjectToMap(tilbud));
      context.put("tilbud", tilbud);

      System.out.println("DEBUG: Setting options");
      Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);

      System.out.println("DEBUG: Generating PDF");
      FileOutputStream fos = new FileOutputStream("document.pdf");
      report.convert(context, options, fos);
      in.close();
      fos.close();


      System.out.println("DEBUG: PDF generated!");

    } catch (Exception e) {
      System.out.println("Error! " + e.toString());
    }
  }

  public void convert2() {
      try {
          //ITemplateEngine templateEngine = TemplateEngineKind.Velocity;
          InputStream in = new FileInputStream(new File("bilforsikring.odt"));
          IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

// 2) Create Java model context
          IContext context = report.createContext();
          context.put("name", "world");

        context.put("calculatorUseDate", java.time.LocalDate.now().format(DateTimeFormatter
            .ofLocalizedDate(FormatStyle.LONG)));

        BilInput bi = new BilInput();
        bi.setMellomnavn("Wim");
        bi.setEtternavn("Sjøholm");
        bi.setFoedselsnr("27/05/80");
        bi.setPostnr("0482");

        bi.setRegistreringsnummer("EC58238");
        bi.setTyverialarm("false");
        bi.setGjenfinning("false");

        bi.setLeasing("false");
        bi.setForsteBil("false");
        bi.setBonus("70:fire_aar");
        bi.setDekning("kasko");
        bi.setKjoerelengde("12000");
        bi.setAlderYngsteFoerer("21");
        bi.setPantIBil("false");
        bi.setAntallBilskader("0");
        bi.setParkeringsforhold("egen_laast_garasje");
        bi.setDagensSelskap("ingen");

        context.putMap(convertObjectToMap(bi));

        List<Tilbud> tilbud = new ArrayList<Tilbud>();
        tilbud.add(new Tilbud("Utrygg", "Bilforsikring", "Info 1", "5000", "12000", "1337"));
        tilbud.add(new Tilbud("Else", "Bil", "Info 2", "5000", "12000", "420"));
        tilbud.add(new Tilbud("SlukketBrann", "Bil", "Info 3", "5000", "12000", "6969"));
        //context.putMap(convertObjectToMap(tilbud));
        context.put("tilbud", tilbud);

// 3) Set PDF as format converter
          Options options = Options.getTo(ConverterTypeTo.PDF);

// 3) Generate report by merging Java model with the ODT and convert it to PDF
          OutputStream out = new FileOutputStream(new File("bilforsikring.pdf"));
          report.convert(context, options, out);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  public static Map<String, Object> convertObjectToMap(Object input) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    Map<String, Object> objectAsMap = new HashMap<>();

    BeanInfo info = Introspector.getBeanInfo(input.getClass());
    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
      Method reader = pd.getReadMethod();
      if (reader != null)
        objectAsMap.put(pd.getName(), reader.invoke(input));
    }

    return objectAsMap;
  }

}