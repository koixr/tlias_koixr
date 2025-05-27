package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClazzMapper {

    /**
     * 查询所有班级信息
     */
     public List<Clazz> list(ClazzQueryParam clazzQueryParam);

     /**
     * 根据id查询班级信息
     */
     @Delete("delete from clazz where id = #{id}")
     void deleteById(Integer id);

     /**
     * 添加班级信息
     */
     void save(Clazz clazz);

     /**
      *
      */
     @Select("select * from clazz where id = #{id}")
     Clazz getById(Integer id);

     /**
     * 修改班级信息
     */
     void updateById(Clazz clazz);

     /**
     * 查询所有班级信息
     */
     @Select("select * from clazz")
     List<Clazz> listAll();
}
