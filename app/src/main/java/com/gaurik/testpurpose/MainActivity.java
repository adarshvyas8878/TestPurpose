package com.gaurik.testpurpose;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String link = "http://epmlz.mountlitera.com/jsonmobile.asmx/Alerts?StudID=STUG115310009&&MacID=";
//    String link = "https://jsonkeeper.com/b/DN0J";
    RecyclerView recyclerView;
    ArrayList<Model> arrayList;
    String entrydate,sender,senderimg,header,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        arrayList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        hitApi();
    }

    private void hitApi()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = response.replaceAll("<(.?)>"," ");
                    response = response.replaceAll("<(.*?)\\>"," ");
                    response = response.replaceAll("<(.*?)\\\n"," ");
                    response = response.replaceFirst("(.*?)\\>", " ");
                    response = response.replaceAll("&nbsp;"," ");
                    response = response.replaceAll("&amp;"," ");

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        entrydate = jsonObject.getString("EntryDate");
                        sender = jsonObject.getString("SenderName");
                        header = jsonObject.getString("SubjectHeader");
                        senderimg = jsonObject.getString("SenderImage");
                        msg = jsonObject.getString("Message");
                        msg=msg.substring(0,35)+".....";

                        arrayList.add(new Model(entrydate,sender,senderimg,header,msg));
                    }
                    startRecycler();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void startRecycler()
    {
        RecyclerView.Adapter<MsgViewHolder> adapter = new RecyclerView.Adapter<MsgViewHolder>() {
            @NonNull
            @Override
            public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.card_for_recycler,parent,false);
                MsgViewHolder msgViewHolder = new MsgViewHolder(view);
                return msgViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull MsgViewHolder holder, int position) {
                Model model = arrayList.get(position);
                holder.vhdate.setText(model.getEntryDate());
                holder.vhheader.setText(model.getSubjectHeader());
                holder.vhmsg.setText(model.getMessage());
                holder.vhname.setText(model.getSenderName());
//                Picasso.get().load(model.getSenderImage()).into(holder.vhimg);
            }

            @Override
            public int getItemCount() {
                return arrayList.size();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class MsgViewHolder extends RecyclerView.ViewHolder {
        TextView vhdate,vhname,vhmsg,vhheader;
        ImageView vhimg;
        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);

            vhname =itemView.findViewById(R.id.usr_name);
            vhdate = itemView.findViewById(R.id.set_date);
            vhmsg = itemView.findViewById(R.id.set_msg);
            vhheader = itemView.findViewById(R.id.thing);
            vhimg =itemView.findViewById(R.id.imgview);
        }
    }
}