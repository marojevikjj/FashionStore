package onlineshopapp.fashionstore.model;

import antlr.collections.List;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Data
public class PDFExport {

    private Order order;

    public PDFExport(Order order) {
        this.order = order;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.PINK);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("User Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Postman", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Status", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        
            table.addCell(order.getUser().getName());
            table.addCell(Double.toString(order.getPrice()));
            table.addCell(order.getPostman().getName());
            table.addCell(order.getDate().toString());
            table.addCell(order.getOrderStatus().toString());

    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("User Orders", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
