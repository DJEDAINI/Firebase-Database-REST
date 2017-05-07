package com.github.florent37.materialviewpager.sample.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialviewpager.sample.App.App;
import com.github.florent37.materialviewpager.sample.MainActivity;
import com.github.florent37.materialviewpager.sample.Model.SeaalInformation;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.utils.Constants;
import com.github.florent37.materialviewpager.sample.utils.CustomRequest;
import com.github.florent37.materialviewpager.sample.utils.CustomRequestArray;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.MyViewHolder> implements
        DatePickerDialog.OnDateSetListener , Constants{

    public static List<SeaalInformation> contents;
    DatePickerDialog datePickerDialog ;
    Calendar calendar ;
    int Year, Month, Day ;
    CustomRequestArray mRequest ;

    TextView dateAccess= null;

    static final int TYPE_CELL = 1;

    public TestRecyclerViewAdapter() {
        this.contents = new ArrayList<SeaalInformation>();
    if(App.getInstance().isConnected()) {
        CustomRequest jsonReq = new CustomRequest(Request.Method.GET, FIREBASE_DATA, null,
                    new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Data", response.toString());
                            Iterator<String> it = response.keys();
                            while (it.hasNext()){
                                String key = it.next();
                                JSONObject childJSONObject = response.getJSONObject(key);
                                if (childJSONObject != null) {
                                    SeaalInformation inf = new SeaalInformation(childJSONObject);
                                    contents.add(inf);
                                    notifyDataSetChanged();
                                }
                            }
                            } catch(JSONException e){
                                e.printStackTrace();
                            }
                        }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().addToRequestQueue(jsonReq);
    }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_CELL;
    }

    public void addInfo(SeaalInformation infor){
      //  myRef.child(infor.getInfoId()).setValue(infor);
        final  SeaalInformation data = infor;
        // Post params to be sent to the server
        Map<String, String> params = new HashMap<String, String>();
        params.put("infoId", data.getInfoId());
        params.put("infoNom", data.getInfoNom());
        params.put("infoDate", data.getInfoDate());
        params.put("infoEtat", data.getInfoEtat());
        params.put("infoDatePro", data.getInfoDatePro());

        JsonObjectRequest request_json = new JsonObjectRequest(FIREBASE_DATA, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Process os success response
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        App.getInstance().addToRequestQueue(request_json);
        contents.add(infor);
        notifyDataSetChanged();
    }

    public void updateData(int position , SeaalInformation infor){

            //  myRef.child(infor.getInfoId()).setValue(infor);
            final  SeaalInformation data = infor;
            final int pos = position;
            // Post params to be sent to the server
            Map<String, String> params = new HashMap<String, String>();
            params.put("infoId", data.getInfoId());
            params.put("infoNom", data.getInfoNom());
            params.put("infoDate", data.getInfoDate());
            params.put("infoEtat", data.getInfoEtat());
            params.put("infoDatePro", data.getInfoDatePro());

            final JSONObject js = new JSONObject(params);

            CustomRequest jsonReq = new CustomRequest(Request.Method.GET, FIREBASE_DATA, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Iterator<String> it = response.keys();
                                while (it.hasNext()){
                                    String key = it.next();
                                    JSONObject childJSONObject = response.getJSONObject(key);
                                    if (childJSONObject.getString("infoId").equals(data.getInfoId())) {
                                        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PATCH , FIREBASE_SEND + key + ".json" + FIREBASE_AUTH, js,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        //Process os success response
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                VolleyLog.e("Error: ", error.getMessage());
                                            }
                                        });
                                        App.getInstance().addToRequestQueue(request_json);
                                        contents.set(pos , data);
                                        notifyDataSetChanged();

                                    }
                                }
                            } catch(JSONException e){
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            App.getInstance().addToRequestQueue(jsonReq);
        }
    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public TestRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TestRecyclerViewAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_small, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final SeaalInformation info = contents.get(position);
        holder.infoId.setText("ID : " + info.getInfoId());
        holder.infoNom.setText(info.getInfoNom());
        holder.infoDate.setText("Visite : "+ info.getInfoDate());
        holder.infoDatePro.setText("Prochaine visite : " + info.getInfoDatePro());
        holder.infoEtat.setText("Etat : " + info.getInfoEtat());
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText id = (EditText) holder.addDialog.findViewById(R.id.iddialog);
                id.setText(info.getInfoId());
                id.setEnabled(false);
                final EditText nom = (EditText) holder.addDialog.findViewById(R.id.nomdialog);
                nom.setText(info.getInfoNom());
                final TextView date = (TextView) holder.addDialog.findViewById(R.id.datedialog);
                date.setText(info.getInfoDate());
                dateAccess = date;
                final Spinner spi = (Spinner) holder.addDialog.findViewById(R.id.okdialog);
                ArrayAdapter adap = ArrayAdapter.createFromResource(v.getContext(),
                        R.array.snipperdialog, R.layout.item_spinner);
                spi.setAdapter(adap);
                spi.setSelection(info.getIndex());
                holder.dialog.show();
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog = DatePickerDialog.newInstance(TestRecyclerViewAdapter.this, Year, Month, Day);

                        datePickerDialog.setThemeDark(false);

                        datePickerDialog.showYearPickerFirst(false);

                        datePickerDialog.setAccentColor(Color.parseColor("#D32F2F"));

                        datePickerDialog.show(((MainActivity) v.getContext()).getFragmentManager(), "DatePickerDialog");

                    }
                });
                Button ok = (Button) holder.addDialog.findViewById(R.id.ok);
                Button annuler = (Button) holder.addDialog.findViewById(R.id.cancel);
                annuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id.getText().toString().isEmpty()){
                            Snackbar.make(v, "ID ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                            return;
                        }else if (nom.getText().toString().isEmpty()){
                            Snackbar.make(v, "Nom ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        else if (spi.getSelectedItem().toString().isEmpty()){
                            Snackbar.make(v, "Etat ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                            return;
                        }else if (date.getText().toString().equals(v.getResources().getString(R.string.une_date))){
                            Snackbar.make(v, "Date ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        SeaalInformation info = new SeaalInformation(id.getText().toString() ,
                                nom.getText().toString() , spi.getSelectedItem().toString() , date.getText().toString()  );


                        if(spi.getSelectedItem().toString().equals(v.getResources().getStringArray(R.array.snipperdialog)[0])){
                            Date myDate = null;
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                myDate = format.parse(date.getText().toString());
                                Calendar c = Calendar.getInstance();
                                c.setTime(myDate);
                                c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 60);
                                Date newDate = c.getTime();
                                String newFormattedDate = format.format(newDate);//01/21/2015
                                info.setInfoDatePro(newFormattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        updateData(position , info);
                        notifyDataSetChanged();
                        holder.dialog.dismiss();
                        Toast.makeText(v.getContext() , "Item Changed" , Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateAccess.setText(dayOfMonth + "-" + monthOfYear + "-" + year);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardItem ;
        public View addDialog;
        LovelyCustomDialog dialog;
       public TextView infoId , infoNom , infoDate , infoDatePro , infoEtat;
        public MyViewHolder(View view) {
            super(view);
            cardItem = (CardView) view.findViewById(R.id.card_view);
            infoId = (TextView) view.findViewById(R.id.infoId);
            infoNom = (TextView) view.findViewById(R.id.infoNom);
            infoDate = (TextView) view.findViewById(R.id.infoDate);
            infoDatePro = (TextView) view.findViewById(R.id.infoDatePro);
            infoEtat = (TextView) view.findViewById(R.id.infoEtat);
            LayoutInflater li = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            addDialog = li.inflate(R.layout.adddialog , null);
            dialog = new LovelyCustomDialog(view.getContext(), R.style.EditTextTintTheme)
                    .setTopColorRes(R.color.darkRed)
                    .setView(addDialog)
                    .setTitle(R.string.edit_info)
                    .setIcon(R.drawable.ic_assignment_white_36dp)
                    .setCancelable(false);

        }

    }

}