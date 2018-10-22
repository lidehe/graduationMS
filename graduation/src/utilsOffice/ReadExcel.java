package utilsOffice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcel {
	public static Map<Integer, List<String>> getDataFromExcel(File file) {
		Map<Integer, List<String>> resultDatas = new HashMap<>();// 结果数据
		InputStream is = null;
		//File file = new File(path);
		//System.out.println(file);
		// 如果是文件，就执行数据载入，若是目录，提示用户选择目录
		if (file.isFile()) { 
			try {
				is = new FileInputStream(file);
				// 此处可根据文档的后缀名判断出文档版本是新还是旧，对应就能选择合适的处理方法
				String path=file.getName();
				System.out.println("等待截取的文件的路径是L:"+path);
				String sufix = null;
				sufix = path.substring(path.lastIndexOf("."), path.length());
				System.out.println(sufix);
				if (sufix.equals(".xls")) {
					resultDatas = readExcelOld(is);
				} else if (sufix.endsWith(".xlsx")) {
					resultDatas = readExcelNew(is);
				} 
				is.close();
			} catch (Exception e) {
				throw new RuntimeException("读取excel文件失败");
			}
			
		} else {
			System.out.println("当前选择的是目录，请选择一个excel文件！");
		}

		return resultDatas;
	}

	// 读取office2007之前的excel文档
	@SuppressWarnings("deprecation")
	public static Map<Integer, List<String>> readExcelOld(InputStream is) {
		HSSFWorkbook hssfWorkbook = null;
		Map<Integer, List<String>> datas = new HashMap<>();// 存放excel中获得的结果
		int rowCount = 0;// 结果的行：因为sheet中可能有空行，所以不能以sheet中的行来计算结果的行
		List<String> rowDatas = null;// 存放一行的数据，取完一行就放到datas里
		HSSFRow row;
		HSSFCell cell;
		try {
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			throw new RuntimeException("新版office的需要新的处理方式");
		}
		// 循环读取工作表
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		// 循环读取行,要加1，因为第一行是标题
		for (int rowNum = hssfSheet.getFirstRowNum()+1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			row = hssfSheet.getRow(rowNum);
			if (row == null) {// 如果是空行，直接跳过
				continue;
			}
			rowDatas = new ArrayList<>();
			// 循环读取列
			for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
				cell = row.getCell(j);
				String value=null;
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_BLANK:
                  value="空的";
					break;
				case HSSFCell.CELL_TYPE_STRING:
					value=cell.toString();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					Double d=cell.getNumericCellValue();
                   DecimalFormat df=new DecimalFormat("#");
                    value=df.format(d);
					break;

				default:
					break;
				}
				rowDatas.add(value);
			}
			datas.put(rowCount, rowDatas);
			rowCount++;
		}
		try {
			hssfWorkbook.close();
		} catch (IOException e) {
			throw new RuntimeException("关闭旧版excel文档失败");
		}
		return datas;
	}

	// 读取office2007以后的excel文档
	public static Map<Integer, List<String>> readExcelNew(InputStream is) {
		Map<Integer, List<String>> datas = new HashMap<>();// 存放excel中获得的结果
		int rowCount = 0;// 结果的行：因为sheet中可能有空行，所以不能以sheet中的行来计算结果的行
		XSSFWorkbook xsw = null;
		List<String> rowDatas = null;// 存放一行的数据，取完一行就放到datas里
		XSSFRow row;
		XSSFCell cell;
		try {
			xsw = new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new RuntimeException("你的文档不被支持，请使用2003-2007版的office");
		}
		XSSFSheet sheet = xsw.getSheetAt(0);
		// 行循环,要加1，因为第一行是标题
		for (int i = sheet.getFirstRowNum()+1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			if (row == null) {// 如果是空行，直接跳过
				continue;
			}
			// 列循环
			rowDatas = new ArrayList<>();// 存放一行的数据，取完一行就放到datas里
			for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
				cell = row.getCell(j);
				String value=null;
				if(cell!=null)
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_BLANK:
                  value="空的";
					break;
				case HSSFCell.CELL_TYPE_STRING:
					value=cell.toString();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					Double d=cell.getNumericCellValue();
                   DecimalFormat df=new DecimalFormat("#");
                    value=df.format(d);
					break;
				default:
					break;
				}
				rowDatas.add(value);
			}
			datas.put(rowCount, rowDatas);
			rowCount++;
		}
		try {
			xsw.close();
		} catch (IOException e) {
			throw new RuntimeException("关闭新版excel文档失败");
		}
		return datas;
	}
}
