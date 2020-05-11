package com.example.wordload.controller;

import com.example.wordload.entites.OutputInformation;
import com.example.wordload.service.WorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * @author 19892
 */
@RestController
public class WorkLoadController {

    private String undefined="undefined";

    @Autowired
    private WorkloadService workloadService;

    @RequestMapping(value = "/addWork")
    public OutputInformation addWork(@RequestHeader(value = "token")String token, @RequestParam(value = "workName",defaultValue = "undefined")String workName, @RequestParam(value = "hour",defaultValue = "-1")int hour, @RequestParam(value = "deadline",required = false) java.sql.Date deadline){
        int code=0;
        if (!("undefined".equals(workName)||hour==-1)){
            code=workloadService.addWork(token,workName,hour,deadline);
        }else {
            code=602;
        }
        return new OutputInformation(code);
    }

    @RequestMapping(value ="/modifyWork")
    public OutputInformation modifyWork(@RequestHeader(value = "token")String  token,@RequestParam(value = "workName",required = false) String workName, @RequestParam(value = "workId",defaultValue = "-1")long workId, @RequestParam(value = "hour",defaultValue = "-1")int hour, @RequestParam(value = "deadline",required = false)Date deadline){
        int code;
        if (workId!=-1){
            code=workloadService.modifyWork(token,workId,workName,hour,deadline);
        }else {
            code=602;
        }
        return new OutputInformation(code);
    }

    @RequestMapping(value = "finishWork")
    public OutputInformation finishWork(@RequestHeader(value = "token")String token, @RequestParam(value = "workId")long workId){
        int code;
        if (workId!=-1){
            code=workloadService.finishWork(token,workId);
        }else {
            code=602;
        }
        return new OutputInformation(code);
    }

    @RequestMapping(value = "deleteWork")
    public OutputInformation deleteWork(@RequestHeader(value = "token")String token,@RequestParam(value = "workId",defaultValue = "-1")long workId){
        int code;
        if (workId!=-1){
            code=workloadService.delectWork(token,workId);
        }else {
            code=602;
        }
        return new OutputInformation(code);
    }

    @RequestMapping(value = "getWork")
    public OutputInformation geWork(@RequestHeader(value = "token")String token,@RequestParam(value = "month",defaultValue = "-1")int month,@RequestParam(value = "year",defaultValue = "-1")int year,@RequestParam(value = "page",defaultValue = "-1")int page){

        if (page!=-1){
            return workloadService.getWork(token,month,year,page);
        }else {
            return new OutputInformation(602);
        }
    }

    @RequestMapping(value="getHour")
    public OutputInformation getHour(@RequestHeader(value = "token")String token,@RequestParam(value = "year",defaultValue = "-1")int year){
        if (year==-1){
            return workloadService.getHour(token);
        }else {
            return workloadService.getHour(token,year);
        }
    }

}
