package com.example.financemanager

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt


class convector : AppCompatActivity() {
    //Переменны для хранения валют
    var baseCurrency:String="EUR";
    var secondCurrency:String="RUB";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convector)

        //Обработчики выбора валют
        val currency=findViewById<Spinner>(R.id.currency);
        currency.setSelection(0);
        currency?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(parent?.getItemAtPosition(position).toString())
                {
                    "EUR"->{baseCurrency="EUR"}
                    "RUB"->{baseCurrency="RUB"}
                    "USD"->{baseCurrency="USD"}
                    "GBP"->{baseCurrency="GBP"}
                    "UAH"->{baseCurrency="UAH"}
                }
            }

        }

        val currency2=findViewById<Spinner>(R.id.currency2);
        currency2.setSelection(1);
        currency2?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(parent?.getItemAtPosition(position).toString())
                {
                    "EUR"->{secondCurrency="EUR"}
                    "RUB"->{secondCurrency="RUB"}
                    "USD"->{secondCurrency="USD"}
                    "GBP"->{secondCurrency="GBP"}
                    "UAH"->{secondCurrency="UAH"}
                }
            }

        }

    }
    fun convertClick(view: View) {//Нажатие на кнопку конвертировать
        request()
    }
    fun btn00(view: View)//Клавиатура
    {
        val btn = view as Button
        var text:TextView = findViewById(R.id.input_text)
        val output = findViewById<TextView>(R.id.output_text)
        when (btn.id) {
            R.id.btn0 -> if(text.text!="0")out("0");
            R.id.btn1 -> out("1");
            R.id.btn2 -> out("2");
            R.id.btn3 -> out("3");
            R.id.btn4 -> out("4");
            R.id.btn5 -> out("5");
            R.id.btn6 -> out("6");
            R.id.btn7 -> out("7");
            R.id.btn8 -> out("8");
            R.id.btn9 -> out("9");
            R.id.btnClear -> {text.text="0";output.text="0"}
            R.id.btnz -> {
                if(text.text.indexOf(',')==-1)
                    text.append(",");
            }
        }
    }
    fun out(string: String){//Функция добавления числа в строку ввода
        var text:TextView = findViewById(R.id.input_text)
        if(text.text.toString()!="0")
            text.append(string)
        else
            text.text=string;

    }
    fun request(){//Функция для отправки GET запроса и обработки ответа
        //Получение элементов ввода и вывода
        val output = findViewById<TextView>(R.id.output_text)
        val input = findViewById<TextView>(R.id.input_text)
        //Объект запроса
        var g=HttpGetRequest();
        //Ссылка, в которую вставляются API-KEY и выбранные валюты
        val url = "https://api.currencyapi.com/v3/latest?apikey=cur_live_Oz7Mt6iPIXgdu57PdeXF2EiRJRJVZ44AhBOwxzPK&currencies=${secondCurrency}&base_currency=${baseCurrency}"
        //Получение результата
        val result = HttpGetRequest().execute(url).get()
        //Обработка ответа и вывод в поле
        var requestSplit=result.split(':');
        var requestComplete=requestSplit[8].replace('}',' ');
        var Currency=requestComplete.toFloat();
        output.text=(String.format("%.1f",(Currency*input.text.toString().replace(',','.').toFloat())).toString());
    }
    fun mainClick(view: View){//Переход на главную страницу
        finish()

    }
    fun profileClick(view:View){//Переход в профиль
        finish()
        var intent: Intent = Intent(this@convector,ProfileActivity::class.java)
        startActivity(intent);
    }

}
