package avinash.distributeddownloadingsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Avinash Sharma on 16-Apr-17.
 */

public class Download_Details extends AppCompatActivity {
    String key, filename, URL, partCount;
    int admin;
    String [] list;
    CardView CV;
    TextView tvFname, tvURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_downloads);
        Bundle extras = getIntent().getExtras();
         key = extras.getString("Key");
         admin = extras.getInt("Admin");
        partCount = extras.getString("PartCount");
        filename = extras.getString("File");
        URL = extras.getString("URL");
        CV = (CardView) findViewById(R.id.CV);
        tvFname = (TextView) findViewById(R.id.tvFilename);
        tvURL = (TextView) findViewById(R.id.tvURL);
        tvFname.setText(filename);
        tvURL.setText(URL);



        getList();
        ListAdapter adapter = new CustomAdapter(this,list);

        ListView LV = (ListView)  findViewById(R.id.lv);
        LV.setAdapter(adapter);





        LV.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String KEY = String.valueOf(parent.getItemAtPosition(position));

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Download Link for " + filename + ": " + URL + " Download Key: " + KEY);
                        Intent chooser =Intent.createChooser(sharingIntent, " Share Using");
                        if(sharingIntent.resolveActivity(getPackageManager())!=null) {
                            startActivity(chooser);
                            Toast.makeText(Download_Details.this, "Download Key has been shared.", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(Download_Details.this,"Cannot Share", Toast.LENGTH_LONG).show();

                    }
                }
        );


    }

    public void getList()
    {
        //int s=Integer.parseInt(partCount);
        list = new String[4];
        for(int i=0;i<4; i++)
        {
            list[i] = key + String.valueOf(i);
        }
    }
    class CustomAdapter extends ArrayAdapter<String> {
        public CustomAdapter(Context context, String[] keys) {
            super(context, R.layout.list_item_details, keys);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater avisInflator = LayoutInflater.from(getContext());
            View customView = avisInflator.inflate(R.layout.list_item_details, parent, false);

            String singleItem = getItem(position);
            TextView textView = (TextView) customView.findViewById(R.id.key);


            textView.setText(singleItem);

            return customView;
        }
    }
}
