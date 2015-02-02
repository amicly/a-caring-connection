package com.a_caring_reminder.app.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Dan Bryant on 7/30/2014.
 */
public class SendMessage extends AsyncTask {

    String url = "";
    public SendMessage(String url){
        this.url = url;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try{

            JSONObject ja = new JSONObject();
            Gson gson = new Gson();
            ja.put("message", params[1]);
            ja.put("email", params[0]);

            HttpClass hc = new HttpClass();
            hc.postClient(url, ja.toString());
        }
        catch(Exception ex){
            Log.d("register", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
