package com.example.wordload.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.wordload.entites.UserInformation;
import com.example.wordload.entites.Workload;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.sql.Date;

/**
 * @author 19892
 */
@Repository
public interface ApiMapper {

    /**
     * 检验是否已验证通过 1表示是 0表示否
     * @param userId
     * @return
     */
    @Select("SELECT verified FROM `userinformation` WHERE userId = #{userId} limit 1")
    int ifVerified(@Param("userId")String userId);

    /**
     * 获取用户信息 返回一个含有可修改的变量的用户对象
     * @param userId
     * @return
     */
    @Select("SELECT * FROM `userinformation` WHERE userId = #{userId} limit 1" )
    JSONObject getUserInfo(String userId);


    /**
     * 修改指定字段的数据
     * @param name 字段名
     * @param data 更改的内容
     * @param userId 用户id
     * @return
     */
    @Update("Update `userinformation` SET ${name} = #{data} WHERE `userId`=#{userId}")
    int modifyUserInfo(String name, String data,String userId);


    /**
     * 在数据库中添加用户 默认设verified为0
     * @param userId
     * @return
     */
    @Insert("Insert  Into `userinformation` (`userId`,`verified`) VALUES (#{userId},0)")
    int insertUser(@Param(value = "userId" )String userId);

}