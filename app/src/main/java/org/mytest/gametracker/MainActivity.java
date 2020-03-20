package org.mytest.gametracker;
//import static ProcessList.COLUMN_PROCESS_NAME;
//import static ProcessList.COLUMN_PROCESS_PROP;
//import static ProcessList.COLUMN_PROCESS_COUNT;
//import static ProcessList.COLUMN_PROCESS_TIME;
import static org.mytest.gametracker.ProcessList.COLUMN_PROCESS_COUNT;
import static org.mytest.gametracker.ProcessList.COLUMN_PROCESS_NAME;
import static org.mytest.gametracker.ProcessList.COLUMN_PROCESS_PROP;
import static org.mytest.gametracker.ProcessList.COLUMN_PROCESS_TIME;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap<String,Object>> processList;
    private MonitorService backgroundService;
    private MyCustomAdapter adapter = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    Layout complex_list_item;

//    private void createAdapter()
//    {
//        //processList = ((MonitorApp)getApplication()).getProcessList();
//        adapter = new MyCustomAdapter(this, processList, R.layout.complex_list_item,
//                new String[]
//                        {
//                                COLUMN_PROCESS_NAME,
//                                COLUMN_PROCESS_PROP, // TODO: you may calculate and pre-fill this field
//                                // from COLUMN_PROCESS_COUNT and COLUMN_PROCESS_TIME
//                                // so eliminating the need to use the custom adapter
//                        },
//                new int[]
//                        {
//                                android.R.id.text1,
//                                android.R.id.text2
//                        });
//
//        listView.setAdapter(adapter);
//    }

    // callback method invoked by the service when foreground process changed
//    @Override
//    public void sendResults(int resultCode, Bundle b)
//    {
//        adapter.notifyDataSetChanged();
//    }

    private class MyCustomAdapter extends SimpleAdapter
    {
        MyCustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
        {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent)
        {
            View result = super.getView(position, convertView, parent);

            // TODO: customize process statistics display
            int count = (Integer)(processList.get(position).get(COLUMN_PROCESS_COUNT));
            int seconds = (Integer)(processList.get(position).get(COLUMN_PROCESS_TIME));

            return result;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service)
        {
            MonitorService.LocalBinder binder = (MonitorService.LocalBinder)service;
            backgroundService = binder.getService();
            //backgroundService.setCallback(MainActivity.this);
            backgroundService.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName className)
        {
            backgroundService = null;
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        if(backgroundService != null)
        {
            //backgroundService.setCallback(this);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(backgroundService != null)
        {
            backgroundService.setCallback(null);
        }
    }
}
