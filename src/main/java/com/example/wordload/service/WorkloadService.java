package com.example.wordload.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.wordload.AdditionFunc.MyToken;
import com.example.wordload.entites.*;
import com.example.wordload.mapper.ApiMapper;
import com.example.wordload.mapper.WorkloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkloadService {
    @Autowired
    WorkloadMapper workloadMapper;

    @Autowired
    ApiMapper apiMapper;
    /**
     * 根据token返回一个UserInformation实例 若未登录或登陆过期或未验证则返回null
     * @param token
     * @return
     */
    private UserInformation ifLogin(String token){
        MyToken myToken=MyToken.valid(token);
        if (myToken!=null){
            UserInformation user= JSON.parseObject(myToken.getUserId(),UserInformation.class);
            if (apiMapper.ifVerified(user.getUserId())!=1){
                return null;
            }else{
                return user;
            }
        }
        else {
            return null;
        }
    }


    public int addWork(String token, String workName, int hour, Date deadline) {
        UserInformation user=ifLogin(token);
        if (user!=null){
            workloadMapper.addWork(user.getUserId(),workName,hour,deadline,new java.sql.Date(System.currentTimeMillis()));
            return 0;
        }
        else {
            return 601;
        }
    }

    public int finishWork(String token, long workId) {
        int code=0;
        UserInformation user=ifLogin(token);
        if (user!=null){
            try {
                workloadMapper.finishWork(workId);
                return 0;
            }catch (Exception e){
                e.printStackTrace();
                return -1;
            }
        }
        return 601;
    }

    public int modifyWork(String token,long workId, String workName, int hour, Date deadline) {
        UserInformation user=ifLogin(token);

        //需检测workid对应工作量与用户是否匹配
        if (user!=null){
            if (false) return 601;
        }else {
            return 601;
        }
        try {
            workloadMapper.modifyWork(workName,hour,deadline,workId);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int delectWork(String token,long workId){
        UserInformation user=ifLogin(token);
        //需检测workId对应工作量与用户是否匹配
        if (user!=null){
            if (false) return 601;
        }else {
            return 601;
        }
        try {
            workloadMapper.deleteWork(workId);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public OutputInformation getWork(String token, int month, int year, int page) {
        UserInformation user=ifLogin(token);
        int num,totalPage;
        int startWorkload=(page-1)*10;
        if (user==null){
            return new OutputInformation(601);
        }
        Date date=new Date(System.currentTimeMillis());
        if (month==-1){
            month=date.getMonth();
        }
        if (year==-1){
            year=date.getYear();
        }
        try {
            List<Workload> workloadList=workloadMapper.getWork(user.getUserId(),month, year);
            num=workloadList.size();
            totalPage=(num-1)/10+1;
            JSONObject outputData=new JSONObject();
            outputData.put("num",num);
            outputData.put("totalPage",totalPage);
            outputData.put("page",page);
            List<Workload> requiredPage=new ArrayList<>();
            for (int i=0;i<10&&startWorkload<num;i++,startWorkload++){
                Workload w=workloadList.get(startWorkload);
                requiredPage.add(i,w);
            }
            outputData.put("workload",requiredPage);
            return new OutputInformation(0,"success",outputData);
        }catch (Exception e){
            e.printStackTrace();
            return new OutputInformation(-1);
        }
    }


    public OutputInformation getHour(String token) {
        UserInformation user=ifLogin(token);
        //需检测workId对应工作量与用户是否匹配
        if (user==null) {
            return new OutputInformation(601);
        }
        try {
            List<AmountW>workloadList=workloadMapper.getAll(user.getUserId());
            return new OutputInformation(0,"success",workloadList);
        }catch (Exception e){
            e.printStackTrace();
            return new OutputInformation(-1);
        }
    }

    public OutputInformation getHour(String token, int year) {
        UserInformation user=ifLogin(token);
        //需检测workId对应工作量与用户是否匹配
        if (user==null) {
            return new OutputInformation(601);
        }
        try {
            List<YearW>workloadList=workloadMapper.getHour(user.getUserId(),year);
            return new OutputInformation(0,"success",workloadList);
        }catch (Exception e){
            e.printStackTrace();
            return new OutputInformation(-1);
        }
    }
}
