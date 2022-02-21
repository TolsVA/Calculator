package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Counters counters;

    protected TextView textCounter1; // История
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

        setContentView(R.layout.activity_main);

        counters = new Counters();
        initView();
    }

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
                setTitle(getString(R.string.app_name));
//                finish();
                return true;
            case R.id.clear_history:
                counters.counter1 = "";
                setTextCounter(textCounter1, counters.getCounter1());
                return true;
            case R.id.cal_key_style:
                // сохраним настройки
                setAppTheme(CalKeyCodeStyle);
                nameStyle = getResources().getString(R.string.cal_key_style);
                setTitle(getResources().getString(R.string.cal_key_style));
                // пересоздадим активити, чтобы тема применилась
                recreate();
                return true;
            case R.id.my_cool_style:
                setAppTheme(MyCoolCodeStyle);
                nameStyle = getResources().getString(R.string.my_cool_style);
                setTitle(getResources().getString(R.string.my_cool_style));
                recreate();
                return true;
            case R.id.material_light:
                setAppTheme(AppThemeLightCodeStyle);
                nameStyle = getResources().getString(R.string.material_light);
                setTitle(getResources().getString(R.string.material_light));
                recreate();
                return true;
            case R.id.material_dark:
                setAppTheme(AppThemeDarkCodeStyle);
                nameStyle = getResources().getString(R.string.material_dark);
                setTitle(getResources().getString(R.string.material_dark));
                recreate();
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
    }


    final View.OnClickListener menuClickListener = new View.OnClickListener() {
        @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
        @Override
        public void onClick(View v) {
            MaterialButton menu = findViewById(R.id.key_menu);
//            MaterialButton menu1 = findViewById(R.id.key_1);
//            MaterialButton menu2 = findViewById(R.id.key_2);
////            Drawable imageView1 = getResources().getDrawable(R.drawable.ic_baseline_wb_sunny_24);
//            Drawable imageView2 = getResources().getDrawable(R.drawable.ic_baseline_repeat_one_24);
////            Drawable imageView3 = getResources().getDrawable(R.color.black);
////
//            menu.setIcon(imageView2);
//            menu.setBackground(getResources().getDrawable(R.color.black));
//            menu1.setBackground(getResources().getDrawable(R.color.black));
//            menu2.setBackground(getResources().getDrawable(R.color.black));
//            menu.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.sun)));
//            menu.setIconSize(50);
//            ConstraintLayout kk = findViewById(R.id.mainContainer);
//
//            MenuItem item = findViewById(R.id.cal_key_style);



            String string = getResources().getString(R.string.key_menu);
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            if (flag) {
                actionBar.show(); // показать меню
                if (nameStyle.equals("nameStyle")) {
                    setTitle(getString(R.string.app_name));
                } else {
                    setTitle(nameStyle);
                }
                menu.setText(string);

                flag = false;
            } else {
                actionBar.hide(); // скрыть меню
                if (Locale.getDefault().getLanguage().equals("ru")) {
                    menu.setText(getResources().getString(R.string.key_menu2));
                } else if (Locale.getDefault().getLanguage().equals("en")) {
                    menu.setText(getResources().getString(R.string.key_menu2));
                } else {
                    menu.setText("ыыыыы ыыы ыыыыыы");
                }
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