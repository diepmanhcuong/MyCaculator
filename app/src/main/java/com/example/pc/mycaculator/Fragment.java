package com.example.pc.mycaculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Fragment extends android.app.Fragment {
    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";
    private final String LAST_RESULT = "LASTRESULT";
    SharedPreferences sharedPreferences;
    Button mot,hai,ba,bon,nam,sau,bay,tam,chin,clear,doi,percent,cong,tru,nhan,chia,bang,cham,khong;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        _screen = (TextView)view.findViewById(R.id.txresult);
        sharedPreferences = this.getActivity().getSharedPreferences(LAST_RESULT,Context.MODE_PRIVATE);
        _screen.setText(sharedPreferences.getString("lastResult",""));
        setHasOptionsMenu(true);
        mot = (Button)view.findViewById(R.id.mot);
        hai = (Button)view.findViewById(R.id.hai);
        ba = (Button)view.findViewById(R.id.ba);
        bon = (Button)view.findViewById(R.id.bon);
        nam = (Button)view.findViewById(R.id.nam);
        sau = (Button)view.findViewById(R.id.sau);
        bay = (Button)view.findViewById(R.id.bay);
        tam = (Button)view.findViewById(R.id.tam);
        chin = (Button)view.findViewById(R.id.chin);
        khong = (Button)view.findViewById(R.id.khong);
        cham = (Button)view.findViewById(R.id.cham);

        mot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });

        hai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        bon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        sau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        bay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        tam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        chin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        khong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });
        cham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumber(v);
            }
        });


        clear = (Button)view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClear(v);
            }
        });
        percent = (Button)view.findViewById(R.id.percent);
        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(v);
            }
        });
        cong = (Button)view.findViewById(R.id.cong);
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(v);
            }
        });
        tru = (Button)view.findViewById(R.id.tru);
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(v);
            }
        });

        nhan = (Button)view.findViewById(R.id.nhan);
        nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(v);
            }
        });
        chia = (Button)view.findViewById(R.id.chia);
        chia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOperator(v);
            }
        });
        bang = (Button)view.findViewById(R.id.bang);
        bang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEquals(v);
            }
        });
        doi = view.findViewById(R.id.doi);
        doi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDoi(v);
            }
        });
        return view;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_operate,menu);
        super.onCreateOptionsMenu(menu, inflater);
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
                this.getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
