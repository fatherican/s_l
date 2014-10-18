package cn.njcit.web.service.manager.impl;

import cn.njcit.common.constants.AppConstants;
import cn.njcit.common.util.UID;
import cn.njcit.common.util.encrypt.MD5Util;
import cn.njcit.domain.user.User;
import cn.njcit.web.controller.manager.ColleageQueryForm;
import cn.njcit.web.controller.user.*;
import cn.njcit.web.dao.manager.WebManagerDao;
import cn.njcit.web.service.manager.WebManagerService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.*;

/**
 * Created by YK on 2014/9/26.
 */
@Service("managerService")
public class WebManagerServiceImpl implements WebManagerService {


    @Autowired
    private WebManagerDao webManagerDao;


    @Override
    public List<TClass> getClassManageList(TClassQueryForm classQueryForm, User sessionUser) {
        if (initQueryClass(classQueryForm, sessionUser)) return null;
        List<TClass> classList = webManagerDao.getClassManageList(classQueryForm);
        return classList;
    }



    @Override
    public int getClassManageCount(TClassQueryForm classQueryForm, User sessionUser) {
        if (initQueryClass(classQueryForm, sessionUser)) return 0;
        int total = webManagerDao.getClassManageCount(classQueryForm);
        return total;
    }


    private boolean initQueryClass(TClassQueryForm classQueryForm, User sessionUser) {
        if(sessionUser.getRole().intValue() == AppConstants.STUDENT_PIPE_ROLE.intValue()){//学管处只能添加辅导员角色的老师
            classQueryForm.setColleageId(String.valueOf(sessionUser.getColleageId()));
        }else  if(sessionUser.getRole().intValue() == AppConstants.ADMIN_ROLE.intValue()){//学管处只能添加辅导员角色的老师
            classQueryForm.setColleageId(null);
        }else{
            return true;
        }
        return false;
    }


    @Override
    public List<Colleage> getColleageManageList(ColleageQueryForm colleageQueryForm, User sessionUser) {
        List<Colleage> colleageList = webManagerDao.getColleageManageList(colleageQueryForm);
        return colleageList;
    }

    @Override
    public int getColleageManageCount(ColleageQueryForm colleageQueryForm, User sessionUser) {
       int total= webManagerDao.getColleageManageCount(colleageQueryForm);
        return total;
    }

    @Override
    public int editColleage(Colleage colleage) {
        int count = webManagerDao.editColleage(colleage);
        return count;
    }

    @Override
    public int deleteColleage(Colleage colleage) {
        int count =  webManagerDao.deleteColleage(colleage);
        return count;

    }

    @Override
    public int addColleage(Colleage colleage) {
        int count = webManagerDao.addColleage(colleage);
        return count;
    }

    @Override
    public int editClass(TClass tClass) {
        int count = webManagerDao.editClass(tClass);
        return count;
    }

    @Override
    public int deleteClass(TClass tClass) {
        int count = webManagerDao.deleteClass(tClass);
        return count;
    }

    @Override
    public int addClass(TClass tClass) {
        tClass.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        tClass.setClassName(tClass.getClassName().toUpperCase());
        int count = webManagerDao.addClass(tClass);
        return count;
    }


    @Override
    public String importClass(String filePath,String webRootPath) throws IOException {
        String errorFilePath = null;
        String errorFileName = String.valueOf(UID.getUID());
        List<Map<String,String>> errorLine = new ArrayList<Map<String,String>>();
        //校验改行数据是否正确，正确则封装成Tclass，不正确，则保存到错误 文件中，以供用户参考
        int from = 1;
        int num=100;
        int columnNum = 2;//总计有2列
        String globalErrorFileName = null;
        boolean existErrorData = false;
        List<TClass> tClassList = null;
        while(true){
            errorLine.clear();
            if(filePath.endsWith("xls")){
                tClassList =  readExcel2003Class(filePath, from, num, columnNum, errorLine);
            }else if(filePath.endsWith("xlsx")){
                tClassList =  readExcel2003Class(filePath, from, num, columnNum, errorLine);
            }
            //插入数据到数据库
                //梳理数据
            for(TClass tClass : tClassList){
                tClass.setClassName(tClass.getClassName().trim().toUpperCase());
                tClass.setColleageId(String.valueOf((int)(Float.parseFloat(tClass.getColleageId()))));
                tClass.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            }
                //批量插入数据，注意，此时有可能有重复收，所以需要使用ignore关键字
            if(tClassList!=null && tClassList.size()>0){
                webManagerDao.batchInsertClass(tClassList);
            }
            errorFilePath = insertErrorLineToExcel2003(errorFileName,errorLine,columnNum,webRootPath,"classTemplate.xls");
            if(errorFilePath!=null){
                existErrorData=true;
                globalErrorFileName = errorFilePath;
            }
            from+=num;//设置 下一次读取的位置
            if(tClassList.size()<num){//当前Excel读完了，跳出
                break;
            }
        }
        if(existErrorData){
            return globalErrorFileName;
        }
        return null;
    }

