package cn.njcit.web.controller.manager;

import cn.njcit.common.util.CommonUtil;
import cn.njcit.common.util.UID;
import cn.njcit.domain.user.User;
import cn.njcit.web.controller.user.Colleage;
import cn.njcit.web.controller.user.TClass;
import cn.njcit.web.controller.user.TClassQueryForm;
import cn.njcit.web.service.manager.WebManagerService;
import cn.njcit.web.service.user.WebUserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 学员管理，班级管理，信息导入
 * Created by YK on 2014/9/26.
 */
@Controller
@RequestMapping("/webManager")
public class WebManagerController {
    @Autowired
    private WebManagerService webManagerService;
    @Autowired
    private WebUserService webUserService;

    /**
     * 班级列表界面
     * @return
     */
    @RequestMapping("classManageListIndex")
    public String classManageListIndex(){
        return "/web/manager/classIndex";
    }

    /**
     * 班级列表数据
     * 学管处，只能查看自己学院的班级
     * 超级管理员 ，则包括全部
     * @return
     */
    @RequestMapping("classManageList")
    public @ResponseBody Map classManageList(TClassQueryForm classQueryForm,HttpServletRequest request,HttpServletResponse response){
        User sessionUser = (User) request.getSession().getAttribute("user");
        classQueryForm.initDataTable(request);
        List<TClass> classList = webManagerService.getClassManageList(classQueryForm, sessionUser);
        int  total  = webManagerService.getClassManageCount(classQueryForm, sessionUser);
        return CommonUtil.reurnDataTable(total, classList, null);
    }


