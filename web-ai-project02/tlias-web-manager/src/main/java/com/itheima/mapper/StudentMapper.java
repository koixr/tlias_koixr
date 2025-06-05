package com.itheima.mapper;

import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    /**
     * 查询学生列表
     * @param studentQueryParam
     * @return
     */
    List<Student> list(StudentQueryParam studentQueryParam);

    /**
     * 删除学生
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 新增学生信息
     */
    @Insert("insert into student(name,no,gender,phone,degree,clazz_id,id_card,is_college,address,graduation_date,create_time,update_time) values(#{name},#{no},#{gender},#{phone},#{degree},#{clazzId},#{idCard},#{isCollege},#{address},#{graduationDate},#{createTime},#{updateTime})")
//    @Insert("insert into student(name,no,gender,phone,degree,clazz_id,id_card,is_college,address,graduation_date,create_time,update_time,iv) values(#{name},#{no},#{gender},#{phone},#{degree},#{clazzId},AES_ENCRYPT(#{idCard}, @key, @iv),#{isCollege},#{address},#{graduationDate},#{createTime},#{updateTime},@iv)")
    void insert(Student student);

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @Select("select * from student where id = #{id}")
//    @Select("select id, name, no, gender, phone, AES_DECRYPT(id_card, 'sjh', iv) AS aes_id_card, is_college, address, degree, graduation_date, clazz_id, violation_count, violation_score, create_time, update_time, iv from student where id = #{id}")
    Student getById(Integer id);

    /**
     * 修改学生信息
     */
    void update(Student student);

    /**
     * 修改学生违纪信息
     */
    @Update("update student set violation_count = #{violationCount},violation_score = #{violationScore},update_time = #{updateTime} where id = #{id}")
    void updateViolation(Student student);

    /**
     * 统计学生学历信息
     */
    List<Map<String, Object>> coutStudentDegreeData();

    /**
     * 统计学生班级人数信息
     */
    List<Map<String, Object>> studentCountData();
}