    @Override
    public String importStudent(String filePath, String webRootPath) throws IOException {
        String errorFilePath = null;
        String errorFileName = String.valueOf(UID.getUID());
        List<Map<String,String>> errorLine = new ArrayList<Map<String,String>>();
        //校验改行数据是否正确，正确则封装成Tclass，不正确，则保存到错误 文件中，以供用户参考
        int from = 1;
        int num=100;//每次读取多少行
        int columnNum = 4;//总计有4列
        List<Student> studentList = null;
        String globalErrorFileName = null;
        boolean existErrorData = false;
        while(true){
            errorLine.clear();
            if(filePath.endsWith("xls")){
                studentList =  readExcel2003Student(filePath, from, num, columnNum, errorLine);
            }else if(filePath.endsWith("xlsx")){
//                studentList =  readExcel2003Class(filePath, from, num, 2, errorLine);
            }
            //插入数据到数据库
                //梳理数据
            for(Student student : studentList){
                student.setStudentId(UID.getUID() + "");
                student.setPassword(MD5Util.md5Hex("123456"));
                student.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            }
            //批量插入数据，注意，此时有可能有重复收，所以需要使用ignore关键字
            if(studentList!=null && studentList.size()>0){
                webManagerDao.batchInsertStudent(studentList);
            }
            errorFilePath = insertErrorLineToExcel2003(errorFileName,errorLine,columnNum,webRootPath,"studentTemplate.xls");
            if(errorFilePath!=null){
                existErrorData=true;
                globalErrorFileName = errorFilePath;
            }
            from+=num;//设置 下一次读取的位置
            if(studentList.size()<num){//当前Excel读完了，跳出
                break;
            }
        }
        if(existErrorData){
            return globalErrorFileName;
        }
        return null;
    }

    @Override
    public String importTeacher(String filePath, String webRootPath) throws IOException {
        String errorFilePath = null;
        String errorFileName = String.valueOf(UID.getUID());
        List<Map<String,String>> errorLine = new ArrayList<Map<String,String>>();
        //校验改行数据是否正确，正确则封装成Tclass，不正确，则保存到错误 文件中，以供用户参考
        int from = 1;
        int num=100;//每次读取多少行
        int columnNum = 3;//总计有4列
        List<Teacher> teacherList = null;
        String globalErrorFileName = null;
        boolean existErrorData = false;
        while(true){
            errorLine.clear();
            if(filePath.endsWith("xls")){
                teacherList =  readExcel2003Teacher(filePath, from, num, columnNum, errorLine);
            }else if(filePath.endsWith("xlsx")){
//                studentList =  readExcel2003Class(filePath, from, num, 2, errorLine);
            }
            //插入数据到数据库
            //梳理数据
            for(Teacher teacher : teacherList){
                teacher.setTeacherId(UID.getUID() + "");
                teacher.setPassword(MD5Util.md5Hex("123456"));
                teacher.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                teacher.setRole(String.valueOf(AppConstants.INSTRUCTOR__ROLE.intValue()));
            }
            //批量插入数据，注意，此时有可能有重复收，所以需要使用ignore关键字
            if(teacherList!=null && teacherList.size()>0){
                webManagerDao.batchInsertTeacher(teacherList);
            }
            errorFilePath = insertErrorLineToExcel2003(errorFileName,errorLine,columnNum,webRootPath,"teacherTemplate.xls");
            if(errorFilePath!=null){
                existErrorData=true;
                globalErrorFileName = errorFilePath;
            }
            from+=num;//设置 下一次读取的位置
            if(teacherList.size()<num){//当前Excel读完了，跳出
                break;
            }
        }
        if(existErrorData){
            return globalErrorFileName;
        }
        return null;
    }

