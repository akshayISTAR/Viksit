package pro.viksit.com.viksit.util;

import android.widget.Switch;

import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Feroz on 18-04-2017.
 */

public class HttpUtil {
    private String url;
    private String type;
    private HashMap<String,String> param;
    private String postrequest;
    public HttpUtil(){}
    public HttpUtil(String url, String type, HashMap<String, String> param,String postrequest) {
        this.url = url;
        this.type = type;
        this.param = param;
        this.postrequest = postrequest;
    }


    public String getStringResponse(){
        String jsonresponse="";
        try {
            System.out.println("url "+url);
            System.out.println("type "+type);

            HttpResponse httpResponse = getHttpResponse();
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                jsonresponse = EntityUtils.toString(httpEntity);
                System.out.println("res .... " + jsonresponse);
            } else {
                return "null";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonresponse;
    }
    public void getVoidResponse(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }

    private HttpResponse getHttpResponse(){
        HttpResponse httpResponse = null;
        HttpClient httpclient = new DefaultHttpClient();
        try{
        switch(type){
            case "GET":
                httpResponse = httpclient.execute(new HttpGet(url));
                break;
            case "POST":
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                if(param != null) {
                    for (String key : param.keySet()) {
                        nameValuePairs.add(new BasicNameValuePair(key, param.get(key)));
                    }
                }
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpResponse = httpclient.execute(httpPost);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(url);
                if(postrequest != null){
                    StringEntity se = new StringEntity(postrequest);
                    se.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
                    httpPut.setEntity(se);
                    httpPut.setHeader("Accept", "application/json");
                    httpPut.setHeader("Content-type", "application/json");
                }
                httpResponse = httpclient.execute(httpPut);
                break;
        } }catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }catch (JsonSyntaxException jse) {
            jse.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return httpResponse;
    }

    public String getPostrequest() {
        return postrequest;
    }

    public void setPostrequest(String postrequest) {
        this.postrequest = postrequest;
    }
}