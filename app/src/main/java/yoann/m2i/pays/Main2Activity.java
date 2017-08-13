package yoann.m2i.pays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private ListView listResult;
    ArrayList<String> pays = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private final String URL_WS2 = "http://demo@services.groupkt.com/country/get/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listResult = (ListView) findViewById(R.id.listResult);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, pays);
        listResult.setAdapter(adapter);

        //adapter = composant nécessaire au fonctionnement d'une ListView
        // Il définit le view pour chacun des elements de la liste : R.layout.list_item
        //Il définit lz tableau des valeurs : payx
    }


    public void btnRetour (View v) throws Exception {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void btnAll (View v) throws Exception {

        String adr = URL_WS2;
        ThreadHttp http = new ThreadHttp();
        http.setAddress(adr);
        http.start();
        http.join();
        try {
            JSONObject json_data= new JSONObject(http.getResponse());
            JSONArray jsonTab = json_data.getJSONObject("RestResponse").getJSONArray("result");
            for (int i = 0; i < jsonTab.length(); i++) {
                pays.add(jsonTab.getJSONObject(i).getString("name"));

            }
            listResult.setAdapter(adapter);

        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

}
