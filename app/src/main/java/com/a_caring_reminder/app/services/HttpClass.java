package com.a_caring_reminder.app.services;

import android.text.Html;
import android.util.Log;

/**
 * Created by Dan Bryant on 7/30/2014.
 */
public class HttpClass {

    public HttpClass(){

    }

    public void postClient(String url, String service){

        try{
//            HttpPost httppost = new HttpPost(url);
//            httppost.setHeader("Accept", "application/json");
//            httppost.setHeader("Content-type", "application/json");
//            httppost.setHeader("User-Agent", "android");
//            //Set the encoding for our post
//            StringEntity se = new StringEntity(service, HTTP.UTF_8);
//
//            //post
//            httppost.setEntity(se);
//
//            BasicHttpContext context = new BasicHttpContext();
//
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpResponse response = httpClient.execute(httppost, context);
//
//            //response
//            HttpEntity responseEntity = response.getEntity();
//            String r = EntityUtils.toString(responseEntity);
//
//            Log.d("postReturn", stripHtml(r));

        }
        catch (Exception ex){
            Log.d("postClient", ex.getMessage());
        }

    }

    String stripHtml(String html) {
        return Html.fromHtml(html).toString().replace(" ", "");
    }
}
