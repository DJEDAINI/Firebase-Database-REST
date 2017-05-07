package com.github.florent37.materialviewpager.sample.fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.florent37.materialviewpager.sample.MainActivity;
import com.github.florent37.materialviewpager.sample.Model.SeaalInformation;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.Adapters.TestRecyclerViewAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;

    DatePickerDialog datePickerDialog ;
    Calendar calendar ;
    int Year, Month, Day ;
    View addDialog ;
    LovelyCustomDialog dialog;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeView)
    SwipeRefreshLayout swipeView;


    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        FloatingActionButton fab = ButterKnife.findById(getActivity() ,R.id.fab );
        final List<SeaalInformation> items = new ArrayList<>();

        //setup materialviewpager

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        final TestRecyclerViewAdapter adapter = new TestRecyclerViewAdapter();

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setAdapter(new TestRecyclerViewAdapter());
                swipeView.setRefreshing(false);
            }
        });
        mRecyclerView.setAdapter(adapter);

        addDialog = getActivity().getLayoutInflater().inflate(R.layout.adddialog , null);
        final EditText id  = (EditText) addDialog.findViewById(R.id.iddialog);
        final EditText nom  = (EditText) addDialog.findViewById(R.id.nomdialog);
        final TextView date  = (TextView) addDialog.findViewById(R.id.datedialog);
        final Spinner etat  = (Spinner) addDialog.findViewById(R.id.okdialog);
        ArrayAdapter adap = ArrayAdapter.createFromResource(getContext(),
                R.array.snipperdialog, R.layout.item_spinner);
        etat.setAdapter(adap);
        dialog =   new LovelyCustomDialog(getContext(), R.style.EditTextTintTheme)
                .setTopColorRes(R.color.darkRed)
                .setView(addDialog)
                .setTitle(R.string.text_input_title)
                .setIcon(R.drawable.ic_assignment_white_36dp)
                .setCancelable(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                nom.setText("");
                date.setText(getResources().getString(R.string.une_date));
                dialog.show();
            }
        });

        Button ok = (Button) addDialog.findViewById(R.id.ok);
        Button annuler = (Button) addDialog.findViewById(R.id.cancel);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(RecyclerViewFragment.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#D32F2F"));

                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty()){
                    Snackbar.make(v, "ID ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                    return;
                }else if (findInfo(id.getText().toString())){
                    Snackbar.make(v, "ID existe " , Snackbar.LENGTH_LONG).show();
                    return;
                }else if (nom.getText().toString().isEmpty()){
                    Snackbar.make(v, "Nom ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                    return;
                }
                else if (etat.getSelectedItem().toString().isEmpty()){
                    Snackbar.make(v, "Etat ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                    return;
                }else if (date.getText().toString().equals(getResources().getString(R.string.une_date))){
                    Snackbar.make(v, "Date ne peut pas etre vide" , Snackbar.LENGTH_LONG).show();
                    return;
                }
                SeaalInformation info = new SeaalInformation(id.getText().toString() ,
                        nom.getText().toString() , etat.getSelectedItem().toString() , date.getText().toString()  );


                if(etat.getSelectedItem().toString().equals(getResources().getStringArray(R.array.snipperdialog)[0])){
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
                adapter.addInfo(info);
                dialog.dismiss();
                Toast.makeText(v.getContext() , "Item Added" , Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    boolean findInfo(String codeId) {
        for(SeaalInformation carnet : TestRecyclerViewAdapter.contents) {
            if(carnet.getInfoId().equals(codeId)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        ((TextView) addDialog.findViewById(R.id.datedialog)).setText(dayOfMonth + "-" + monthOfYear + "-" + year);
    }
}
