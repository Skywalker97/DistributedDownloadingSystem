package avinash.distributeddownloadingsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import avinash.distributeddownloadingsystem.Database.SQLiteHelper;

public class DownloadPart extends Fragment {
    public DownloadPart()
    {

    }

    private EditText url,key;
    private Button download;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.download_part);
        context=this;
        url = (EditText) findViewById(R.id.link);
        key = (EditText) findViewById(R.id.key);
        download = (Button) findViewById(R.id.download);


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!url.getText().toString().contains(" ")&&!key.getText().toString().contains(" ")&&!url.getText().toString().equals("")&&!key.getText().toString().equals(""))
                {
                    Toast.makeText(context, "Download has started", Toast.LENGTH_SHORT).show();
                    String JSON_LinkKey = "{\"LinkKey\":{\"link\":\" Link  \",\"Key\":Key}}";
                    try {
                        JSONObject LinkKey = (new JSONObject(JSON_LinkKey)).getJSONObject("LinkKey");

                        //Communicate with the API here.

                    }catch (Exception e){e.printStackTrace();}
                }
                else
                {
                    if(url.getText().toString().contains(" ")||url.getText().toString().equals(""))
                        url.setError("url cannot contain blank spaces");
                    if(key.getText().toString().contains(" ")||key.getText().toString().equals(""))
                        key.setError("key cannot contain blank spaces");
                }
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {

        View view = inflater.inflate(R.layout.download_part, container, false);
        super.onCreate(savedInstanceState);

        context=view.getContext();
        url = (EditText) view.findViewById(R.id.link);
        key = (EditText) view.findViewById(R.id.key);
        download = (Button) view.findViewById(R.id.download);
      SQLiteHelper  sq = new SQLiteHelper(view.getContext());

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!url.getText().toString().contains(" ")&&!key.getText().toString().contains(" ")&&!url.getText().toString().equals("")&&!key.getText().toString().equals(""))
                {
                    Toast.makeText(context, "Download has started", Toast.LENGTH_SHORT).show();
                    String JSON_LinkKey = "{\"LinkKey\":{\"link\":\" Link  \",\"Key\":Key}}";
                    try {
                        JSONObject LinkKey = (new JSONObject(JSON_LinkKey)).getJSONObject("LinkKey");

                        //Communicate with the API here.

                    }catch (Exception e){e.printStackTrace();}

                    /* Download_Info DI = new Download_Info(file.getText().toString(), url.getText().toString(), 0, key.getText().toString());
                    sq.addRow(DI);*/

                }
                else
                {
                    if(url.getText().toString().contains(" ")||url.getText().toString().equals(""))
                        url.setError("url cannot contain blank spaces");
                    if(key.getText().toString().contains(" ")||key.getText().toString().equals(""))
                        key.setError("key cannot contain blank spaces");
                }
            }
        });
        return view;
    }


}