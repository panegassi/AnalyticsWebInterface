package com.google.firebase.quickstart.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

// [START analytics_web_interface]
public class AnalyticsWebInterface {

    public static final String TAG = "AnalyticsWebInterface";
    private FirebaseAnalytics mAnalytics;

    public AnalyticsWebInterface(Context context) {
        mAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @JavascriptInterface
    public void logEvent(String name, String jsonParams) {
        LOGD("logEvent:" + name);
        mAnalytics.logEvent(name, bundleFromJson(jsonParams));
    }

    @JavascriptInterface
    public void setUserProperty(String name, String value) {
        LOGD("setUserProperty:" + name);
        mAnalytics.setUserProperty(name, value);
    }

    private void LOGD(String message) {
        // Only log on debug builds, for privacy
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }

    private Bundle bundleFromJson(String json) {
        // [START_EXCLUDE]
        if (TextUtils.isEmpty(json)) {
            return new Bundle();
        }

        Bundle result = new Bundle();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.get(key);

                /**
                 * @author Leandro Panegassi <leandropanegassi89@gmail.com>
                 * Customização da classe AnalyticsWebInterface para suportar o event type "ecommerce_purchase" com items (produtos) utilizando Google Firebase Analytics + Google Analytics.
                 */
                if (key.equals("items")) {
                    JSONArray itemsArray = (JSONArray) value;
                    ArrayList itemsArrayList = new ArrayList();

                    for (int i = 0; i < itemsArray.length(); i++) {
                        Bundle itemBundle = new Bundle();
                        JSONObject itemObject = itemsArray.getJSONObject(i);
                        Iterator<String> keysInterator = itemObject.keys();

                        while (keysInterator.hasNext()) {
                            String kInterator = keysInterator.next();
                            Object valueObject = itemObject.get(kInterator);

                            if (valueObject instanceof String) {
                                itemBundle.putString(kInterator, (String) valueObject);
                            } else if (valueObject instanceof Integer) {
                                itemBundle.putInt(kInterator, (Integer) valueObject);
                            } else if (valueObject instanceof Double) {
                                itemBundle.putDouble(kInterator, (Double) valueObject);
                            } else {
                                Log.w(TAG, "Value for key " + kInterator + " not one of [String, Integer, Double]");
                            }

                        }

                        itemsArrayList.add(itemBundle);

                    }

                    result.putParcelableArrayList("items", itemsArrayList);
                    
                } else {

                    if (value instanceof String) {
                        result.putString(key, (String) value);
                    } else if (value instanceof Integer) {
                        result.putInt(key, (Integer) value);
                    } else if (value instanceof Double) {
                        result.putDouble(key, (Double) value);
                    } else {
                        Log.w(TAG, "Value for key " + key + " not one of [String, Integer, Double]");
                    }

                }
            }
        } catch (JSONException e) {
            Log.w(TAG, "Failed to parse JSON, returning empty Bundle.", e);
            return new Bundle();
        }

        return result;
        // [END_EXCLUDE]
    }

}
// [END analytics_web_interface]
