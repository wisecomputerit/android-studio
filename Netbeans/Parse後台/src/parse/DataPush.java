package parse;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DataPush {

    private static final String APPLICATION_ID = "UtFEsVX4ZcHMRsVHHbmmC1LVVdCrtnFkQ8kyvEJi";
    private static final String REST_API_KEY = "eo1QLNXVuW1pkcmOXof0woyQzRnLDJ33jYXSMsIq";
    
    private static final String PUSH_URL = "https://api.parse.com/1/push";
    private static String message = "7";
    public static void main(String[] args) throws Exception {
        String[] channels = new String[]{"bingo"};
        String type = "android";
        Map<String, String> data = new HashMap<String, String>();
        if(args.length > 0) {
            message = args[0];
        } else {
            //return;
        }
        // 中文編碼
        String parseMessage = URLDecoder.decode(URLEncoder.encode(message, "UTF-8"), "ISO-8859-1");
        data.put("title", "Parse-Bingo");
        data.put("alert", parseMessage);
        try {
            new DataPush().sendPost(channels, type, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPost(String[] channels, String type, Map<String, String> data) throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("channels", channels);
        if (type != null) {
            jo.put("type", type);
            
        }
        jo.put("data", data);

        this.pushData(jo.toString());
        System.out.println(jo.toString());
    }

    public void pushData(String postData) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpEntity entity = null;
        String responseString = null;
        HttpPost httpost = new HttpPost(PUSH_URL);
        httpost.addHeader("X-Parse-Application-Id", APPLICATION_ID);
        httpost.addHeader("X-Parse-REST-API-Key", REST_API_KEY);
        httpost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity reqEntity = new StringEntity(postData);
        httpost.setEntity(reqEntity);
        response = httpclient.execute(httpost);
        entity = response.getEntity();
        if (entity != null) {
            responseString = EntityUtils.toString(response.getEntity());
        }

        System.out.println(responseString);
    }
}
