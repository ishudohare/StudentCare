package com.example.ishudohare.finalpre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class xyz extends AppCompatActivity {
    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xyz);
        Weather weather_data[] = new Weather[]
                {
                        new Weather(R.drawable.clock, "Cloudy"),
                        new Weather(R.drawable.clock, "Showers"),
                        new Weather(R.drawable.rightarrow, "Snow"),
                        new Weather(R.drawable.clock, "Storm"),
                        new Weather(R.drawable.rightarrow, "Sunny")
                };

        WeatherAdapter adapter = new WeatherAdapter(this,
                R.layout.listview_item_row, weather_data);


        listView1 = (ListView) findViewById(R.id.listView1);

        View header = (View) getLayoutInflater().inflate(R.layout.listview_header_row, null);

        listView1.addHeaderView(header);

        listView1.setAdapter(adapter);
    }
}
