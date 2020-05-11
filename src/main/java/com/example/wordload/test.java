package com.example.wordload;

import com.alibaba.fastjson.JSON;
import com.example.wordload.entites.OutputInformation;
import com.example.wordload.entites.UserInformation;

public class test {
     public static void main(String[] args) {
         UserInformation userInformation=new UserInformation("a","b","c",566);
         String JsonStr= "{\"academy\":\"软件学院\",\"campus\":\"\",\"department\":\"\",\"flag\":0,\"position\":\"\",\"userId\":\"201900302046\",\"userName\":\"李卓栋\",\"verified\":1}";
         UserInformation fromJson=JSON.parseObject(JsonStr,UserInformation.class);
         System.out.println(fromJson.getUserId());
         System.out.println(fromJson.toString());

    }
}
