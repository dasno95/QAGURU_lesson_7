package dasno95.lesson_7;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesFromZIPTest {

    @Test
    void parsingFilesFromZIP() throws Exception {
        ZipFile zip = new ZipFile("src/test/resources/resources.zip");

        // pdf
        ZipEntry zipEntryPDF = zip.getEntry("PDF_file.pdf");
        try(InputStream isPDF = zip.getInputStream(zipEntryPDF)) {
            PDF parsed = new PDF(isPDF);
            assertThat(parsed.text).contains("I am PDF file!");
        }

        // xlsx
        ZipEntry zipEntryXLSX = zip.getEntry("XLSX_file.xlsx");
        try(InputStream isXLSX = zip.getInputStream(zipEntryXLSX)) {
            XLS parsed = new XLS(isXLSX);
            assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).isEqualTo("Teacher");
            assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue()).isEqualTo("Office");
            assertThat(parsed.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("Ivanov");
            assertThat(parsed.excel.getSheetAt(0).getRow(1).getCell(1).getNumericCellValue()).isEqualTo(305.0);
        }

        //csv
        ZipEntry zipEntryCSV = zip.getEntry("CSV_file.csv");
        try(InputStream isCSV = zip.getInputStream(zipEntryCSV)) {
            CSVReader reader = new CSVReader(new InputStreamReader(isCSV));
            List<String[]> list = reader.readAll();
            assertThat(list)
                    .hasSize(3)
                    .contains(
                            new String[] {"Flower", "Colour"},
                            new String[] {"Rose", "Red"},
                            new String[] {"Tulip", "Yellow"}
                    );
        }
    }
}