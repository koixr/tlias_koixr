package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {
    //------------------------原始分页查询实现--------------------------------
//    /**
//     * 查询所有员工
//     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
//    public Long count();
//
//    /**
//     *  员工展示
//     *  拼接注意空格拼接
//     */
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id=d.id " +
//            "order by e.update_time desc limit #{start},#{pageSize} ")
//    public List<Emp> list(Integer start,  Integer pageSize);


    /**
     * 查询所有员工--使用PageHelper插件依赖
     * 注意事项： 1.一定不能加分号
     *              2.只会对紧跟在其后的第一条sql语句进行操作
     * 错误找Cause 500是服务器出错   端口被占用
     * 自动增强为count（），与limit  语句
     */
    //@Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id=d.id order by e.update_time desc ")
    //#  参数: name, gender, begin, end 无颜色选择上下文 注入语言MySQL
    //# #{}在sql中使用占位符被替换为？，？出现在单引号中被解析为普通字符，无占位含义，$符号作为拼接符号，一般是非预编译的sql，性能不高，一般不使用，故使用concat函数拼接
    //400是请求错误，日期format是yyyy-MM-dd，月日写成了1-1是错的，01-01是对的
    //public List<Emp> list(String name, Integer  gender, LocalDate begin, LocalDate end);

    //使用动态sql，否则写死条件始终三个条件查询，无条件相当于为null
    //使用<where>和<if>标签，<where>标签会自动拼接where，<if>标签会自动拼接条件
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 保存员工信息
     * @param emp
     */
    //从数据库中获取到主键值
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            " values(#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    /**
     * 删除员工信息
     */
    void deleteById(List<Integer> ids);


    /**
     * 根据id查询员工信息
     */
    Emp getById(Integer id);

    /**
     * 更新员工信息
     */
    void updateById(Emp emp);

    /**
     * 统计员工职位人数
     */
    /*可能存在MybatisX插件误报 不用在意 或者在设置中编辑器中找到Mybatis选项关闭 或者添加上mapkey注解选项无报错不添加也行*/
//    @MapKey("pos")
    List<Map<String,Object>> coutEmpJobData();

    /**
     * 统计员工性别人数
     * @return
     */
//    @MapKey("gender")
    List<Map<String, Object>> coutEmpGenderData();

    /**
     * clazz新增班级中展示班主任姓名，回显数据中的班主任name
     */
    @Select("select * from emp ")
    List<Emp> listAll();

    /**
     * 登录
     */
    @Select("select id,username,name from emp where username=#{username} and password=#{password}")
    Emp getByUsernameAndPassword(Emp emp);
}
