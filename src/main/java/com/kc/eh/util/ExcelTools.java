package com.kc.eh.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.util.StringUtils;

/**
 * 读取excel表格 返回List<Object>
 * @author MJ
 *
 */
public class ExcelTools {

	@SuppressWarnings("resource")
	public List<Object> readExcel(String filePath,Class<?> clazz) throws Exception {
	    List<Object> list = new ArrayList<Object>();
	    //List columnList = new ArrayList();
	    Map<String, String> map = setValues(clazz);
	    list = excelToList(filePath, list, map, clazz);
	    //sample(filePath, list);
	    return list;
	}

	private List<Object> excelToList(String filePath, List<Object> list, Map<String, String> map, Class<?> clazz) throws Exception{
		HSSFWorkbook workbook = null;
	    
	    // 读取Excel文件
		InputStream inputStream = new FileInputStream(filePath);
	    workbook = new HSSFWorkbook(inputStream);
	    inputStream.close();
	    for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
	    	HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
	    	int coloumNum = hssfSheet.getRow(0).getPhysicalNumberOfCells();
	    	
	      	if (hssfSheet == null) {
	    	  continue;
	      	}
	      	HSSFRow headerRow = hssfSheet.getRow(0);
	      	//判断是否有序号列 有则 赋值起始从第一列开始 跳过序号列（防止赋值错位）
	      	String stringCellValue = headerRow.getCell(0).getStringCellValue();
	      	int start = 0;
	      	if ("序号".equals(stringCellValue)) {
	      		start++;
		    }
	      	// 循环行
	      	for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	      		HSSFRow hssfRow = hssfSheet.getRow(rowNum);
	      		if ( hssfRow == null || isEmptyRow(hssfRow)) {
	      			//空行添加一个null 占位 
	      			list.add(null);
	      			continue;
	      		}
	      		//BIWaterUserExport biwater = new BIWaterUserExport();
	      		//反射创建实列
	      		Object newInstance = clazz.newInstance();
	      		// 将单元格中的内容存入集合
	      		for (int i = start; i < coloumNum + start; i++) {
		    		// 将单元格中的内容存入集合
		  	        
		  	        HSSFCell cell = hssfRow.getCell(i);
		  	        HSSFCell headerCell = headerRow.getCell(i);
		  	        if (cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
		  	        	continue;
		  	        }
		  	        CellType cellTypeEnum = cell.getCellTypeEnum();
		  	        if (cellTypeEnum == CellType.NUMERIC) {
						cell.setCellType(CellType.STRING);
					}
		  	        //从 map中取出需要执行的方法名
		  	        String methodName = map.get(headerCell.getStringCellValue());
		  	        if (methodName == null){
		  	        	
		  	        	continue;
		  	        }
		  	        //biwater.setPump_id(cell.getStringCellValue());
		  	        //执行方法  setXXXX(param) 参数是单元格的内容
		  	        Method method = clazz.getMethod(methodName, String.class);
		  	        method.invoke(newInstance, cell.getStringCellValue());
	      		}
	      		list.add(newInstance);
	      	}
		}
		return list;
		
	}
	
	private boolean isEmptyRow(HSSFRow hssfRow){
		for (int i = hssfRow.getFirstCellNum(); i < hssfRow.getLastCellNum(); i++) {
			HSSFCell cell = hssfRow.getCell(i);
			CellType cellTypeEnum = cell.getCellTypeEnum();
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}
	
	private Map<String,String> setValues(Class<?> clazz) {
		//Class<?> clazz = BIWaterUserExport.class;
	    //List list = new ArrayList();
	    Field[] fields = clazz.getDeclaredFields();
	    Map<String,String> map = new HashMap<String,String>();
	    for(int i=0;i<fields.length;i++){
            
            /*if(logger.isInfoEnabled()){
                logger.info("字段名称为："+fields[i].getName());
                logger.info("字段类型为："+fields[i].getType().getName());
            }*/
           
            //判断需要打印的列头数量
            try {
                String methodName = "get"+StringUtils.capitalize(fields[i].getName());
                String setMethodName = "set"+StringUtils.capitalize(fields[i].getName());
                Method method = clazz.getMethod(methodName);
				/* Java Package.getAnnotation()
				 * 返回该元素的指定类型的注释，如果是这样的注释，否则返回null 
				 * */
                Column annotation4Column = method.getAnnotation(Column.class);
                if(annotation4Column != null){
                	map.put(annotation4Column.comment(), setMethodName);
                    //Map<String,String> map = new HashMap<String,String>();
                    //map.put(fields[i].getName(), annotation4Column.comment());
                    //if(isDisplayColumn(fields[i].getName(),list))
                        //columnList.add(map);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
		return map;
	}
	
	private static boolean isDisplayColumn(String columnName,List list){
        if(list.size() == 0) return true;
        return list.contains(columnName);
    }
}
