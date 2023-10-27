package com.example.notify;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class sendNotification {

        String FcmToken, title, sendBy;
        Context mContext;
        Activity mActivity;

        private RequestQueue requestQueue;

        private final String postUrl = "https://fcm.googleapis.com/fcm/send";

        private final String fcmServerKey = "AAAA2gHc1Vw:APA91bErhpBeX7TSlRbnPVvue7SdA9I4YGm3CuuzoGrpOlW_iDSr__pcZmSBHWeJsgN4feGUMUSTHUtYUILcJczjnpGPo2IkxkbeB4oh8c4LAju6FIZtFC5l-oumDtdIjxdGg03olsSp";

    public sendNotification(String fcmToken, String title, String sendBy, Context mContext, Activity mActivity) {
        FcmToken = fcmToken;
        this.title = title;
        this.sendBy = sendBy;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void sendNotify() {
        requestQueue = Volley.newRequestQueue(mActivity);
        JSONObject myObj = new JSONObject();
        try {
            myObj.put("to", FcmToken);
            JSONObject notifyObj = new JSONObject();
            notifyObj.put("sendBy", sendBy);
            notifyObj.put("title", title);
            notifyObj.put("icon", R.drawable.notify);

            myObj.put("notification", notifyObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, myObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;
                }

            };
            requestQueue.add(request);
        }
        catch(JSONException e) {

        }
    }
}
