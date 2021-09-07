package com.applicationdevelopers.lordoffood.Interfaces;

import com.android.volley.VolleyError;

/**
 * Created by Seyd Ans Ali 06-June-2021
*/
public interface NetworkCallback {
    void onSuccess(String result);
    void onError(VolleyError volleyError);
}
