package cn.njcit.web.domain;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by YK on 2014/9/12.
 */
public class DataTableForm implements Serializable {
    private Integer start;//开始条数
    private Integer length;//结束条数
    private Integer end;
    private String orderByName; //排序的字段名称
    private String orderBydesc;  //此值是asc 或 desc

    public DataTableForm initDataTable(HttpServletRequest request){
        String orderColumnIndex = request.getParameter("order[0][column]");
        String orderByName = request.getParameter("columns["+orderColumnIndex+"][data]");
        this.orderByName = orderByName;
        this.orderBydesc = request.getParameter("order[0][dir]");
        return this;
    }


    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getOrderByName() {
        return orderByName;
    }

    public void setOrderByName(String orderByName) {
        this.orderByName = orderByName;
    }

    public String getOrderBydesc() {
        return orderBydesc;
    }

    public void setOrderBydesc(String orderBydesc) {
        this.orderBydesc = orderBydesc;
    }
}
