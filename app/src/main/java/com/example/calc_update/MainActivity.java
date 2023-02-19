package com.example.calc_update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView result,solution;
    MaterialButton btnBrackOpen,btnBrackClose;
    MaterialButton btnDivide,btnMultiply,btnPlus,btnMinus,btnEquals;
    MaterialButton btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    MaterialButton btnAC,btnDot, btnhistory;
    String calculateDataStr = "";
    String calculateData = "";
    private ArrayList<String> His_List = new ArrayList<>();
    private static final String STATE_COUNTER = "counter";
    private static final String STATE_HIST = "history";

    protected void onSaveInstanceState (@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        if(solution.getText().toString()!= null) {
            outState.putString("solution", solution.getText().toString());
        }
        if(result.getText().toString()!= null) {
            outState.putString("result", result.getText().toString());
        }
        outState.putString(STATE_COUNTER,String.valueOf(result.getText()));
        outState.putStringArrayList(STATE_HIST, His_List);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.get("solution")!= null) {
            solution.setText(savedInstanceState.get("solution").toString());
        }
        if (savedInstanceState.get("result")!= null) {
            result.setText(savedInstanceState.get("result").toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);
        btnhistory = this.findViewById(R.id.btn_history);
        assignId(btnBrackOpen,R.id.btn_OB);
        assignId(btnBrackClose,R.id.btn_CB);
        assignId(btnDivide,R.id.btn_divide);
        assignId(btnMultiply,R.id.btn_multiply);
        assignId(btnPlus,R.id.btn_plus);
        assignId(btnMinus,R.id.btn_minus);
        assignId(btnEquals,R.id.btn_equal);
        assignId(btn0,R.id.btn_0);
        assignId(btn1,R.id.btn_1);
        assignId(btn2,R.id.btn_2);
        assignId(btn3,R.id.btn_3);
        assignId(btn4,R.id.btn_4);
        assignId(btn5,R.id.btn_5);
        assignId(btn6,R.id.btn_6);
        assignId(btn7,R.id.btn_7);
        assignId(btn8,R.id.btn_8);
        assignId(btn9,R.id.btn_9);
        assignId(btnAC,R.id.btn_ac);
        assignId(btnDot,R.id.btn_dot);

        if (savedInstanceState != null){
            calculateDataStr = savedInstanceState.getString(STATE_COUNTER, "");
            result.setText(calculateDataStr);
            His_List = savedInstanceState.getStringArrayList(STATE_HIST);
        }

        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                if(calculateDataStr.contains("null")) {
                    calculateDataStr = calculateDataStr.substring(4);
                }
                intent.putStringArrayListExtra("LS", His_List);
                startActivity(intent);
            }
        });
    }

    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton btn =(MaterialButton) view;
        String btnText = btn.getText().toString();
        String dataToCalculate = solution.getText().toString();


        if(btnText.equals("AC")){
            solution.setText("");
            result.setText("0");
            return;
        }
        if(btnText.equals("=")){
            dataToCalculate = dataToCalculate.replaceAll("x", "*");
            dataToCalculate = dataToCalculate.replaceAll("÷", "/");
            calculateData = dataToCalculate;
            String finalResult = getResult(dataToCalculate);
            calculateData+= " = " + finalResult;
            calculateDataStr = calculateData;
            if(calculateDataStr.contains("null")) {
                calculateDataStr = calculateDataStr.substring(4);
            }
            His_List.add(calculateDataStr);
            if(!finalResult.equals("Error")){
                result.setText(finalResult);

            }
            solution.setText(result.getText());
            return;
        }else {
            dataToCalculate += btnText;
        }

        solution.setText(dataToCalculate);

    }

    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}