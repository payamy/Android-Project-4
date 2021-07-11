package com.example.rhodium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.rhodium.data.model.Parameter;

import java.util.List;

public class qoe extends AppCompatActivity {
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qoe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv=(RecyclerView)findViewById(R.id.QoEList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    private void getData() {

        class GetData extends AsyncTask<Void,Void, List<Parameter>> {


            @Override
            protected List<Parameter> doInBackground(Void... voids) {
                List<Parameter>myDataLists=MainActivity.appDatabase.parameterDao().getAll();
                return myDataLists;
            }

            @Override
            protected void onPostExecute(List<Parameter> myDataList) {
                QoeListAdaptor adapter=new QoeListAdaptor(myDataList);
                rv.setAdapter(adapter);
                super.onPostExecute(myDataList);
            }

        }
        GetData gd=new GetData();
        gd.execute();
    }
}
