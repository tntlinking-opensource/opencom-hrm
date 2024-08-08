package com.geeke.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取excel公共方法
 * 
 *
 */
public class ExcelReaderUtils {

    	//EXCEL 列标识
    	private final static String ABC = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z," +
				"AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL,AM,AN,AO,AP,AQ,AR,AS,AT,AU,AV,AW,AX,AY,AZ," +
				"BA,BB,BC,BD,BE,BF,BG,BH,BI,BJ,BK,BL,BM,BN,BO,BP,BQ,BR,BS,BT,BU,BV,BW,BX,BY,BZ," +
				"CA,CB,CC,CD,CE,CF,CG,CH,CI,CJ,CK,CL,CM,CN,CO,CP,CQ,CR,CS,CT,CU,CV,CW,CX,CY,CZ," +
				"DA,DB,DC,DD,DE,DF,DG,DH,DI,DJ,DK,DL,DM,DN,DO,DP,DQ,DR,DS,DT,DU,DV,DW,DX,DY,DZ," +
				"EA,EB,EC,ED,EE,EF,EG,EH,EI,EJ,EK,EL,EM,EN,EO,EP,EQ,ER,ES,ET,EU,EV,EW,EX,EY,EZ";

	//去空
	public static List<String[]> readExcelTempXlsxIsNull(MultipartFile dataFile){
		// 获取Excel文件对象
		XSSFWorkbook xssfWorkbook = null;
		try {
			InputStream ins = dataFile.getInputStream();
			xssfWorkbook = new XSSFWorkbook(ins);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获取文件的指定工作表 默认的第一个
		XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(0);
		int lastCellNum = row.getLastCellNum();
//		Cell cell = null;
		XSSFCell cell = null;
		List<String[]> columns = new ArrayList<String[]>();

		String[] ids = ABC.split(",");

		for(int i=0; i<lastCellNum; i++){
			cell = row.getCell(i);
//			cell = sheet.getCell(i, 0);

			String[] str = new String[2];
			str[0] = ids[i];
			//EXCEL模板列多于ABC定义的长度
			if(i > ids.length){
				str[0] = "" + i;
			}
			if("null".equals(cell+"")){
				continue;
			}
			str[1] = cell+"";

			columns.add(str);
		}

		return columns;
	}

	//excel导入兼容性设置
	public static List<String[]> readExcelAll(MultipartFile file, int rowNum) throws IOException{

		String originalFilename = file.getOriginalFilename();
		List<String[]> data = null;
		//首先判断是不是空的文件
		if (!file.isEmpty()) {
			//对文文件的全名进行截取然后在后缀名进行删选。
			int begin = originalFilename.indexOf(".");
			int last = originalFilename.length();
			//获得文件后缀名
			String a = originalFilename.substring(begin, last);
			if (a.endsWith(".xlsx")) {
				data = readExcelXlsx(file, rowNum);
			}else if(a.endsWith(".xls")) {
				data = readExcel(file, rowNum);
			}
		}
		return data;
	}
	/**
	 *
	 * @param dataFile 读取流
	 * @param rowNum 从第几行开始读，如果有一行表头则从第二行开始读
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> readExcel(MultipartFile dataFile, int rowNum) throws
			IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook workbook = null;
		Cell cell = null;
		// 创建输入流
//		InputStream stream = new FileInputStream(excelFile);
		// 获取Excel文件对象
		InputStream stream = dataFile.getInputStream();
		workbook = new HSSFWorkbook(stream);
		// 获取文件的指定工作表 默认的第一个
		Sheet sheetAt = workbook.getSheetAt(0);
		Row row0 = sheetAt.getRow(0);
		// 行数(表头的目录不需要，从1开始)
		for (int i = rowNum-1; i <= sheetAt.getLastRowNum(); i++) {
			// 创建一个数组 用来存储每一列的值
			Row row = sheetAt.getRow(i);
			if(row == null){
				continue;
			}
			//判断空行
//			if (isRowEmpty(row)) continue;
			String[] str = new String[row0.getLastCellNum()];
			String temp = "";
//			if(row.getCell(3) == null){
//				continue;
//			}
			// 列数
			for (int j = 0; j < row0.getLastCellNum(); j++) {
				// 获取第i行，第j列的值
				if(j<row0.getLastCellNum()){
					cell = row.getCell(j);
					if(cell != null){
						try {
							str[j] = String.valueOf(cell.getStringCellValue());
						} catch (IllegalStateException e) {
							str[j] = String.valueOf(cell.getNumericCellValue());
						}
					}else{
						str[j] = "";
					}
				}else{
					str[j] = "";
				}
				//str[j] =replaceBlank(cell.getContents());
				//edit by panqun 20140816 以上把所有空格都取消了，不适用于英文客户名称
				temp = temp.concat(str[j]);
			}
			if(!StringUtils.isEmpty(temp)){
				// 把刚获取的列存入list
				list.add(str);
			}
		}
		// 返回值集合
		return list;
	}
	public static List<String[]> readExcelXlsx(MultipartFile dataFile, int rowNum) throws
			IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook workbook = null;
		Cell cell = null;
		// 创建输入流
//		InputStream stream = new FileInputStream(excelFile);fds
		// 获取Excel文件对象
		InputStream stream = dataFile.getInputStream();
		workbook = new XSSFWorkbook(stream);
		// 获取文件的指定工作表 默认的第一个
		Sheet sheetAt = workbook.getSheetAt(0);
		Row row0 = sheetAt.getRow(0);
		// 行数(表头的目录不需要，从1开始)
		for (int i = rowNum-1; i <= sheetAt.getLastRowNum(); i++) {
			// 创建一个数组 用来存储每一列的值
			Row row = sheetAt.getRow(i);
			if(row == null){
				continue;
			}
			//判断空行
//			if (isRowEmpty(row)) continue;
			String[] str = new String[row0.getLastCellNum()];
			String temp = "";
//			if(row.getCell(3) == null){
//				continue;
//			}
			// 列数
			for (int j = 0; j < row0.getLastCellNum(); j++) {
				// 获取第i行，第j列的值
				if(j<row0.getLastCellNum()){
					cell = row.getCell(j);
					if(cell != null){
						try {
							str[j] = String.valueOf(cell.getStringCellValue());
						} catch (IllegalStateException e) {
							str[j] = String.valueOf(cell.getNumericCellValue());
						}
					}else{
						str[j] = "";
					}
				}else{
					str[j] = "";
				}
				//str[j] =replaceBlank(cell.getContents());
				//edit by panqun 20140816 以上把所有空格都取消了，不适用于英文客户名称
				temp = temp.concat(str[j]);
			}
			if(!StringUtils.isEmpty(temp)){
				// 把刚获取的列存入list
				list.add(str);
			}
		}
		// 返回值集合
		return list;
	}
//	public static boolean isRowEmpty(XSSFRow row){
//		     for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
//				 XSSFCell cell = row.getCell(c);
//				 if (row.getCell(c) != null)
//			                return false;
//		         }
//		     return true;
//		 }
	/**
     *
     * @功能描述： 去空格，回车符
     * @返回类型：String
     * @参数类型：@param str
     * @参数类型：@return
     *
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null&&!str.equals("")) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest.trim();
    }
//
//    /**
//     *
//     * @功能描述： 去以下字符 \t 跳格  \r回车   \n换行
//     * @返回类型：String
//     * @参数类型：@param str
//     * @参数类型：@return
//     *
//     */
    public static String replaceEnter(String str) {
        String dest = "";
        if (str!=null&&!str.equals("")) {
            Pattern p = Pattern.compile("\\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest.trim();
    }
//
//
//	/**
//	 *
//	 * @作者: 马宏敏
//	 * @创建时间：2011-8-1 上午02:29:13
//	 * @功能描述： 重载上面的readExcel,添加一个读取最大行数，如果读取的行数大于excel的行数，那么就为读取excel行数
//	 * @返回类型：List<String[]>
//	 * @参数类型：@param excelFile
//	 * @参数类型：@param rowNum 从第几行开始读，如果有一行表头则从第二行开始读 从1开始
//	 * @参数类型：@param maxNum 一共要读取的行数，如果读取的行数大于excel的行数，那么就为读取excel行数
//	 * @参数类型：@return
//	 * @参数类型：@throws BiffException
//	 * @参数类型：@throws IOException
//	 *
//	 */
//        public static List<String[]> readExcel(MultipartFile dataFile,int rowNum, int maxNum) throws BiffException, IOException {
//            maxNum++;
//            // 创建一个list 用来存储读取的内容
//            List<String[]> list = new ArrayList<String[]>();
//            Workbook rwb = null;
//            Cell cell = null;
//            // 创建输入流
////            InputStream stream = new FileInputStream(excelFile);
//            // 获取Excel文件对象
//			InputStream stream = dataFile.getInputStream();
//			rwb = Workbook.getWorkbook(stream);
//            // 获取文件的指定工作表 默认的第一个
//            Sheet sheet = rwb.getSheet(0);
//            // 行数(表头的目录不需要，从1开始)
//            for (int i = rowNum-1; i < (maxNum > sheet.getRows() == true ? sheet.getRows() : maxNum); i++) {
//                // 创建一个数组 用来存储每一列的值
//                String[] str = new String[sheet.getColumns()];
//                // 列数
//                for (int j = 0; j < sheet.getColumns(); j++) {
//                   // 获取第i行，第j列的值
//                    cell = sheet.getCell(j, i);
//                    str[j] = cell.getContents();
//                }
//                // 把刚获取的列存入list
//                list.add(str);
//           }
//           // 返回值集合
//           return list;
//        }
	public static List<String[]> readExcelXlsx(MultipartFile dataFile, int rowNum, int maxNum) throws IOException {
		maxNum++;
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;

		Cell cell = null;
		// 获取Excel文件对象
		InputStream stream = dataFile.getInputStream();
		rwb = new XSSFWorkbook(stream);
		// 获取文件的指定工作表 默认的第一个
		Sheet sheetAt = rwb.getSheetAt(0);
		Row row0 = sheetAt.getRow(0);
//		int lastRowNum = sheetAt.getLastRowNum();
		// 行数(表头的目录不需要，从1开始)
		for (int i = rowNum-1; i <= (maxNum > sheetAt.getLastRowNum() == true ? sheetAt.getLastRowNum() : maxNum); i++) {
			// 创建一个数组 用来存储每一列的值
			Row row = sheetAt.getRow(i);
			String[] str = new String[row0.getLastCellNum()];
			String temp = "";
			// 列数
			for (int j = 0; j < row0.getLastCellNum(); j++) {
				// 获取第i行，第j列的值
				if(j<row0.getLastCellNum()){
					cell = row.getCell(j);
					if(cell != null){
						try {
							str[j] = String.valueOf(cell.getStringCellValue());
						} catch (IllegalStateException e) {
							str[j] = String.valueOf(cell.getNumericCellValue());
						}
					}else{
						str[j] = "";
					}
				}else{
					str[j] = "";
				}
				temp = temp.concat(str[j]);
			}
			if(!StringUtils.isEmpty(temp)){
				// 把刚获取的列存入list
				list.add(str);
			}
		}
		// 返回值集合
		return list;
	}
//
//	//处理公式
////	public static String getCellValueFormula(org.apache.poi.ss.usermodel.Cell cell, FormulaEvaluator formulaEvaluator) {
////		if (cell == null || formulaEvaluator == null) {
////			return null;
////		}
////
////		if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA) {
////			return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
////		}
////		return getCellValue(cell);
////	}
//        /**
//         * 从excel文件里读取数据，此方法只针对行列规则的excel文件
//         * 读多个sheet的情况下
//         * 返回结果是一行为一个map,
//         * @param fileName excel文件名
//         * @return
//         */
//        public static Map<String, List<String[]>>   readDataFromExcel(String fileName){
//            Map<String, List<String[]>> map  =new HashMap<String, List<String[]>>();
//     //      List allDataList = new ArrayList();
//            InputStream is =null;
//            Workbook rwb=null;
//           try{
//                is = new FileInputStream(fileName);//读取文件
//                rwb = Workbook.getWorkbook(is);
//               int sheetCount = rwb.getNumberOfSheets();
//               System.out.println("表单总数==="+sheetCount);
//               String[] sheetName= rwb.getSheetNames();
//               for(int k=0;k<sheetCount;k++){
//                   System.out.println("表单名称==="+sheetName[k].toString());
//                   Sheet rs = rwb.getSheet(sheetName[k].toString());//得到第一个工作表单
//                   int col = rs.getColumns(); //得到excel文件的列数
//                   int rol = rs.getRows();//excel文件的行数
//                   System.out.println("得到excel文件的列数"+col);
//                   System.out.println("excel文件的行数"+rol);
//                   List rowList = new ArrayList<String[]>();
//                   for(int j=0;j<rol;j++){
//                      String[] str = new String[rs.getColumns()];
//                      for(int i=0;i<col;i++){
//                         Cell cell = rs.getCell(i,j);
//                         str[i]=cell.getContents();
//                    //     String data = cell.getContents();
//                  //       rowList.add(data);
//                      }
//                      rowList.add(str);
//                   }
//                   map.put(String.valueOf(k), rowList);
//                //    allDataList.add(rowList);
//               }
//
//           }catch(Exception e){
//               e.printStackTrace();
//           }finally{
//               try {
//                  is.close();
//                   rwb.close();
//            } catch (IOException e) {
//            }
//           }
//           return map;
//        }
//
//	public static void main(String[] args) {
//	    Map   map=	    ExcelReader.readDataFromExcel("c://入职批量导入.xls");
//	    List  list= (List)map.get("0");
//	    if(null!=list&&list.size()>2){
//	      for (int i = 0; i < list.size(); i++) {
//	          String[] str=(  String[])list.get(i);
//	          for (int j = 0; j < str.length; j++) {
//	              System.out.println(str[j]);
//
//            }
//	          System.out.println("---------------------------------------");
//
//        }
//	    }
//	}
	/**
	 * 异地账单导入对工伤、生育比例调整
//	 * @param excelFile
//	 * @param rowNum
	 * @return
	 * @throws IOException
	 */
//	public static List<String[]> readExcelYd(File excelFile,int rowNum) throws BiffException,
//	IOException {
//		// 创建一个list 用来存储读取的内容
//		List<String[]> list = new ArrayList<String[]>();
//		Workbook rwb = null;
//		Cell cell = null;
//		// 创建输入流
//		InputStream stream = new FileInputStream(excelFile);
//		// 获取Excel文件对象
//		rwb = Workbook.getWorkbook(stream);
//		// 获取文件的指定工作表 默认的第一个
//		Sheet sheet = rwb.getSheet(0);
//		// 行数(表头的目录不需要，从1开始)
//		for (int i = rowNum-1; i < sheet.getRows(); i++) {
//			// 创建一个数组 用来存储每一列的值
//			String[] str = new String[sheet.getColumns()];
//			String temp = "";
//			// 列数
//			for (int j = 0; j < sheet.getColumns(); j++) {
//				// 获取第i行，第j列的值
//				cell = sheet.getCell(j, i);
//				if(j==41 || j==43 || j==48 || j==50){
//					if(!"Empty".equals(cell.getType().toString())){
//						if(cell.getType()==CellType. LABEL){
//							LabelCell nc = (LabelCell) cell;
//							if(null!=nc. getString()){
//								str[j] = nc. getString();
//							}
//
//						}
//						if(cell.getType()==CellType.NUMBER || cell.getType()==CellType.NUMBER_FORMULA){
//	                    	NumberCell numberCell = (NumberCell)cell;
//	                    	String numberValue=null;
//	                    	if (!isNum(String.valueOf(numberCell.getValue()).trim())){
//	                    		  DecimalFormat decimalFormat = new DecimalFormat("#.####");//格式化设置
//	                    		  numberValue=decimalFormat.format(numberCell.getValue());
//	                    	}else{
//		     			          numberValue = String.valueOf(numberCell.getValue());
//	                    	}
//	     			       if(null!=numberValue){
//	     			    	  str[j] =numberValue;
//							}
//						}
//	                    /*if(cell.getType()==CellType.NUMBER_FORMULA){
//	                    	NumberCell numberCell = (NumberCell)cell;
//	     			        Double numberValue = numberCell.getValue();
//	     			       if(null!=numberValue.toString()){
//	     			    	  str[j] =numberValue.toString();
//							}
//						}*/
//					}else{
//						str[j] =cell.getContents().trim();
//					}
//				}else{
//					str[j] =cell.getContents().trim();
//				}
//				temp = temp.concat(str[j]);
//			}
//			if(!StringUtils.isEmpty(temp)){
//				// 把刚获取的列存入list
//				list.add(str);
//			}
//
//		}
//		// 返回值集合
//		return list;
//     }
	
	 public static boolean isNum(String str){        
	        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");   
	    }

	public static int getCellNum(String cellStr) {
		char[] cellStrArray = cellStr.toUpperCase().toCharArray();
		int len = cellStrArray.length;
		int n = 0;
		for(int i=0;i<len;i++){
			n += (((int)cellStrArray[i])-65+1)*Math.pow(26, len-i-1);
		}
		return n-1;
	}
}

