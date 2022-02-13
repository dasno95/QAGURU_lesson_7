package dasno95.lesson_7;

import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
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

        //csv
    }
}