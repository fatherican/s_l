package cn.njcit.domain.user;

/**
 * Created by YK on 2014-06-15.
 */
public class Student extends User {
    private Integer classId;//班级ID
    private Integer professionalId;//专业Id
    private Integer colleageId;//学院ID

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Integer professionalId) {
        this.professionalId = professionalId;
    }

    public Integer getColleageId() {
        return colleageId;
    }

    public void setColleageId(Integer colleageId) {
        this.colleageId = colleageId;
    }
}
