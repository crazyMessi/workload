package com.example.wordload.AdditionFunc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 19892
 */


public class Crawler {

    private String url;
    private String u;
    private String p;

    public Crawler(String u, String p,String url){
        this.u=u;
        this.p=p;
        this.url=url;
    }

    public Crawler(String u, String p){
        this.u=u;
        this.p=p;
    }

    public Crawler(String url) {
        this.url=url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Document doget() throws IOException {
        final int MAX =10;
        int time =0;
        Document doc =null;
        while (time<MAX){
            doc = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(1000*30)
                    .data("p",p)
                    .data("u",u)
                    .post();
            return doc;
        }
        return doc;
    }
    public Document doget(String token) throws IOException {
        final int MAX =10;
        int time =0;
        Document doc =null;
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        while (time<MAX){
            doc = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(1000*30)
                    .headers(map)
                    .get();
            return doc;
        }
        return doc;
    }

    /**
     *用于调用统一认证接口登陆，返回一个登陆码（code），0表示成功，1表示失败
     */
    public int getCode() throws IOException {
        Document document=doget();
        JSONObject jsonObject= JSON.parseObject(document.text());
        return jsonObject.getInteger("code");
    }

    /**
     * 用于调用统一认证接口，返回一个token（token）
     * @return
     * @throws IOException
     */
    public String getToken() throws IOException {
        Document document=doget();
        JSONObject jsonObject=JSON.parseObject(document.text());
        JSONArray data=jsonObject.getJSONArray("data");
        return data.get(0).toString();
    }

    /**
     * 用于调用获取用户信息的接口，返回data（data）
     * @return 返回用户信息
     * @param token 由认证接口返回的token
     * @throws IOException
     */
    public JSONObject getUserInformation(String token) throws IOException {
        Document document=doget(token);
        JSONObject jsonObject=JSON.parseObject(document.text());
        return jsonObject.getJSONObject("data");
    }
}
