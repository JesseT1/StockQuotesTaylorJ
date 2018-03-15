package com.example.jesse.stockquotes_taylor;

/**
 * Created by Jesse on 3/12/2018.
 */
import android.os.AsyncTask;
import android.util.Log;
import java.net.*;
import java.io.*;

public class StockClass extends AsyncTask<String, Void, String>{
        public String symbol;
        public String lastTradeTime;
        public String lastTradePrice;
        public String change;
        public String range;
        public String range2;
        public String name;

        public StockClass(String symbol) {
            this.symbol = symbol.toUpperCase();
        }


    @Override
    protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://api.iextrading.com/1.0/stock/" + symbol + "/book");
                URLConnection connection = url.openConnection();
                InputStreamReader isr = new InputStreamReader((connection.getInputStream()));
                BufferedReader in = new BufferedReader(isr);
                String line = in.readLine();
                while (in.readLine() != null) ;

                in.close();

                if (line != null && line.length() > 0) {
                    String lineRegex;
                    String[] values = line.split("[,:]");

                    name = values[4];
                    name = name.replace('"', ' ');
                    change = values[47] + "  -  " + values[49] + "%";
                    range = values[71] + "  -  " + values[69];
                    range2 = values[35];
                    lastTradeTime = values[28] + ", " + values[29];
                    lastTradeTime = lastTradeTime.replace('"', ' ');
                    lastTradePrice = values[24];
                    lastTradePrice = lastTradePrice.replace('"', ' ');


                    Log.i(name, " is the name");
                    Log.i(range, " is the range");
                    Log.i(range2, " is the range2");
                    Log.i(change, " is the change");
                }

            }

            catch(Exception e) {

            }
        return null;
    }

    public String getLastTradeTime() {
        doInBackground();
        return lastTradeTime;
    }

    public String getLastTradePrice() {
        doInBackground();
        return lastTradePrice;
    }

    public String getChange() {
        doInBackground();
        return change;
    }

    public String getRange() {
        doInBackground();
        return range;
    }

    public String getName() {
        doInBackground();
        return name;
    }

    public String getSymbol() {
        doInBackground();
        return symbol;
    }
}
