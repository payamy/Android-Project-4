package com.example.rhodium;
import com.example.rhodium.MainActivity;
import com.example.rhodium.data.model.Parameter;

import java.util.List;

public class Optimizer {

    public int cell_id;

    public void calcSigma(int cellID)
    {
        this.cell_id = cellID;
        List<Parameter> rows = MainActivity.appDatabase.parameterDao().loadByIDs(cell_id);
        for (int i = 0; i < rows.size(); i++)
        {
            //Xi ,Yi
            double latitude = rows.get(i).getLatitude();
            double longitude = rows.get(i).getLongitude();

            //Pi
            int power = rows.get(i).getPower();
        }

    }
}


