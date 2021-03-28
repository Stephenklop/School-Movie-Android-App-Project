package com.example.movieappschool.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

// The Singleton class ensures that (at most) one instance of the Volley requestQueue exists.
public class DataServiceSingleton {
    private static DataServiceSingleton sInstance;
    private RequestQueue mRequestQueue;
    private static Context sCtx;

    private DataServiceSingleton(Context context) {
        sCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized DataServiceSingleton getInstance(Context context) {
        // Check if a Singleton instance already exists.
        if (sInstance == null) {
            // If so create a new Singleton.
            sInstance = new DataServiceSingleton(context);
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(sCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}