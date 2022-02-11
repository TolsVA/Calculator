package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Counters counters;

    private TextView textCounter1; // История
    private TextView textCounter2; // События
    private TextView textCounter3; // Результат

    HashMap<Integer, Integer> digits = new HashMap<>();
    HashMap<Integer, String> lexeme = new HashMap<>();
    HashMap<Integer, String> bracket = new HashMap<>();

    private final static String keyCounters = "Counters";

    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Yannis");


        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        counters = new Counters();
        initView();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item1:
                makeToast("это «Вы нажали кнопку« item1 »!»");
                return true;
            case R.id.item2:
                makeToast("это «Вы нажали кнопку« item2 »!»");
                return true;
            case R.id.item3:
                makeToast("это «Вы нажали кнопку« item3 »!»");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Сохранение данных
    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(keyCounters, counters);
    }

    // Восстановление данных
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        counters = instanceState.getParcelable(keyCounters);
        setTextCounters();
    }

    private void setTextCounters() {
        setTextCounter(textCounter1, counters.getCounter1());
        setTextCounter(textCounter2, counters.getCounter2());
        setTextCounter(textCounter3, counters.getCounter3());
    }

    private void initView() {
        // Получить пользовательские элементы по идентификатору
        textCounter1 = findViewById(R.id.history);
        textCounter2 = findViewById(R.id.developments);
        textCounter3 = findViewById(R.id.result);

        digits.put(R.id.key_0, 0);
        digits.put(R.id.key_1, 1);
        digits.put(R.id.key_2, 2);
        digits.put(R.id.key_3, 3);
        digits.put(R.id.key_4, 4);
        digits.put(R.id.key_5, 5);
        digits.put(R.id.key_6, 6);
        digits.put(R.id.key_7, 7);
        digits.put(R.id.key_8, 8);
        digits.put(R.id.key_9, 9);

        lexeme.put(R.id.key_plus, "+");
        lexeme.put(R.id.key_minus, "-");
        lexeme.put(R.id.key_multi, "×");
        lexeme.put(R.id.key_div, "÷");
        lexeme.put(R.id.key_dot, ",");
        lexeme.put(R.id.key_equals, "=");

        bracket.put(R.id.key_left_bracket, "(");
        bracket.put(R.id.key_right_bracket, ")");

        findViewById(R.id.key_del).setOnClickListener(buttonDelClickListener);

        findViewById(R.id.key_back).setOnClickListener(buttonBackClickListener);

        findViewById(R.id.key_0).setOnClickListener(digitClickListener);
        findViewById(R.id.key_1).setOnClickListener(digitClickListener);
        findViewById(R.id.key_2).setOnClickListener(digitClickListener);
        findViewById(R.id.key_3).setOnClickListener(digitClickListener);
        findViewById(R.id.key_4).setOnClickListener(digitClickListener);
        findViewById(R.id.key_5).setOnClickListener(digitClickListener);
        findViewById(R.id.key_6).setOnClickListener(digitClickListener);
        findViewById(R.id.key_7).setOnClickListener(digitClickListener);
        findViewById(R.id.key_8).setOnClickListener(digitClickListener);
        findViewById(R.id.key_9).setOnClickListener(digitClickListener);

        findViewById(R.id.key_plus).setOnClickListener(lexemeClickListener);
        findViewById(R.id.key_minus).setOnClickListener(lexemeClickListener);
        findViewById(R.id.key_multi).setOnClickListener(lexemeClickListener);
        findViewById(R.id.key_div).setOnClickListener(lexemeClickListener);
        findViewById(R.id.key_dot).setOnClickListener(lexemeClickListener);
        findViewById(R.id.key_equals).setOnClickListener(lexemeClickListener);

        findViewById(R.id.key_left_bracket).setOnClickListener(bracketClickListener);
        findViewById(R.id.key_right_bracket).setOnClickListener(bracketClickListener);

        findViewById(R.id.key_menu).setOnClickListener(menuClickListener);

//        Button keyRightBracket = (Button) findViewById(R.id.key_right_bracket);
//        keyRightBracket.setEnabled(false);
//        ((Button) findViewById(R.id.key_menu)).setCompoundDrawablesWithIntrinsicBounds(
//                AppCompatResources.getDrawable(this, R.drawable.ic_baseline_brightness_3_24), null, null, null);

    }

    final View.OnClickListener menuClickListener = new View.OnClickListener() {
        @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
        @Override
        public void onClick(View v) {
            MaterialButton menu = findViewById(R.id.key_menu);
            Drawable imageView1 = getResources().getDrawable(R.drawable.ic_baseline_wb_sunny_24);
            Drawable imageView2 = getResources().getDrawable(R.drawable.ic_baseline_brightness_3_24);
            Drawable imageView3 = getResources().getDrawable(R.color.black);
 //           menu.setIcon(imageView1);


            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            if (flag) {
                actionBar.show(); // показать меню
                setTitle("Кака");
                actionBar.setBackgroundDrawable(imageView3);
                menu.setText("No ActionBar");

                flag = false;
            } else {
                actionBar.hide(); // скрыть меню
                menu.setText("Dark ActionBar");
                flag = true;
            }
        }
    };

    View.OnClickListener digitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!counters.getCounter2().equals("")) {
                if (counters.getCounter2().charAt(counters.getCounter2().length() - 1) == ')') {
                    counters.setCounter2("×");
                }
            }
            counters.setCounter2(String.valueOf(digits.get(v.getId())));
            setTextCounter(textCounter2, counters.getCounter2());
            setTextCounter(textCounter3, counters.getCounter3());
        }
    };

    View.OnClickListener lexemeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            back(lexeme.get(v.getId()));
        }
    };

    View.OnClickListener bracketClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            backParenthesis(bracket.get(v.getId()));
        }
    };

    public View.OnClickListener buttonDelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            counters.setCounter2("del");
            setTextCounter(textCounter2, counters.getCounter2());
            setTextCounter(textCounter3, counters.getCounter3());
        }
    };

    public View.OnClickListener buttonBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!counters.getCounter2().equals("")) {
                counters.setCounter2("bk");

                setTextCounter(textCounter2, counters.getCounter2());
                setTextCounter(textCounter3, counters.getCounter3());
            }
        }
    };

    private void back(String symbol) {
        if (counters.getCounter2().equals("") ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '(') {
            return;
        }

        if (counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '+' ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '-' ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '×' ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '÷' ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == ',') {
            counters.setCounter2("bk");
        }

        if (symbol.equals("=")) {
            counters.setCounter1();
            setTextCounter(textCounter1, counters.getCounter1());
            setTextCounter(textCounter2, counters.getCounter2());
            setTextCounter(textCounter3, counters.getCounter3());
        } else {
            if (!symbol.equals(",") || counters.getCounter2().charAt(counters.getCounter2().length() - 1) != ')') {
                counters.setCounter2(symbol);
                setTextCounter(textCounter2, counters.getCounter2());
            }
        }
    }

    private void backParenthesis(String symbol) {

        if (counters.getCounter2().equals("") && symbol.equals("(")) {
            counters.setCounter2(symbol);
            setTextCounter(textCounter2, counters.getCounter2());
            return;
        }

        if (counters.getCounter2().equals("") && symbol.equals(")")) {
            return;
        }

        if (counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '(' &&
                symbol.equals("(")) {
            counters.setCounter2(symbol);
            setTextCounter(textCounter2, counters.getCounter2());
            return;
        }

        int l = 0;
        int r = 0;
        for (int i = 0; i < counters.getCounter2().length(); i++) {
            if (counters.getCounter2().charAt(i) == '(') {
                l++;
            }
            if (counters.getCounter2().charAt(i) == ')') {
                r++;
            }
        }

        if (counters.getCounter2().charAt(counters.getCounter2().length() - 1) == ')') {
            if (symbol.equals("(")) {
                counters.setCounter2("×");
                counters.setCounter2(symbol);
            } else if (symbol.equals(")")) {
                if (l > r) {
                    counters.setCounter2(symbol);
//                } else {
//                    makeToast("\"ВСЕ СКОБКИ ЗАКРЫТЫ\"");
                }
            }
        }

        if (counters.getCounter2().charAt(counters.getCounter2().length() - 1) == ',') {
            counters.setCounter2("bk");
        }

        if (counters.getCounter2().charAt(counters.getCounter2().length() - 1) <= '9' &&
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) >= '0') {
            if (symbol.equals("(")) {
                counters.setCounter2("×");
                counters.setCounter2(symbol);
            } else if (symbol.equals(")")) {
                if (l > r) {
                    counters.setCounter2(symbol);
//                } else {
//                    makeToast("\"ВСЕ СКОБКИ ЗАКРЫТЫ\"");
                }
            }
        }

        if (counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '+' ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '-' ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '×' ||
                counters.getCounter2().charAt(counters.getCounter2().length() - 1) == '÷') {
            if (symbol.equals("(")) {
                counters.setCounter2(symbol);
            } else if (symbol.equals(")")) {
                if (l > r) {
                    counters.setCounter2("bk");
                    counters.setCounter2(symbol);
//                } else {
//                    makeToast("\"ВСЕ СКОБКИ ЗАКРЫТЫ\"");
                }
            }
        }

        setTextCounter(textCounter2, counters.getCounter2());
    }

    private void setTextCounter(TextView textCounter, String counter) {
        textCounter.setText(counter);
    }

    @Override
    public void onClick(View v) {
    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}