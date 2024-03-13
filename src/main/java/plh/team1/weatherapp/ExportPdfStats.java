package plh.team1.weatherapp;

import plh.team1.weatherapp.model.CityModel;

// Java
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// ITextPDF
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class ExportPdfStats {

    private static final Utilities util = new Utilities();
    private static final String FONT_PATH = "fonts/Roboto-Regular.ttf";

    public static void exportPdfStats(List<CityModel> cityList) {
        String pdfFilePath = "exports/report_" + util.getDateIdentifier() + ".pdf";

        try {
            createPdf(pdfFilePath, cityList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void createPdf(String filePath, List<CityModel> cityList) throws FileNotFoundException {
        try {
            // Create a PdfWriter instance to write to the PDF file
            PdfWriter pdfWriter = new PdfWriter(filePath);

            // Create a PdfDocument instance
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            // Create a Document instance
            Document document = new Document(pdfDocument);

            // Load a font that supports Greek characters
            PdfFont greekFont = PdfFontFactory.createFont(FONT_PATH, PdfEncodings.CP1253, EmbeddingStrategy.PREFER_EMBEDDED);

            // Add title
            Paragraph title = new Paragraph("Στατιστικά καιρού")
                    .setFont(greekFont)
                    .setBold()
                    .setFontSize(16)
                    .setMarginBottom(10);

            document.add(title);

            // Add current date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = dateFormat.format(new Date());

            Paragraph dateTime = new Paragraph("Ημερομηνία και ώρα εκτύπωσης: " + formattedDate)
                    .setFont(greekFont)
                    .setFontSize(12)
                    .setMarginBottom(20);

            document.add(dateTime);

            // Create a Table instance
            Table table = new Table(2); // Number of columns

            // Add headers to the table with the Greek font
            addHeaderCell(table, greekFont, "Πόλη");
            addHeaderCell(table, greekFont, "Φορές Αναζήτησης");

            // Add rows to the table based on the City entries
            for (CityModel city : cityList) {
                addContentCell(table, greekFont, city.getCityName());
                addContentCell(table, greekFont, String.valueOf(city.getTimesSearched()));
            }

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addHeaderCell(Table table, PdfFont font, String content) {
        Cell cell = new Cell();
        cell.setFont(font)
                .add(new Paragraph(content))
                .setBold();
        table.addCell(cell);
    }

    private static void addContentCell(Table table, PdfFont font, String content) {
        Cell cell = new Cell();
        cell.setFont(font)
                .add(new Paragraph(content));
        table.addCell(cell);
    }
}
