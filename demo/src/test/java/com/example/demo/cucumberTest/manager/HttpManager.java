package com.example.demo.cucumberTest.manager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

//TODO: metodi per leggere/PARSING DEI RISULATI DELLA RISPosta(eg. get result code, get entity to string)
//TODO:
public class HttpManager {

    //non si puo usare generico va creato per ogni chiamata
    //CloseableHttpClient  httpClient = HttpClients.createDefault();

    public CloseableHttpClient createConnection(){
        CloseableHttpClient  httpClient = HttpClients.createDefault();
        return  httpClient;
    }

    public void setAuth(HttpRequestBase httpRequestBase){
        httpRequestBase.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + "stuff");
    }

    public CloseableHttpResponse httpGet(String url) throws IOException {
        CloseableHttpClient httpClient = createConnection();
        HttpGet httpGet                = new HttpGet(url);
        setAuth(httpGet);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return response;
    }

    public CloseableHttpResponse httpPost(String url, JSONObject requestBodyJson) throws IOException {
        CloseableHttpClient httpClient = createConnection();
        HttpPost httpPost              = new HttpPost(url);
        setAuth(httpPost);
        httpPost.setHeader("Content-Type", "application/json");
        String requestBody             = requestBodyJson.toString();
        httpPost.setEntity(new StringEntity(requestBody));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }
    public CloseableHttpResponse httpPut(String url,JSONObject requestBodyJson) throws IOException {
        CloseableHttpClient httpClient = createConnection();
        HttpPut httpPut                = new HttpPut(url);
        setAuth(httpPut);
        String requestBody             = requestBodyJson.toString();
        httpPut.setHeader("Content-Type", "application/json");
        httpPut.setEntity(new StringEntity(requestBody));
        CloseableHttpResponse response = httpClient.execute(httpPut);
        return response;
    }

    public CloseableHttpResponse httpDelete(String url) throws IOException {
        CloseableHttpClient httpClient = createConnection();
        HttpDelete httpDelete          = new HttpDelete(url);
        setAuth(httpDelete);
        CloseableHttpResponse response = httpClient.execute(httpDelete);
        return response;
    }

    public int httpGetResponseCode(CloseableHttpResponse response){
        int code = response.getStatusLine().getStatusCode();
        return code;
    }

    public JSONObject  httpGetResponseBodyasJson(CloseableHttpResponse response) throws IOException, JSONException {
        HttpEntity entity = response.getEntity();
        String string= EntityUtils.toString(entity);
        JSONObject json   = new JSONObject(string);
        return json;

    }
}