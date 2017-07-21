package avinash.distributeddownloadingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

import avinash.distributeddownloadingsystem.Database.Download_Info;
import avinash.distributeddownloadingsystem.Database.SQLiteHelper;

public class InitiateDownload extends Fragment {
    public InitiateDownload()
    {

    }
    SQLiteHelper sq;
    EditText et_url,et_numOfRequests;
    Button download;
    Context context;
    ProgressDialog progressDialog;
    String url;
    int numOfRequests;
    private String REQUEST_URL = "http://192.168.43.38:3000/download";//"http://:192.168.43.29:3000/download";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.download_initiate, container, false);
        context=view.getContext();
        et_url = (EditText) view.findViewById(R.id.link);
        et_numOfRequests = (EditText) view.findViewById(R.id.editText2);
        download = (Button) view.findViewById(R.id.download);
        progressDialog = new ProgressDialog(context);
        sq=new SQLiteHelper(view.getContext());

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("listener","called");
                if(!et_url.getText().toString().contains(" ")&&
                        !et_numOfRequests.getText().toString().contains(" ")&&
                        !et_url.getText().toString().equals("")&&
                        !et_numOfRequests.getText().toString().equals(""))
                {
                    url = et_url.getText().toString();
                    numOfRequests = Integer.parseInt(et_numOfRequests.getText().toString());

                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                   /* Download_Info DI = new Download_Info(file.getText().toString(), et_url.getText().toString(), 1, "Pending");
                    sq.addRow(DI);*/

                    Log.i("make request","called");
                    makeRequest();
                    progressDialog.dismiss();
                }
                else
                {
                    if(et_url.getText().toString().contains(" ")||et_url.getText().toString().equals("")){
                        et_url.setError("enter a valid url!");
                    }
                    if(et_numOfRequests.getText().toString().contains(" ")||et_numOfRequests.getText().toString().equals("")){
                        et_numOfRequests.setError("enter a valid number");
                    }
                }
            }
        });
        return view;
    }

    private void makeRequest() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        HashMap<String,String> params = new HashMap <String,String>();
        params.put("url",url);
        params.put("parts",numOfRequests+"");
        Log.i("here","here");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                REQUEST_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context,"Hell yeah!",Toast.LENGTH_SHORT).show();
                        Log.i("res",response.toString());
                        String name, ext, id, partCount;
                        try {
                           // Download_Info DI(response.getString("name"), response.getString("ext"), response.getString("_id"));
                             name = response.getString("name");
                             ext = response.getString("ext");
                             id = response.getString("_id");


                            Download_Info DI= new Download_Info(name,ext,et_url.getText().toString(), et_numOfRequests.getText().toString(),id);
                            sq.addRow(DI);

                        }catch (Exception e){e.printStackTrace();}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Log.i("error listener called",error.getMessage());
                        }
                        catch (Exception e)
                        {
                            Log.i("error",e.getMessage());
                        }
                        Toast.makeText(context,"fuck no",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
        Log.i("request made","true");
    }
}