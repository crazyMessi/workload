package com.example.wordload.controller;

import com.example.wordload.entites.OutputInformation;
import com.example.wordload.entites.UserInformation;
import com.example.wordload.service.ApiService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * @author 19892
 */

@RestController
public class ApiController {
    @Autowired
    private ApiService apiService;

    /**
     *
     * @param userId
     * @param password
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/login")
    public OutputInformation login(@RequestParam(value = "userId")String userId, @RequestParam("password")String password) throws IOException {
        return apiService.login(userId,password);
    }

    /**
     *
     * @param inviteCode
     * @return
     */
    @RequestMapping(value = "/judgeInviteCode")
    public OutputInformation judgeInviteCode(@RequestParam(value = "inviteCode")String inviteCode,@RequestHeader(value = "token")String token){
        return apiService.checkString(inviteCode,token);
    }

    @RequestMapping(value = "/getUserInfo")
    public OutputInformation getUserInfo(@RequestHeader(value = "token") String token){
        return apiService.getUserInfo(token);
    }

    @RequestMapping(value = "/modifyUserInfo")
    public OutputInformation modifyUserInfo(@RequestHeader(value = "token")String token,@RequestParam(value = "campus",required = false)String campus,@RequestParam(value = "department",required = false)String department,@RequestParam(value = "position",required = false)String position,@RequestParam(value = "flag",required = false,defaultValue = "0")String flag){
        int code=0;
        String[] infos={campus,department,position,flag};
        String[] name={"campus","department","position","flag"};
        for (int i=0;i<4;i++){
            if (infos[i]!=null){
                code++;
                code-=apiService.modifyUserInfo(name[i],infos[i],token);
            }
        }
        if (code==0){
            return new OutputInformation(code);
        }else if (code<-100){

            return new OutputInformation(601);
        }else {
            return new OutputInformation(-1);
        }
    }

}


