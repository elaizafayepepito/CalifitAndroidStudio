package com.example.califit;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserAccountService {
    private static final String BASE_URL = "https://secondly-crisp-ferret.ngrok-free.app/user_account";
    private final RequestQueue requestQueue;

    public UserAccountService(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void createAccount(JSONObject accountData, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/create_account";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                accountData,
                responseListener,
                errorListener
        );
        requestQueue.add(request);
    }

    public void login(JSONObject loginData, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/login";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                loginData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        try {
                            // Extract message and account_id from the response JSON object
                            String message = response.getString("message");
                            String accountId = response.getString("account_id");

                            // Log the success message and account_id
                            Log.d("UserAccountService", "Login successful: " + message + ", Account ID: " + accountId);

                            // Pass the entire response to the response listener
                            responseListener.onResponse(response);
                        } catch (Exception e) {
                            Log.e("UserAccountService", "Error parsing login response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        Log.e("UserAccountService", "Login error: " + error.getMessage());
                        errorListener.onErrorResponse(error);
                    }
                }
        );

        requestQueue.add(request);
    }

    public void createUser(JSONObject userData, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/create_user";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                userData,
                responseListener,
                errorListener
        );
        requestQueue.add(request);
    }

    public void getUser(String accountId, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/users/" + accountId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                responseListener,
                errorListener
        );
        requestQueue.add(request);
    }

    /*public void getAllUsers(Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/users";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                responseListener,
                errorListener
        );
        requestQueue.add(request);
    }*/
    public void getAllUsers(Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/users";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the successful response
                        // response is the JSON array received from the API
                        Log.d("UserAccountService", "getAllUsers successful: " + response.toString());
                        responseListener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        Log.e("UserAccountService", "getAllUsers error: " + error.getMessage());
                        errorListener.onErrorResponse(error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void updateAccount(String accountId, JSONObject accountData, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/accounts/update_account/" + accountId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                accountData,
                responseListener,
                errorListener
        );
        requestQueue.add(request);
    }

    public void updateUser(String userId, JSONObject userData, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/users/update_user/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                userData,
                responseListener,
                errorListener
        );
        requestQueue.add(request);
    }
}
