package utilsOffice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {


	/**
	 * 导出新版excel表格(xlsx)，推荐使用
	 * @param targetPath//目标路径
	 * @param list对象数组
	 * @param tableTitle表格sheet标题
	 * @param fileName文件名字(不需要加后缀)
	 */
	@SuppressWarnings("deprecation")
	public static <T> boolean makeNewExcel(String targetPath, List<String[]> list , String tableTitle,
			String fileName)  {
		fileName+=".xlsx";
		File file = new File(targetPath + File.separator + fileName);
		System.out.println("文件全路径是：" + file);

		// 创建excel对象
		XSSFWorkbook workbook = new XSSFWorkbook();

		// 设置单元格字体
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11); // 字体高度
		font.setFontName("宋体"); // 字体

		// 穿件单元格格式
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平布局：居左
		cellStyle.setFont(font);// 使用上面创建的字体

		// 创建sheet对象
		XSSFSheet sheet = workbook.createSheet(tableTitle);
		String[] infors = null;
		// 创建行,第一行做标题，第二行做栏目了，所以从第三行开始
		for (int i = 0; i < list.size(); i++) {
			infors = list.get(i);
			XSSFRow row = sheet.createRow(i);
			// 创建列，把对应的属性值塞进去
			for (int j = 0; j < infors.length; j++) {
				sheet.autoSizeColumn((short) j); // 调整宽度
				XSSFCell cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
				// 根据属性类型设置表格格式
				cell.setCellValue(infors[j]);
			}
		}
		boolean result=true;
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			workbook.write(os);
			workbook.close();
			os.close();
			result=true;
		} catch (Exception e) {
			result=false;
			throw new RuntimeException("导入excel错误");
		}

		return result;
	}
}