    /**
     *新增班级
     * @return
     */
    @RequestMapping(value = "addClass",method = RequestMethod.POST)
    public @ResponseBody Map addClass(TClass tClass,HttpServletRequest request,HttpServletResponse response){
        try{
            int count = webManagerService.addClass(tClass);
            if(count>0){
                return CommonUtil.ajaxSuccess(true);
            }else{
                return CommonUtil.ajaxFail(null,"班级添加失败，请联系管理员");
            }
        } catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该班级已存在，不能重复添加");
        }
    }


    /**
     *编辑班级
     * @return
     */
    @RequestMapping("editClass")
    public @ResponseBody Map editClass(TClass tClass,HttpServletRequest request,HttpServletResponse response){
        try{
            int count = webManagerService.editClass(tClass);
            if(count>0){
                return CommonUtil.ajaxSuccess(true);
            }else{
                return CommonUtil.ajaxFail(null,"班级编辑失败，请联系管理员");
            }
        } catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该班级已存在，不能重复添加");
        }
    }



    /**
     *删除班级
     * 删除班级时要检查 ，该班级是否有学生 ，
     * 如果有学生，则不允许 删除
     * @return
     */
    @RequestMapping("deleteClass")
    public @ResponseBody Map deleteClass(TClass tClass,HttpServletRequest request,HttpServletResponse response){
        int count = webManagerService.deleteClass(tClass);
        if(count>0){
            return CommonUtil.ajaxSuccess(true);
        }else{
            return CommonUtil.ajaxFail(null,"班级编辑失败，请联系管理员");
        }
    }


    /**
     *智能生成班级数据
     * 依据某个年级生成另一个年级的  班级  数据
     * 管理员和学管处 都调用这个接口
     * 管理员 可以生成 所有系  的  数据
     * 学管处 则智能 生成 所负责的系 的 数据
     * @return
     */
    @RequestMapping("generateClass")
    public @ResponseBody Map generateClass(HttpServletRequest request,HttpServletResponse response){

        return null;
    }



    /**
     *学院列表界面跳转
     * @return
     */
    @RequestMapping("colleageManageListIndex")
    public String colleageManageListIndex(HttpServletRequest request,HttpServletResponse response){

        return "/web/manager/colleageIndex";
    }


    /**
     *学院列表数据
     * @return
     */
    @RequestMapping("colleageManageList")
    public @ResponseBody Map colleageManageList(ColleageQueryForm colleageQueryForm, HttpServletRequest request,HttpServletResponse response){
        User sessionUser = (User) request.getSession().getAttribute("user");
        colleageQueryForm.initDataTable(request);
        List<Colleage> colleageList = webManagerService.getColleageManageList(colleageQueryForm, sessionUser);
        int  total  = webManagerService.getColleageManageCount(colleageQueryForm, sessionUser);
        return CommonUtil.reurnDataTable(total, colleageList, null);
    }

    /**
     *编辑学院
     * @return
     */
    @RequestMapping(value = "editColleage",method = RequestMethod.POST)
    public @ResponseBody Map editColleage(Colleage colleage,HttpServletRequest request,HttpServletResponse response){
        try{
            int count = webManagerService.editColleage(colleage);
            if(count>0){
                return CommonUtil.ajaxSuccess(true);
            }else{
                return CommonUtil.ajaxFail(null,"学院编辑失败，请联系管理员");
            }
        } catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该系号已存在，不能重复添加");
        }

    }



    /**
     *删除学院
     * 如果该学院下面有班级，则不允许删除
     * @return
     */
    @RequestMapping("deleteColleage")
    public @ResponseBody Map deleteColleage(Colleage colleage ,HttpServletRequest request,HttpServletResponse response){
        List<TClass> classList = webUserService.getClasses(colleage);

        if(classList==null || classList.size()==0){
            int count = webManagerService.deleteColleage(colleage);
            if(count>0){
                return CommonUtil.ajaxSuccess(true);
            }else{
                return CommonUtil.ajaxFail(null,"学院删除失败，请联系管理员");
            }
        }else {
            return CommonUtil.ajaxFail(null,"该学院下已经有班级，无法删除");
        }
    }

    /**
     *添加学院
     *如果该学院下面有班级，则不允许删除
     * @return
     */
    @RequestMapping("addColleage")
    public @ResponseBody Map addColleage(Colleage colleage ,HttpServletRequest request,HttpServletResponse response){
        try{
            int count = webManagerService.addColleage(colleage);
            if(count>0){
                return CommonUtil.ajaxSuccess(true);
            }else{
                return CommonUtil.ajaxFail(null,"学院添加失败，请联系管理员");
            }
        } catch(DuplicateKeyException dke){//插入重复数据学号
            return CommonUtil.ajaxFail(null,"该系号已存在，不能重复添加");
        }
    }



    /**
     *信息导入列表界面
     * @return
     */
    @RequestMapping("informationImportIndex")
    public String informationImportIndex(HttpServletRequest request,HttpServletResponse response){
        return "/web/manager/infomationImportIndex";
    }


    /**
     *信息导入相关的模板下载和样例数据下载或者是 错误文件
     * @return
     */
    @RequestMapping("download")
    public void getTemplate(HttpServletRequest request,HttpServletResponse response) throws IOException {
            String type = request.getParameter("type");
            String fileName = request.getParameter("fileName");
            OutputStream os = response.getOutputStream();
            try {
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=dict.txt");
                response.setContentType("application/octet-stream; charset=utf-8");
                os.write(FileUtils.readFileToByteArray(new File("    ")));//TODO 此处填写文件路径
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }
    }




    /**
     *导入 班级
     * @return
     */
    @RequestMapping(value = "importClass",method = RequestMethod.POST)
    public ModelAndView importClass(HttpServletRequest request,HttpServletResponse response, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        ModelAndView mav = new ModelAndView("/web/manager/infomationImportIndex");
        // 获得文件名：
        String webRootPath = request.getSession().getServletContext().getRealPath("/");
        String filename = multipartFile.getOriginalFilename();
        String tomcatBinPath = System.getProperty("user.dir");
        File tomcatBinFile = new File(tomcatBinPath);
        File tomcatBinParentFile = tomcatBinFile.getParentFile();
        File tomcatUploadFile = new File(tomcatBinParentFile.getPath()+File.separator+"uploadDir");
        String filePath = tomcatUploadFile.getPath()+File.separator+ UID.getUID()+filename;
        File source = new File(filePath);
        if(filePath.endsWith("xls")||filename.endsWith("xlsx")){//符合文件名后缀的文件
//        保存到本地
            multipartFile.transferTo(source);
            String errorFileName = webManagerService.importClass(filePath,webRootPath);
            if(errorFileName!=null){//存在错误数据，导出 用户修改
                mav.addObject("errorCode","002");//存在错误数据
                mav.addObject("fileName",errorFileName);
                mav.addObject("errorMsg","文件中包含错误数据");
            }
        }else{
            mav.addObject("errorCode","001");//文件不符合要求
            mav.addObject("errorMsg","文件名不合法，只支持Excel");//文件不符合要求

        }
        mav.addObject("refresh",true);
        return mav;
    }




    /**
     *导入  学生
     * @return
     */
    @RequestMapping(value = "importStudent",method = RequestMethod.POST)
    public ModelAndView importStudent(HttpServletRequest request,HttpServletResponse response,@RequestParam("file") MultipartFile multipartFile) throws IOException {
        ModelAndView mav = new ModelAndView("/web/manager/infomationImportIndex");
        // 获得文件名：
        String webRootPath = request.getSession().getServletContext().getRealPath("/");
        String filename = multipartFile.getOriginalFilename();
        String tomcatBinPath = System.getProperty("user.dir");
        File tomcatBinFile = new File(tomcatBinPath);
        File tomcatBinParentFile = tomcatBinFile.getParentFile();
        File tomcatUploadFile = new File(tomcatBinParentFile.getPath()+File.separator+"uploadDir");
        String filePath = tomcatUploadFile.getPath()+File.separator+ UID.getUID()+filename;
        File source = new File(filePath);
        if(filePath.endsWith("xls")||filename.endsWith("xlsx")){//符合文件名后缀的文件
//        保存到本地
            multipartFile.transferTo(source);
            String errorFileName = webManagerService.importStudent(filePath,webRootPath);
            if(errorFileName!=null){//存在错误数据，导出 用户修改
                mav.addObject("errorCode","002");//存在错误数据
                mav.addObject("fileName",errorFileName);
                mav.addObject("errorMsg","文件中包含错误数据");
            }
        }else{
            mav.addObject("errorCode","001");//文件不符合要求
            mav.addObject("errorMsg","文件名不合法，只支持Excel");//文件不符合要求

        }
        mav.addObject("refresh",true);
        return mav;
    }

    /**
     *导入  老师
     * @return
     */
    @RequestMapping(value = "importTeacher",method = RequestMethod.POST)
    public ModelAndView importTeacher(HttpServletRequest request,HttpServletResponse response,@RequestParam("file") MultipartFile multipartFile) throws IOException {
        ModelAndView mav = new ModelAndView("/web/manager/infomationImportIndex");
        // 获得文件名：
        String webRootPath = request.getSession().getServletContext().getRealPath("/");
        String filename = multipartFile.getOriginalFilename();
        String tomcatBinPath = System.getProperty("user.dir");
        File tomcatBinFile = new File(tomcatBinPath);
        File tomcatBinParentFile = tomcatBinFile.getParentFile();
        File tomcatUploadFile = new File(tomcatBinParentFile.getPath()+File.separator+"uploadDir");
        String filePath = tomcatUploadFile.getPath()+File.separator+ UID.getUID()+filename;
        File source = new File(filePath);
        if(filePath.endsWith("xls")){//符合文件名后缀的文件
//        保存到本地
            multipartFile.transferTo(source);
            String errorFileName = webManagerService.importTeacher(filePath,webRootPath);
            if(errorFileName!=null){//存在错误数据，导出 用户修改
                mav.addObject("errorCode","002");//存在错误数据
                mav.addObject("fileName",errorFileName);
                mav.addObject("errorMsg","文件中包含错误数据");
            }
        }else{
            mav.addObject("errorCode","001");//文件不符合要求
            mav.addObject("errorMsg","文件名不合法，暂时只支持Excel-2003");//文件不符合要求

        }
        mav.addObject("refresh",true);
        return mav;
    }



}
