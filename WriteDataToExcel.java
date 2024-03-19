import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteDataToExcel {

    public static void main(String[] args) throws Exception {
        String path = "./ssss.xlsx";

        FileInputStream fis = new FileInputStream(new File(path));
        XSSFWorkbook book = new XSSFWorkbook(fis);
        XSSFSheet sheet = book.getSheet("Sheet2");

        int rowCount = sheet.getPhysicalNumberOfRows();
        rowCount++;

        XSSFRow row = sheet.createRow(rowCount);

        Cell cell = row.createCell(0);
        cell.setCellValue("Shreyash");

        fis.close(); 

       FileOutputStream fos = new FileOutputStream(new File(path));
        book.write(fos);
        fos.close();
    }
}
