package avinash.distributeddownloadingsystem;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import avinash.distributeddownloadingsystem.Database.SQLiteHelper;

import static avinash.distributeddownloadingsystem.Database.SQLiteHelper.COLUMN_KEY;




/**
 * Created by Avinash Sharma on 16-Apr-17.
 */

public class Download_Details extends AppCompatActivity {
    String key, filename, URL;
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
      /*  LV.setOnItemClickListener(new OnItemClickListener()
        {    @Override
        public void onItemClick(AdapterView<?> a, View v, int position,
                                long id) {

            String str = key.getText().toString();
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(str));
            Intent chooser =Intent.createChooser(sharingIntent, " Share Using");
            if(sharingIntent.resolveActivity(getPackageManager())!=null)
                startActivity(chooser);
            else
            {
                Toast.makeText(MainActivity.this,"Cannot Share", Toast.LENGTH_LONG).show();
            }

            startActivity(intent);
        }

        });*/


    }

    public void getList()
    { SQLiteHelper sq = new SQLiteHelper(this);
        Cursor c = sq.getKeys(key);
        c.moveToFirst();
        int count = c.getCount();
        list = new String[count];
        int i=0;
        while(!c.isAfterLast())
        {
            String l =c.getString(c.getColumnIndex(COLUMN_KEY));
            list[i] = l;
            ++i;
            c.moveToNext();
        }
        c.close();
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
