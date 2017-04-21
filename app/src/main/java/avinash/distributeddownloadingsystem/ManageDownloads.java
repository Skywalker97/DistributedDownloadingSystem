package avinash.distributeddownloadingsystem;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import avinash.distributeddownloadingsystem.Database.Download_Info;
import avinash.distributeddownloadingsystem.Database.SQLiteHelper;

import static avinash.distributeddownloadingsystem.Database.SQLiteHelper.COLUMN_FILENAME;
import static avinash.distributeddownloadingsystem.Database.SQLiteHelper.COLUMN_ID;
import static avinash.distributeddownloadingsystem.Database.SQLiteHelper.COLUMN_KEY;
import static avinash.distributeddownloadingsystem.Database.SQLiteHelper.COLUMN_URL;
import static avinash.distributeddownloadingsystem.Database.SQLiteHelper.COLUMN_isADMIN;



/**
 * Created by Avinash Sharma on 15-Apr-17.`
 */

public class ManageDownloads extends Fragment {
    ArrayList<Download_Info> DownloadList;
    RecyclerView RV;
    SQLiteHelper sq;

    adapter DownloadAdapter;
    Context context;
    public ManageDownloads ()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.download_manage, container, false);
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.download_manage);
        RV = (RecyclerView) view.findViewById(R.id.RV);
        DownloadList = new ArrayList<>();
        sq = new SQLiteHelper(view.getContext());
        GetList();
        DownloadAdapter = new adapter();
        RV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RV.setAdapter(DownloadAdapter);
        context = view.getContext();


        return view;

    }

    public void GetList() {
        Cursor c = sq.GetListCursor();

        c.moveToFirst();
        Download_Info obj;
        int k=c.getCount();
        Log.d("GL",String.valueOf(k));
        while (!c.isAfterLast()) {
            String Fname, URL, key;
            long id;
            int isAdmin;
            Fname = c.getString(c.getColumnIndex(COLUMN_FILENAME));
            URL = c.getString(c.getColumnIndex(COLUMN_URL));
            isAdmin = c.getInt(c.getColumnIndex(COLUMN_isADMIN));
            key = c.getString(c.getColumnIndex(COLUMN_KEY));
            id = c.getInt(c.getColumnIndex(COLUMN_ID));


            obj = new Download_Info(id, Fname, URL, isAdmin, key);
            DownloadList.add(obj);
            c.moveToNext();

        }
        c.close();

    }


    private class VH extends RecyclerView.ViewHolder {
        CardView CV;
        TextView FileName;
        TextView URL;
        TextView isAdmin;
        TextView size;
        ImageView im;

        private VH(View view) {
            super(view);
            CV = (CardView) view.findViewById(R.id.CV);
            FileName = (TextView) view.findViewById(R.id.tvFname);
            URL = (TextView) view.findViewById(R.id.tvURL);
            isAdmin = (TextView) view.findViewById(R.id.tvisAdmin);
            size = (TextView) view.findViewById(R.id.tvSize);
            im = (ImageView) view.findViewById(R.id.imageView4);

        }

    }


    public class adapter extends RecyclerView.Adapter<VH> {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
           // LayoutInflater LI = getLayoutInflater();
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH holder, final int position) {
            final Download_Info obj = DownloadList.get(position);
           // Log.d("RV",DownloadList.get(1).getFileName());
            /*if(DownloadList.size()==1)
            Log.d("RV","t");
            else
                Log.d("RV","f");
            */
            holder.FileName.setText(obj.getFileName());
            holder.URL.setText(obj.getURL());
            //holder.size.setText("1GB");
            if (obj.getAdmin() != 0) {
                holder.isAdmin.setText("ADMIN");
            }
            else {
                holder.isAdmin.setText(" ");
                holder.im.setVisibility(View.INVISIBLE);
            }


            holder.CV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  ArrayList<Download_Info> A = new ArrayList<Download_Info>();
                   /* if(obj.getAdmin()==1) {
                        for (int i = 0; i < DownloadList.size(); i++) {
                            Download_Info a = DownloadList.get(position);
                            Download_Info b = DownloadList.get(i);
                            if (b.getKey() == a.getKey()) {
                                A.add(b);

                            }


                        }*/
                    Intent i = new Intent(context, Download_Details.class);
                    i.putExtra("Key", obj.getKey());
                    i.putExtra("Admin",obj.getAdmin());
                    i.putExtra("File",obj.getFileName());
                    i.putExtra("URL",obj.getURL());

                    startActivity(i);


                }


            });

        }

        @Override
        public int getItemCount() {
            return DownloadList.size();
        }
    }


}
