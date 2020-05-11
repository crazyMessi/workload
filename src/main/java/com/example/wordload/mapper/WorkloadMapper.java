package com.example.wordload.mapper;

import com.example.wordload.entites.AmountW;
import com.example.wordload.entites.Workload;
import com.example.wordload.entites.YearW;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * @author 19892
 */
@Repository
public interface WorkloadMapper {
    /**
     * 添加工作量
     * @param workName 工作名称 可用null代替
     * @param hour 时长 单位（h）
     * @param deadline ddl Date格式（yyyy-mm-dd）
     * @param startTime 工作开始的时间，即当前时间
     * @param userId 用户账号
     * @return
     */
    void addWork(@Param("userId") String userId,@Param("workName") String workName, @Param("hour")int hour, @Param("deadline") Date deadline, @Param("startTime") Date startTime);
//
    /**
     * 设置工作已完成
     * @param workId
     * @return
     */
    void finishWork(long workId);


    /**
     * 获取用户当月/某年|月的工作量详情（此处未分页）
     * @param month 默认为本月
     * @param year 默认为本年
     * @return
     */
    List<Workload> getWork(String userId,int month, int year);

    /**
     * 修改工作量
     * @param workName ]
     * @param hour
     * @param deadline
     * @param workId
     */
    void modifyWork(String workName,int hour,Date deadline,long workId);

    /**
     * 删除工作量
     * @param workId
     * @return 成功返回0
     */
    @Delete("delete from `workload` where `workId` =#{workId}")
    int deleteWork(long workId);

    /**
     * 统计页面根据总/年获取时长
     * @param userId
     * @return
     */
    @Select("select year(startTime),sum(`hour`) from `workload` where `userId`=#{userId} group by year(startTime)")
    List<AmountW> getAll(String userId);

    /**
     * 统计页面根据年获取时长
     * @param year
     * @param userId
     * @return
     */
    @Select("select month(startTime),sum(`hour`) from `workload` where `userId`=#{userId} and year(startTime)=#{year} group by month(startTime)" )
    List<YearW> getHour(String userId, int year);


}
