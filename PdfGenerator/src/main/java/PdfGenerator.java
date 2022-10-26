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
        //pg.genBildataAndPDF();
        //pg.genHusdataAndPDF();
        pg.genReisedataAndPDF();
    }

    public void genReisedataAndPDF() {
        ReiseInput ri = new ReiseInput();

        // Om reise
        ri.setOmfang("familie");
        ri.setEldste("18");
        ri.setAntallskader("1");

        // Om meg
        ri.setMellomnavn("Wim");
        ri.setEtternavn("Sjøholm");
        ri.setFoedselsnr("27058038356");
        ri.setPostnr("0482");
        ri.setGate("Grefsenveien");
        ri.setGatenr("8");
        List<String> foreninger = new ArrayList<String>();
        foreninger.add("KOL");
        foreninger.add("Norsk Journalistlag");
        foreninger.add("Ansatt kommune");
        ri.setForeningsMedlemsskap(foreninger);

        List<Tilbud> tilbud = new ArrayList<Tilbud>();
        tilbud.add(new ReiseTilbud("Utrygg", "Hus", "Info 1",  "12000", "1337"));
        tilbud.add(new ReiseTilbud("Else", "Hus", "Info 2", "12000", "420"));
        tilbud.add(new ReiseTilbud("SlukketBrann", "Husforsikring", "Info 3", "12000", "6969"));

        generatePDF(ri, tilbud, "reise");

    }

    public void genHusdataAndPDF() {
        HusInput hi = new HusInput();

        // Om deg selv
        hi.setMellomnavn("Wim");
        hi.setEtternavn("Sjøholm");
        hi.setFoedselsnr("27058038356");
        hi.setPostnr("0482");
        List<String> foreninger = new ArrayList<String>();
        foreninger.add("KOL");
        foreninger.add("Norsk Journalistlag");
        foreninger.add("Ansatt kommune");
        hi.setForeningsMedlemsskap(foreninger);

        // Om boligen
        hi.setGate("Grefsenveien");
        hi.setGatenr("8");
        hi.setBoligtype("enebolig");
        hi.setBruttoareal("100");
        hi.setStandard("normal");
        hi.setByggeaar("1955");
        hi.setByggemaate("mur");
        hi.setBebodd("true");
        hi.setAntallBoenheter("1");
        hi.setUtleiestatus1("utleid");
        hi.setRoerIRoer("false");
        hi.setLeieNaering("false");
        hi.setInnbruddsalarm("true");
        List<String> innbruddsAlarmEgenskaper = new ArrayList<String>();
        innbruddsAlarmEgenskaper.add("fg_godkjent");
        innbruddsAlarmEgenskaper.add("varsler_vaktselskap");
        hi.setInnbruddsalarmEgenskap(innbruddsAlarmEgenskaper);
        hi.setBrannalarm("true");
        List<String> brannAlarmEgenskaper = new ArrayList<String>();
        brannAlarmEgenskaper.add("fg_godkjent");
        brannAlarmEgenskaper.add("varsler_vaktselskap");
        hi.setBrannalarmEgenskap(brannAlarmEgenskaper);
        hi.setVannalarm("false");
        hi.setVannstoppventil("false");
        hi.setKomfyrvaktKomfyralarm("komfyrvakt");
        hi.setAutomatsikringer("true");
        hi.setAutomatsikringerAar("1995");
        hi.setTakvinkel("flatt_tak");
        hi.setVerneverdig("true");
        hi.setEtasjer("2");
        hi.setTilbakeslagsventil("false");
        hi.setPantIHus("false");
        hi.setRomUbakkeplan("false");
        hi.setTakIGodStand("true");

        // Om forsikringen
        hi.setEgenandel("5000");
        hi.setAntallForsikringsskader("0");
        hi.setVannskadeAar("2020");
        hi.setMusograateskader("true");
        hi.setAndreskader("true");
        hi.setAntallHusstandmedlemmer("2");
        hi.setKrypkjeller("false");
        hi.setNyttElAnleggAar("1995");
        hi.setRoerrenovertAar("null");
        hi.setAntallVaatrom("1");
        hi.setpRomSum("25");
        hi.setpRomKjeller("0");
        hi.setsRomSum("30");
        hi.setsRomKjeller("0");
        hi.setElKontroll("ja_siste_ti");
        hi.setRehabilitering("false");


        List<Tilbud> tilbud = new ArrayList<Tilbud>();
        tilbud.add(new HusTilbud("Utrygg", "Hus", "Info 1",  "12000", "1337"));
        tilbud.add(new HusTilbud("Else", "Hus", "Info 2", "12000", "420"));
        tilbud.add(new HusTilbud("SlukketBrann", "Husforsikring", "Info 3", "12000", "6969"));

        generatePDF(hi, tilbud, "hus");
    }

    public void genBildataAndPDF() {

        BilInput bi = new BilInput();
        bi.setMellomnavn("Wim");
        bi.setEtternavn("Sjøholm");
        bi.setFoedselsnr("27/05/80");
        bi.setPostnr("0482");

        bi.setRegistreringsnummer("EC58238");
        bi.setTyverialarm("false");
        bi.setGjenfinning("false");

        bi.setBilmerkeNavn("Volkswagen");
        bi.setRegistreringsaar("1995");
        bi.setModellNavn("ID.4");
        bi.setModellVariantNavn("77kWh 204hk Business");
        bi.setModellaar("2007");

        bi.setLeasing("false");
        bi.setForsteBil("true");
        bi.setBonus("70:fire_aar");
        bi.setDekning("kasko");
        bi.setKjoerelengde("12000");
        bi.setAlderYngsteFoerer("21");
        bi.setPantIBil("false");
        bi.setAntallBilskader("0");
        bi.setParkeringsforhold("egen_laast_garasje");
        bi.setDagensSelskap("ingen");

        List<String> foreninger = new ArrayList<String>();
        foreninger.add("KOL");
        foreninger.add("Norsk Journalistlag");
        foreninger.add("Ansatt kommune");
        bi.setForeningsMedlemsskap(foreninger);

        List<Tilbud> tilbud = new ArrayList<Tilbud>();
        tilbud.add(new BilTilbud("Utrygg", "Bilforsikring", "Info 1", "5000", "12000", "1337"));
        tilbud.add(new BilTilbud("Else", "Bil", "Info 2", "5000", "12000", "420"));
        tilbud.add(new BilTilbud("SlukketBrann", "Bil", "Info 3", "5000", "12000", "6969"));

        generatePDF(bi, tilbud, "bil");

    }
