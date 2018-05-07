package com.example.pc.mycaculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";
    private final String LAST_RESULT = "LASTRESULT";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _screen = (TextView)findViewById(R.id.txresult);
        sharedPreferences = getSharedPreferences(LAST_RESULT, Context.MODE_PRIVATE);
        _screen.setText(sharedPreferences.getString("lastResult",""));
    }

    public void onClickNumber(View view) {
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) view;
        display += b.getText();
        updateScreen();
    }

    private void clear() {
        result = "";
        currentOperator = "";
        display = "";

    }

    private void updateScreen() {
        _screen.setText(display);
    }


    private boolean isOperator(char c) {
        switch (c){
            case '+':
            case '-':
            case '*':
            case '÷':
            case '%':
                return true;
            default: return false;
        }
    }

    public void onClickEquals(View view) {
        if(display == "") return;
        if(!getResult()) return;
        _screen.setText(display + "\n" + String.valueOf(result));
    }

    public void onClickOperator(View view) {
        if(display == "") return;

        Button b = (Button)view;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }

        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private Boolean getResult() {
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }
    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "*": return Double.valueOf(a) * Double.valueOf(b);
            case "%": return Double.valueOf(a) % Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    public void onClickClear(View view) {
        clear();
        updateScreen();
    }
    public void onClickDoi(View view){
        String resultCurrent = _screen.getText().toString();
        if(_screen.getText()!=null){
            if(resultCurrent.charAt(0)=='-'){
                Double a = Double.parseDouble(resultCurrent);
                Double b = Math.abs(a);
                String c = b.toString();

                _screen.setText(c);
            }else {
                _screen.setText("-"+resultCurrent);
            }

        }

    }

    public void save(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastResult",_screen.getText().toString());
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_operate, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    public void readData(){
//        SharedPreferences  sharedPreferences = getSharedPreferences(LAST_RESULT,Context.MODE_PRIVATE);
//        String
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear:
                clear();
                updateScreen();
                break;
            case R.id.save: save();
            finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
