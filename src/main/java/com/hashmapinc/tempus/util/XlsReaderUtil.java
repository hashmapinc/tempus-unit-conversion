package com.hashmapinc.tempus.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class XlsReaderUtil {

    private final DataFormatter dataFormatter = new DataFormatter();
    private static final String PI = "3.14159";

    public Workbook getWorkbook(String workBook) throws IOException, InvalidFormatException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(workBook)).getFile());

        return WorkbookFactory.create(file);
    }

    public Sheet getSheetFromWorkbook(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName);
    }

    public String getStringValueFromRow(Row row, int cellNo) {
        return dataFormatter.formatCellValue(row.getCell(cellNo));
    }

    public Double getDoubleValueFromRow(Row row, int cellNo) {

        String cellValue = dataFormatter.formatCellValue(row.getCell(cellNo , Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
        if (!cellValue.isEmpty() && !cellValue.contains("PI")) {
            return Double.parseDouble(cellValue);
        } else if (cellValue.contains("PI")){
            String newCellValue = cellValue.replace("PI", PI);
            if (newCellValue.contains("*")) {
                String[] values = newCellValue.split("\\*");
                return Double.parseDouble(values[0]) * Double.parseDouble(values[1]);
            }
            return Double.parseDouble(newCellValue);
        }
        return 0.0;
    }
}
