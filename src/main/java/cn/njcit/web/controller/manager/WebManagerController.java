package cn.njcit.web.controller.manager;

import cn.njcit.web.service.manager.WebManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 班级列表界面
     * @return
     */
    @RequestMapping("classManageListIndex")
    public String classManageListIndex(){
        return "";
    }

    /**
     * 班级列表数据
     * @return
     */
    @RequestMapping("classManageList")
    public @ResponseBody Map classManageList(HttpServletRequest request,HttpServletResponse response){

        return null;
    }


    /**
     *新增班级
     * @return
     */
    @RequestMapping("addClass")
    public @ResponseBody Map addClass(HttpServletRequest request,HttpServletResponse response){

        return null;
    }


    /**
     *编辑班级
     * @return
     */
    @RequestMapping("editClass")
    public @ResponseBody Map editClass(HttpServletRequest request,HttpServletResponse response){

        return null;
    }



    /**
     *删除班级
     * 删除班级时要检查 ，该班级是否有学生 ，
     * 如果有学生，则不允许 删除
     * @return
     */
    @RequestMapping("deleteClass")
    public @ResponseBody Map deleteClass(HttpServletRequest request,HttpServletResponse response){

        return null;
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

        return null;
    }


    /**
     *学院列表数据
     * @return
     */
    @RequestMapping("colleageManageList")
    public @ResponseBody Map colleageManageList(HttpServletRequest request,HttpServletResponse response){

        return null;
    }

    /**
     *编辑学院
     * @return
     */
    @RequestMapping("editColleage")
    public @ResponseBody Map editColleage(HttpServletRequest request,HttpServletResponse response){

        return null;
    }



    /**
     *删除学院
     * 如果该学院下面有班级，则不允许删除
     * @return
     */
    @RequestMapping("deleteColleage")
    public @ResponseBody Map deleteColleage(HttpServletRequest request,HttpServletResponse response){

        return null;
    }



    /**
     *信息导入列表界面
     * @return
     */
    @RequestMapping("informationImportIndex")
    public String informationImportIndex(HttpServletRequest request,HttpServletResponse response){

        return null;
    }


    /**
     *信息导入相关的模板下载和样例数据下载
     * @return
     */
    @RequestMapping("getTemplate")
    public void getTemplate(HttpServletRequest request,HttpServletResponse response){

//        return null;
    }


    /**
     *导入 班级
     * @return
     */
    @RequestMapping("importClass")
    public @ResponseBody Map importClass(HttpServletRequest request,HttpServletResponse response){

        return null;
    }

    /**
     *导入  学生
     * @return
     */
    @RequestMapping("importStudent")
    public @ResponseBody Map importStudent(HttpServletRequest request,HttpServletResponse response){

        return null;
    }

    /**
     *导入  老师
     * @return
     */
    @RequestMapping("importTeacher")
    public @ResponseBody Map importTeacher(HttpServletRequest request,HttpServletResponse response){

        return null;
    }



}