package yoann.m2i.pays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText txtSaisie;
    private EditText txtNom;
    private EditText txtCode;
    private TextView txtResult;

    private final String URL_WS1 = "http://demo@services.groupkt.com/country/get/iso2code/";
    private final String URL_WS2 = "http://demo@services.groupkt.com/country/get/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSaisie = (EditText) findViewById(R.id.txtSaisie);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtNom = (EditText) findViewById(R.id.txtNom);
        txtCode = (EditText) findViewById(R.id.txtCode);
    }

    public void btnOk (View v) throws Exception {
        try {
            String adr = URL_WS1+txtSaisie.getText().toString().toUpperCase();
            ThreadHttp http = new ThreadHttp();
            http.setAddress(adr);
            http.start();
            http.join();
            txtResult.setText(http.getResponse());

            JSONObject json_data= new JSONObject(http.getResponse());
            json_data = json_data.getJSONObject("RestResponse").getJSONObject("result");
            txtResult.setText(json_data.toString());

            String name;
            String alpha3_code;
            name= json_data.getString("name");
            alpha3_code= json_data.getString("alpha3_code");
            txtNom.setText(name);
            txtCode.setText(alpha3_code);


        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }

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

            String names ="";
            String alpha3_code = "";

            for (int i = 0; i < jsonTab.length(); i++) {
                names += jsonTab.getJSONObject(i).getString("name") +"\n"
                       + jsonTab.getJSONObject(i).getString("alpha3_code") + "\n\n";
                alpha3_code +=jsonTab.getJSONObject(i).getString("alpha3_code") + "\n\n";

            }
            txtResult.setText(names);
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void btnNext (View v) throws Exception {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }



}
