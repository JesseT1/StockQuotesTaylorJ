package com.example.jesse.stockquotes_taylor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity{
    String inp;
    String symbol;
    String name;
    String tradePrice;
    String tradeTime;

    String change;
    String range;

    EditText editText;
    Button button;

    TextView symbolTextView;
    TextView nameTextView;
    TextView tradePriceTextView;
    TextView tradeTimeTextView;
    TextView changeTextView;
    TextView rangeTextView;

    boolean invalidSymbol = false;
    StockClass stock;
    Context context;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("inp",editText.getText().toString());
        outState.putString("symbol",symbolTextView.getText().toString());
        outState.putString("name",nameTextView.getText().toString());
        outState.putString("tradePrice",tradePriceTextView.getText().toString());
        outState.putString("tradeTime",tradeTimeTextView.getText().toString());
        outState.putString("change",changeTextView.getText().toString());
        outState.putString("range",rangeTextView.getText().toString());
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getString("inp"));
        symbolTextView.setText(savedInstanceState.getString("symbol"));
        nameTextView.setText(savedInstanceState.getString("name"));
        tradePriceTextView.setText(savedInstanceState.getString("tradePrice"));
        tradeTimeTextView.setText(savedInstanceState.getString("tradeTime"));
        changeTextView.setText(savedInstanceState.getString("change"));
        rangeTextView.setText(savedInstanceState.getString("range"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        symbolTextView = (TextView) findViewById(R.id.symbol);
        nameTextView = (TextView) findViewById(R.id.name);
        tradePriceTextView = (TextView) findViewById(R.id.tradePrice);
        tradeTimeTextView = (TextView) findViewById(R.id.tradeTime);
        changeTextView = (TextView) findViewById(R.id.change);
        rangeTextView = (TextView) findViewById(R.id.range);
        context = getApplicationContext();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                try {
                    stockQuote();
                }
                catch(Exception e){
                    Toast.makeText(context, "This is not a valid symbol", Toast.LENGTH_LONG).show();
                }
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    try {
                        stockQuote();
                    }
                    catch(Exception e){
                        Toast.makeText(context, "This is not a valid symbol", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
    }
    public void stockQuote() throws IOException{

        inp = editText.getText().toString();

        if (inp.length()>0){

            stock = new StockClass(inp);

            new Thread(){
                public void run() {
                    try {
                        stock.execute();
                    }
                    catch (Exception e) {
                        invalidSymbol = true;
                        Toast.makeText(context, "This is not a valid symbol", Toast.LENGTH_LONG).show();
                    }
                        symbol = stock.getSymbol();
                        name = stock.getName();
                        tradePrice = stock.getLastTradePrice();
                        tradeTime = stock.getLastTradeTime();
                        change = stock.getChange();
                        range = stock.getRange();

                        symbolTextView.setText(symbol);
                        nameTextView.setText(name);
                        tradePriceTextView.setText(tradePrice);
                        tradeTimeTextView.setText(tradeTime);
                        changeTextView.setText(change);
                        rangeTextView.setText(range);
                    }
            }
                    .start();
        }
        if(name == null && invalidSymbol == true) {
            Toast.makeText(context, "This is not a valid symbol", Toast.LENGTH_LONG).show();
            invalidSymbol=false;
        }
    }

}