/*
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

    public void convertFTLtoPDF() {
        try {
            InputStream in = new FileInputStream(new File("bilforsikring.ftl"));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

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

            // 3) Set PDF as format converter
            Options options = Options.getTo(ConverterTypeTo.XHTML);

            // 3) Generate report by merging Java model with the ODT and convert it to PDF
            OutputStream out = new FileOutputStream(new File("bilforsikring.xhtml"));
            report.convert(context, options, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertBil() {
        try {
            InputStream in = new FileInputStream(new File("bilforsikring.odt"));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.load( "tilbud", Tilbud.class, true );


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

            bi.setBilmerkeNavn("Volkswagen");
            bi.setRegistreringsaar("1995");
            bi.setModellNavn("ID.4");
            bi.setModellVariantNavn("77kWh 204hk Business");
            bi.setModellaar("2007");

            bi.setLeasing("false");
            bi.setForsteBil("true");
            bi.setBonus("70:fire_aar");
            bi.setDekning("kasko");
            bi.setKjoerelengde("12000");
            bi.setAlderYngsteFoerer("21");
            bi.setPantIBil("false");
            bi.setAntallBilskader("0");
            bi.setParkeringsforhold("egen_laast_garasje");
            bi.setDagensSelskap("ingen");

            List<String> foreninger = new ArrayList<String>();
            foreninger.add("KOL");
            foreninger.add("Norsk Journalistlag");
            foreninger.add("Ansatt kommune");
            bi.setForeningsMedlemsskap(foreninger);

            context.putMap(convertObjectToMap(bi));

            List<Tilbud> tilbud = new ArrayList<Tilbud>();
            tilbud.add(new Tilbud("Utrygg", "Bilforsikring", "Info 1", "5000", "12000", "1337"));
            tilbud.add(new Tilbud("Else", "Bil", "Info 2", "5000", "12000", "420"));
            tilbud.add(new Tilbud("SlukketBrann", "Bil", "Info 3", "5000", "12000", "6969"));
            //context.putMap(convertObjectToMap(tilbud));
            context.put("tilbud", tilbud);

            // 3) Set PDF as format converter
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);

            // 3) Generate report by merging Java model with the ODT and convert it to PDF
            OutputStream out = new FileOutputStream(new File("bilforsikring.pdf"));
            report.convert(context, options, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 */
    public void generatePDF(CalculatorInput calcInput, List<Tilbud> tilbud, String lob) {
        String filnavn = lob + "forsikring";
        try {
            InputStream in = new FileInputStream(new File(filnavn + ".odt"));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.load( "tilbud", Tilbud.class, true );


            IContext context = report.createContext();

            context.put("calculatorUseDate", java.time.LocalDate.now().format(DateTimeFormatter
                    .ofLocalizedDate(FormatStyle.LONG)));

            context.putMap(convertObjectToMap(calcInput));
            context.put("tilbud", tilbud);

            // 3) Set PDF as format converter
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);

            // 3) Generate report by merging Java model with the ODT and convert it to PDF
            OutputStream out = new FileOutputStream(new File(filnavn + ".pdf"));
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