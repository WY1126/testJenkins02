package org.wangyao.testjenkins02.code.weakreference;

/**
 *
 *
 * @author wangyao
 * @Date 2025/12/2 17:30
 *
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelLineNumberFormatter {

    private static final Pattern LINE_NUMBER_PATTERN = Pattern.compile("(.*线)(\\d+)(#.*)");

    public static void main(String[] args) {
        try {
            // 1. 输入输出文件路径配置（实际使用时请修改路径）
            String inputFilePath = "C:\\Users\\17706\\Desktop\\aa.xlsx";
            String outputFilePath = "C:\\Users\\17706\\Desktop\\bb.xlsx";
            int targetColumn = 0; // 要处理的列索引（0表示第一列）

            // 2. 加载Excel文件
            try (FileInputStream fis = new FileInputStream(inputFilePath);
                 Workbook workbook = WorkbookFactory.create(fis)) {

                // 3. 处理所有工作表
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                    System.out.println("处理工作表: " + sheet.getSheetName());

                    // 4. 遍历所有行（跳过空行）
                    for (Row row : sheet) {
                        Cell cell = row.getCell(targetColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        if (cell.getCellType() == CellType.STRING) {
                            String originalValue = cell.getStringCellValue();
                            String formattedValue = formatLineNumber(originalValue);

                            // 5. 如果值被修改，则更新单元格
                            if (!formattedValue.equals(originalValue)) {
                                cell.setCellValue(formattedValue);
                                System.out.println("修改: " + originalValue + " → " + formattedValue);
                            }
                        }
                    }
                }

                // 6. 保存修改后的文件
                try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                    workbook.write(fos);
                    System.out.println("\n处理完成！已保存到: " + outputFilePath);
                }
            }
        } catch (Exception e) {
            System.err.println("处理出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 格式化线路编号 - 将"线XX#"中的编号补足3位
     * 示例:
     *   "10kV古桂133D线89#" -> "10kV古桂133D线089#"
     *   "35kV青山线101#"    -> "35kV青山线101#" (保持不变)
     */
    private static String formatLineNumber(String lineName) {
        Matcher matcher = LINE_NUMBER_PATTERN.matcher(lineName);
        if (matcher.find()) {
            String prefix = matcher.group(1); // 线前面的部分
            String number = matcher.group(2); // 数字部分
            String suffix = matcher.group(3); // #后面的部分

            // 若数字位数少于3位，则前面补0
            if (number.length() < 3) {
                String paddedNumber = String.format("%03d", Integer.parseInt(number));
                return prefix + paddedNumber + suffix;
            }
        }
        return lineName; // 未匹配则返回原始值
    }
}
