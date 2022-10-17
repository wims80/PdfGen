import java.io.InputStream;
import java.io.FileInputStream;
import org.odftoolkit.odfdom.doc.*;

public class PdfGenerator {
    public static void main(String[] args) {
        PdfGenerator pg = new PdfGenerator();
        pg.generatePdf();
    }

    public void generatePdf() {
        try {
            InputStream in = new FileInputStream("bilforsikring.odt");
            OdfTextDocument odt = OdfTextDocument.loadDocument(in);
            //OdfTextDocument odt = OdfTextDocument.newTextDocument();
        } catch (Exception e) {
            System.out.println("Error! " + e.toString());
        }
    }
}