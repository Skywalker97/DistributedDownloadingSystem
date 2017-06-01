package avinash.distributeddownloadingsystem;

import android.content.Context;
import android.os.AsyncTask;
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

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import avinash.distributeddownloadingsystem.Database.SQLiteHelper;

public class DownloadPart extends Fragment {
    public DownloadPart()
    {

    }

    private EditText url,_key;
    private Button download;
    Context context;
    final String RETRIEVE_URL= "http://192.168.43.38:3000/retrieve";
    ArrayList<String> key, values;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {

        View view = inflater.inflate(R.layout.download_part, container, false);
        super.onCreate(savedInstanceState);

        context=view.getContext();
        url = (EditText) view.findViewById(R.id.link);
        _key = (EditText) view.findViewById(R.id.key);
        download = (Button) view.findViewById(R.id.download);
        SQLiteHelper  sq = new SQLiteHelper(view.getContext());
        key = new ArrayList<>();
        values = new ArrayList<>();
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _key.setText("58fa9153849e4b2500de5deb");
                if(!url.getText().toString().contains(" ")&&!_key.getText().toString().contains(" ")&&!url.getText().toString().equals("")&&!_key.getText().toString().equals(""))
                {
/*
                    Toast.makeText(context, "Download has started", Toast.LENGTH_SHORT).show();
                    String JSON_LinkKey = "{\"LinkKey\":{\"link\":\" Link  \",\"Key\":Key}}";
                    try {
                        JSONObject LinkKey = (new JSONObject(JSON_LinkKey)).getJSONObject("LinkKey");

                        //Communicate with the API here.

                    }catch (Exception e){e.printStackTrace();}
*/
                    /* Download_Info DI = new Download_Info(file.getText().toString(), url.getText().toString(), 0, key.getText().toString());
                    sq.addRow(DI);*/
                    DownloadFile downloadFile=new DownloadFile();
                    downloadFile.execute(RETRIEVE_URL);
                }
                else
                {
                    if(url.getText().toString().contains(" ")||url.getText().toString().equals(""))
                        url.setError("url cannot contain blank spaces");
                    if(_key.getText().toString().contains(" ")||_key.getText().toString().equals(""))
                        _key.setError("key cannot contain blank spaces");
                }
            }
        });
        return view;
    }

    private String getQuery() throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < key.size(); i++) {
            if (i == 0) {
                result.append(URLEncoder.encode(key.get(i), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(values.get(i), "UTF-8"));

            } else {
                result.append("&");
                result.append(URLEncoder.encode(key.get(i), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(values.get(i), "UTF-8"));
            }
        }

        return result.toString();
    }


    class DownloadFile extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            try {
                key.add("_id");
                values.add("58fa9153849e4b2500de5deb");

                key.add("index");
                values.add("0");

            } catch (Exception e) {
                //Log.i("error", e.getMessage());
            }
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {

                URL url = new URL(f_url[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.connect();
                int lenghtOfFile = connection.getContentLength();
                Log.i("here","true");
                InputStream input = new BufferedInputStream(url.openStream(), 16384);



                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.txt");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.i("error",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(context,"downloaded",Toast.LENGTH_SHORT).show();
        }
    }

}