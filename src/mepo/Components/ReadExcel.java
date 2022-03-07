package mepo.Components;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mepo.Helper.OrderDetailHelper;
import mepo.Helper.OrderHelper;
import mepo.Implements.OrderDetailImp;
import mepo.Implements.OrderImp;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
    public static final int COLUMN_INDEX_USERNAME = 0;
    public static final int COLUMN_INDEX_PASSWORD = 1;
    public static final int COLUMN_INDEX_FULLNAME = 2;
    public static final int COLUMN_INDEX_EMAIL = 3;
    public static final int COLUMN_INDEX_COIN = 4;
    public static final int COLUMN_INDEX_ORDERDATE = 5;
    public static final int COLUMN_INDEX_productID = 6;


    public static List<User> readExcelUser(String excelFilePath) throws IOException {
        List<User> listUsers = new ArrayList<>();

        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            User user = new User();
            user.setPassword("123456");
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_USERNAME:
                        user.setUsername((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_FULLNAME:
                        user.setFullname((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_COIN:
                        user.setCoin((Double) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_EMAIL:
                        user.setContact((String) getCellValue(cell));
                        break;
                    default:
                        break;
                }

            }
            listUsers.add(user);
        }

        workbook.close();
        inputStream.close();

        return listUsers;
    }

    public static void readFileExcelOrders(String excelFilePath) throws IOException {
        OrderImp orderHelper = new OrderHelper();
        OrderDetailImp orderDetailHelper = new OrderDetailHelper();

        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            Order order = new Order();
            String username = "";
            String orderDate = "";
            String email = "";
            double productId = -1;
            int orderId = -1;
            OrderDetail orderDetail = new OrderDetail();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_USERNAME:
                        username = (String) getCellValue(cell);
                        break;
                    case COLUMN_INDEX_EMAIL:
                        email = (String) getCellValue(cell);
                        break;
                    case COLUMN_INDEX_ORDERDATE:
                        orderDate = (String) getCellValue(cell);
                        break;
                    case COLUMN_INDEX_productID:
                        productId = (double) getCellValue(cell);
                        break;
                    default:
                        break;
                }

                if (!username.equals("") && !orderDate.equals("") && productId != -1 && !email.equals("")) {
                    order.setOrderDate(orderDate);
                    order.setAccountAddress(email);
                    order.setUsername(username);
                    orderId = orderHelper.insert(order).getOrderID();
                    if (orderId != -1) {
                        orderDetail.setProductID((int) productId);
                        orderDetail.setOrderID(orderId);
                        orderDetailHelper.insert(orderDetail);
                    }
                }

            }

        }

        workbook.close();
        inputStream.close();


    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}