    private List<Teacher> readExcel2003Teacher(String filePath, int from, int num, int columnNum, List<Map<String, String>> errorLine) throws IOException {
        Map<String,TClass> cacheClass = new HashMap<String, TClass>();//缓存班级名称 和 班级 id
        List<Teacher> teacherList = new ArrayList<Teacher>();
        InputStream is = new FileInputStream(filePath);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt( 0);//读取第一个表格，其他的表格不考虑
        int emptyLinuCount =0;
        int rowNum = from;
        // 循环行Row
        while(true){
            HSSFRow hssfRow = hssfSheet.getRow( rowNum);
            if(hssfRow == null){
                break;
            }
            if(teacherList.size()==num){//已经读取到指定书目的行
                break;
            }
            Teacher teacher = new Teacher();
            // 循环列Cell
            for(int cellNum = 0; cellNum < columnNum; cellNum++){
                HSSFCell hssfCell = hssfRow.getCell( cellNum);
                if(hssfCell == null){
                    continue;
                }
                String value = getValue(hssfCell);
                switch (cellNum){
                    case 0:
                        teacher.setColleageId(value); break;
                    case 1:
                        teacher.setTeacherWorkNum(value.toUpperCase()); break;
                    case 2:
                        teacher.setTeacherName(value.trim()); break;
                }
            }
            String colleageId = String.valueOf((int) (Float.parseFloat(teacher.getColleageId())));
            String compareColleageId = String.valueOf((int)(Float.parseFloat(teacher.getColleageId())));
            Map<String,String> errorItem = null;
            if(StringUtils.isEmpty(teacher.getColleageId())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",teacher.getTeacherWorkNum());
                errorItem.put("cell2",teacher.getTeacherName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::学院为空"):"学院为空");
            }else if(StringUtils.isEmpty(teacher.getTeacherWorkNum())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",teacher.getTeacherWorkNum());
                errorItem.put("cell2",teacher.getTeacherName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::工号为空"):"工号为空");
            }else if(StringUtils.isEmpty(teacher.getTeacherName())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",teacher.getTeacherWorkNum());
                errorItem.put("cell2",teacher.getTeacherName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::教师姓名为空"):"教师姓名为空");
            }else{//没有任何格式错误
                teacherList.add(teacher);
            }

            if(errorItem!=null){//存在错误格式
                errorLine.add(errorItem);
            }

            rowNum++;
        }
        return teacherList;
    }

    /**
     * 将错误的行插入到新的excel中
     * @param errorFileName  错误文件的名字
     * @param errorLine    存放的错误数据
     * @param cellLength    模板中列的个数
     * @param webRootPath
     * @return
     * @throws IOException
     */
    private String insertErrorLineToExcel2003(String errorFileName, List<Map<String, String>> errorLine,int cellLength,String webRootPath,String templateName) throws IOException {
        if(errorLine==null || errorLine.size()==0){//错误的行为空，则 不处理

        }else{
           File file = new File(webRootPath+File.separator+errorFileName+".xls");
           if(!file.exists()){
               FileCopyUtils.copy(new File(webRootPath+File.separator+"resources"+File.separator+"excelTemplate"+File.separator+templateName),file);
           }
            InputStream is = new FileInputStream(file);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt( 0);//读取第一个表格，其他的表格不考虑
            for (int i = 0; i < errorLine.size(); i++) {
                Map<String,String> errorMap = errorLine.get(i);
                //从第几行开始创建新行
                HSSFRow row = hssfSheet.createRow(hssfSheet.getLastRowNum()+1);
                for(int errorMapIndex=0;errorMapIndex<cellLength;errorMapIndex++){
                   String value = errorMap.get("cell"+errorMapIndex);
                    HSSFCell cell = row.createCell(errorMapIndex);
                    cell.setCellValue(value);
                }
                //填充错误说明
                HSSFCell cell = row.createCell(cellLength);
                cell.setCellValue("第"+errorMap.get("errorLine")+"行，"+errorMap.get("errorMsg"));
            }
            FileOutputStream fos =  new FileOutputStream(file);
            hssfWorkbook.write(fos);
            fos.close();
            return errorFileName+".xls";
        }
        return null;
    }


    /**
     * 读取 班级 2003 的 excel
     * @param path
     * @param from
     * @param num
     * @param cellLength
     * @param errorLine
     * @return
     * @throws IOException
     */
    public List<TClass> readExcel2003Class(String path, int from, int num, int cellLength, List<Map<String, String>> errorLine) throws IOException {
        List<TClass> tClassList = new ArrayList<TClass>();
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt( 0);//读取第一个表格，其他的表格不考虑
        int emptyLinuCount =0;
        int rowNum = from;
        // 循环行Row
            while(true){
                HSSFRow hssfRow = hssfSheet.getRow( rowNum);
                if(hssfRow == null){
                    break;
                }
                if(tClassList.size()==num){//已经读取到指定书目的行
                    break;
                }
                TClass tClass = new TClass();
                // 循环列Cell
                for(int cellNum = 0; cellNum < cellLength; cellNum++){
                    HSSFCell hssfCell = hssfRow.getCell( cellNum);
                    if(hssfCell == null){
                        continue;
                    }
                    String value = getValue(hssfCell);
                    switch (cellNum){
                        case 0:
                            tClass.setColleageId(value); break;
                        case 1:
                            tClass.setClassName(value.toUpperCase()); break;
                    }
                }
                String colleageId = String.valueOf((int) (Float.parseFloat(tClass.getColleageId())));
                String compareColleageId = String.valueOf((int)(Float.parseFloat(tClass.getColleageId())));
                Map<String,String> errorItem = null;
                if(StringUtils.isEmpty(tClass.getColleageId())){
                    if(errorItem==null) errorItem = new HashMap<String, String>();
                    errorItem.put("cell0",colleageId);
                    errorItem.put("cell1",tClass.getClassName());
                    errorItem.put("errorLine",String.valueOf(rowNum));
                    errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::学院为空"):"学院为空");
                }else if(StringUtils.isEmpty(tClass.getClassName())){
                    if(errorItem==null) errorItem = new HashMap<String, String>();
                    errorItem.put("cell0",colleageId);
                    errorItem.put("cell1",tClass.getClassName());
                    errorItem.put("errorLine",String.valueOf(rowNum));
                    errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::班级为空"):"班级为空");
                }else if(!tClass.getClassName().startsWith(compareColleageId)){
                    if(errorItem==null) errorItem = new HashMap<String, String>();
                    errorItem.put("cell0",colleageId);
                    errorItem.put("cell1",tClass.getClassName());
                    errorItem.put("errorLine",String.valueOf(rowNum));
                    errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::班级格式不正确，班级名称格式必须以系号开头"):"班级格式不正确，班级名称格式必须以系号开头");
                }else{//没有任何格式错误
                    tClassList.add(tClass);
                }
                if(errorItem!=null){//存在错误格式
                    errorLine.add(errorItem);
                }

                rowNum++;
            }
        return tClassList;
    }





    /**
     * 读取 学生 2003 的 excel
     * @param path
     * @param from  从第几行开始读
     * @param num   读多少航
     * @param cellLength  总共有多少列
     * @param errorLine   存放格式错误的行
     * @return
     * @throws IOException
     */
    public List<Student> readExcel2003Student(String path, int from, int num, int cellLength, List<Map<String, String>> errorLine) throws IOException {
        Map<String,TClass> cacheClass = new HashMap<String, TClass>();//缓存班级名称 和 班级 id
        List<Student> studentList = new ArrayList<Student>();
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt( 0);//读取第一个表格，其他的表格不考虑
        int emptyLinuCount =0;
        int rowNum = from;
        // 循环行Row
        while(true){
            HSSFRow hssfRow = hssfSheet.getRow( rowNum);
            if(hssfRow == null){
                break;
            }
            if(studentList.size()==num){//已经读取到指定书目的行
                break;
            }
            Student student = new Student();
            // 循环列Cell
            for(int cellNum = 0; cellNum < cellLength; cellNum++){
                HSSFCell hssfCell = hssfRow.getCell( cellNum);
                if(hssfCell == null){
                    continue;
                }
                String value = getValue(hssfCell);
                switch (cellNum){
                    case 0:
                        student.setColleageId(value); break;
                    case 1:
                        student.setClassName(value.toUpperCase()); break;
                    case 2:
                        student.setStudentNum(value.toUpperCase()); break;
                    case 3:
                        student.setStudentName(value.trim()); break;
                }
            }
            String colleageId = String.valueOf((int) (Float.parseFloat(student.getColleageId())));
            String compareColleageId = String.valueOf((int)(Float.parseFloat(student.getColleageId())));
            Map<String,String> errorItem = null;
            if(StringUtils.isEmpty(student.getColleageId())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",student.getClassName());
                errorItem.put("cell2",student.getStudentNum());
                errorItem.put("cell3",student.getStudentName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::学院为空"):"学院为空");
            }else if(StringUtils.isEmpty(student.getClassName())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",student.getClassName());
                errorItem.put("cell2",student.getStudentNum());
                errorItem.put("cell3",student.getStudentName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::班级为空"):"班级为空");
            }else if(!student.getClassName().startsWith(compareColleageId)){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",student.getClassName());
                errorItem.put("cell2",student.getStudentNum());
                errorItem.put("cell3",student.getStudentName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::班级格式不正确，班级名称格式必须以系号开头"):"班级格式不正确，班级名称格式必须以系号开头");
            }else if(StringUtils.isEmpty(student.getStudentNum())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",student.getClassName());
                errorItem.put("cell2",student.getStudentNum());
                errorItem.put("cell3",student.getStudentName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::学号不允许为空"):"学号不允许为空");
            }else if(!student.getStudentNum().toUpperCase().startsWith(student.getClassName().toUpperCase())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",student.getClassName());
                errorItem.put("cell2",student.getStudentNum());
                errorItem.put("cell3",student.getStudentName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::学号和班级不匹配"):"学号和班级不匹配");
            }else if(StringUtils.isEmpty(student.getStudentName())){
                if(errorItem==null) errorItem = new HashMap<String, String>();
                errorItem.put("cell0",colleageId);
                errorItem.put("cell1",student.getClassName());
                errorItem.put("cell2",student.getStudentNum());
                errorItem.put("cell3",student.getStudentName());
                errorItem.put("errorLine",String.valueOf(rowNum));
                errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::姓名不允许为空"):"姓名不允许为空");
            }else{//没有任何格式错误,向student中插入classId
                //缓存处理
                TClass tClass = null;
                if(cacheClass.get(student.getClassName().toUpperCase())==null){
                    tClass = webManagerDao.getTClass(student.getClassName().toUpperCase());
                    if(tClass!=null)
                        cacheClass.put(student.getClassName().toUpperCase(),tClass);
                }else{
                    tClass = cacheClass.get(student.getClassName().toUpperCase());
                }
                //判断class是否存在
                if(tClass==null){
                    if(errorItem==null) errorItem = new HashMap<String, String>();
                    errorItem.put("cell0",colleageId);
                    errorItem.put("cell1",student.getClassName());
                    errorItem.put("cell2",student.getStudentNum());
                    errorItem.put("cell3",student.getStudentName());
                    errorItem.put("errorLine",String.valueOf(rowNum));
                    errorItem.put("errorMsg",errorItem.get("errorMsg")!=null?(errorItem.get("errorMsg")+"::姓名不允许为空"):"姓名不允许为空");
                }else{
                    student.setColleageId(tClass.getColleageId());
                    student.setColleageName(tClass.getColleageName());
                    student.setClassId(tClass.getClassId());
                    studentList.add(student);
                }
            }

            if(errorItem!=null){//存在错误格式
                errorLine.add(errorItem);
            }

            rowNum++;
        }
        return studentList;
    }


    public List<TClass> readExcel2007(String path,int from ,int num,int cellLength,List<Map> errorLine){
        return null;
    }





    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell){
        if(hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN){
            return String.valueOf( hssfCell.getBooleanCellValue());
        }else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC){
            return String.valueOf( hssfCell.getNumericCellValue());
        }else{
            return String.valueOf( hssfCell.getStringCellValue());
        }
    }

}
