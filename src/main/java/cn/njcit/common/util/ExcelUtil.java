package cn.njcit.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 该工具类主要用来操作Excel。
 * 包括：
 * ① 将Excel的内容读入到数据库
 * Created by YK on 2014-06-12.
 *
 */
public class ExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static final String PROPERTY_FILE_PATH = "";
    private static final String EXCEL_FILE_PATH = "";

    /**
     * 将Excel中的所有行转换成List
     * @param excelFileName excel文件的名称
     * @param propertyName  存放字段对应关系的文件名
     * @param startRow 读取excel的起始行，从0开始。该值的作用是，针对某些excel有表头。而表头并不是真正我们需要的数据
     * @param classType 需要实例化的类对象
     * @param <T>
     * @return
     */
    public static <T> List<T> convertAllExcelToList(String excelFileName,String propertyName,int startRow,Class<T> classType){
        POIFSFileSystem fs;
        HSSFWorkbook wb;
        HSSFSheet sheet;
        HSSFRow row;
        ArrayList<T> excelList = new ArrayList<T>();
        //根据
        try {
            //①装载property文件的内容
            Properties  properties = new Properties ();
            properties.load(new FileInputStream(PROPERTY_FILE_PATH+propertyName));
            int propertyColNum = properties.size();
            if(propertyColNum!=0){
                //由于在配置properties的时候，有可能不是从0开始配置的，有可能是从1开始，
                //为了处理这种情况，所以下面做了个判断
                int beginIndex = 0;//读取的第一个列的序列
                int endIndex = properties.size();//读取excel的最后一列的序列
                String  propertyValue = properties.getProperty("0");
                if(StringUtils.isEmpty(propertyValue)){
                    beginIndex=1;
                    endIndex+=1;
                }
                FileInputStream excelFis = new FileInputStream(new File(EXCEL_FILE_PATH+excelFileName));
                fs = new POIFSFileSystem(excelFis);
                wb = new HSSFWorkbook(fs);
                sheet = wb.getSheetAt(0);//获得第一个sheet，该方法不能处理多个sheet的情况。
                // 得到Excel数据的总行数
                int rowNum = sheet.getLastRowNum();
                row = sheet.getRow(0);
                int colNum = row.getPhysicalNumberOfCells();//实际一行有多少列
                if(colNum!=propertyColNum){
                    logger.error(propertyName+":property配置文件的行的个数("+propertyColNum+")和excel中列的个数("+colNum+")不匹配");
                    return null;
                }else{
                    //②将每一行数据初始化到对象中
                    for (int i = startRow; i <= rowNum; i++) {
                        T instance = classType.newInstance();
                        row = sheet.getRow(i);
                        for(int j=0;j<colNum;j++){
                              String excelValue  = getStringCellValue(row.getCell((short) j));
                              BeanUtils.setProperty(instance,properties.getProperty((j+beginIndex)+""),excelValue);
                        }
                        //③将每一个对象添加到列表中
                        excelList.add(instance);
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return excelList;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }


    public static void main(String[] args) {


        System.out.println(convertAllExcelToList("abc.xlsx","abc.properties",1,Person.class));

    }



}
