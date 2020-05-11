package com.example.wordload.service;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.wordload.AdditionFunc.Crawler;
import com.example.wordload.AdditionFunc.MyToken;
import com.example.wordload.entites.OutputInformation;
import com.example.wordload.entites.UserInformation;
import com.example.wordload.mapper.ApiMapper;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;



import java.io.IOException;
import java.util.Date;


/**
 * @author 19892
 */
@Service
public class ApiService {
    @Autowired
    ApiMapper apiMapper;

    @Value("${url.loginApi}")
    private String loginApi;

    @Value("${url.sdInfApi}")
    private String sdInfApi;

    @Value("${expireTime}")
    private long expireTime;

    @Value(("${checkString}"))
    private String checkString;

    /**
     * 调用统一认证接口登录
     * @param userId
     * @param psw
     * @return
     * @throws IOException
     */
    public OutputInformation login(String userId, String psw) throws IOException {
        OutputInformation outputInformation=new OutputInformation(0);

        //登陆并获取token、状态码
        Crawler loginCrawler=new Crawler(userId,psw,loginApi);
        int code=loginCrawler.getCode();
        String token=loginCrawler.getToken();

        JSONObject out=new JSONObject();

        //如果登陆成功 则查询是否已载入数据库 若未载入则载入数据库 并且设verified为0 若已载入则查询verified字段
        if (code==0){
            int verified=0;
            JSONObject s=apiMapper.getUserInfo(userId);
            UserInformation object=JSONObject.parseObject(s.toJSONString(),UserInformation.class);
            if (object.getUserId()==null){
                apiMapper.insertUser(userId);
            }else {
                verified=apiMapper.ifVerified(userId);
            }

            //无论是否验证成功 返回一个token（含有从sdInfApi中获取的学生信息 这部分信息不存入数据库 而是储存在token中 这样教务处信息更新后 再在此处重新登录既可同步更新）
            Crawler sdInfCrawler = new Crawler(sdInfApi);
            JSONObject userInf = sdInfCrawler.getUserInformation(token);
            userInf.put("verified",verified);
            UserInformation user=new UserInformation(userInf);
            MyToken myToken=new MyToken(user.toString(),System.currentTimeMillis()+expireTime);
            out.put("verified",verified);
            out.put("token",myToken.generate());
            outputInformation.setData(out);
        }


        //若未成功 说明账号密码错误
        else if (code==1){
            outputInformation=new OutputInformation(1,"账号或密码错误");
        }

        //其他未知错误
        else {
            outputInformation=new OutputInformation(-1);
        }
        return outputInformation;
    }




    public OutputInformation getUserInfo(String token) {
        if (token!=null) {
            UserInformation user=ifLogin(token);
            if (user!=null){

                JSONObject s=apiMapper.getUserInfo(user.getUserId());
                UserInformation user1=JSONObject.parseObject(s.toJSONString(),UserInformation.class);

                user1.combine(user);
                return new OutputInformation(0,"success",user1);
            }
            else {
                return new OutputInformation(601);
            }
        }
        else {
            return new OutputInformation(601);
        }
    }

    public int modifyUserInfo(String name, String data,String token) {
        UserInformation user=ifLogin(token);
        int code;
        if (user.getUserId()!=null){
            code=apiMapper.modifyUserInfo(name,data,user.getUserId());
        }else {
            code=1000;
        }
        return code;
    }

    /**
     * 检验验证码（需先登录）
     * @param inviteCode
     * @param token
     * @return
     */
    public OutputInformation checkString(String inviteCode, String token) {
        //先检查是否登陆 若未登陆返回601
        MyToken myToken=MyToken.valid(token);
        if (myToken!=null){
            UserInformation user= JSON.parseObject(myToken.getUserId(),UserInformation.class);
            if (user!=null){
                int code=checkString.equals(inviteCode)?0:1;

                if (code==0){
                    apiMapper.modifyUserInfo("verified","1",user.getUserId());
                    return new OutputInformation(code);
                } else{
                    return new OutputInformation(1,"验证码错误");
                }
            }else {
                return new OutputInformation(601);
            }
        }else {
            return new OutputInformation(601);
        }
    }

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

}
