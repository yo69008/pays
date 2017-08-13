package yoann.m2i.pays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ThreadHttp extends Thread {

    private String response;
    private String address;

    public void setAddress(String address){this.address = address;}
    public String getAddress(){return address; }

    public String getResponse(){return response;}

    @Override
    public void run() {
        super.run();
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(address);
            URLConnection con=url.openConnection();
            InputStream input = con.getInputStream();
//            InputStream in = url.openStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            input.close();
            reader.close();

        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        response = builder.toString();

        
    }
}
