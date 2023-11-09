package com.example.financemanager

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import org.eazegraph.lib.charts.PieChart
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    //Объявление переменной для деиграммы
    var diagramm: PieChart? =null

    //Переменная для основного класса
    var incomeDiagramm:Diagramm?=null;

    //Элемент дял сортировки
    var spinCategories:Spinner?=null;

    //Выбраны доходы / расходы
    var incomeToggle=true;

    //Для отображения текущей даты
    lateinit var currentDate:Date;
    var dateText:TextView?=null;

    lateinit var calendar:Calendar;

    //Контейнер для добавления карточек категорий
    lateinit var layoutContainer: RelativeLayout

    //Для отображения баланса
    lateinit var balanceTitle:TextView
    lateinit var balance2Title:TextView
    lateinit var balance:Balance;

    //Кнопки для выборки по периоду
    lateinit var day:TextView;
    lateinit var week:TextView;
    lateinit var month:TextView;
    lateinit var year: TextView;

    //Цвет не выбранной кнопки
    var unselectColor: Int?=null;



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.AppTheme)

        //Получение кнопок для выборки по периоду
        day=findViewById(R.id.day)
        week=findViewById(R.id.weeks)
        month=findViewById(R.id.month)
        year=findViewById(R.id.year)

        //Получение цвета не выбранной кнопки
        unselectColor=day.getCurrentTextColor()

        //Инициализация калегдаря по текущей дате
        calendar=Calendar.getInstance(Locale("ru"))

        //Получения контейнера для диаграммы
        diagramm=findViewById(R.id.piechart);

        //Получение элемента для выбора сортировки
        spinCategories=findViewById(R.id.spnCategory) as Spinner;

        //Получение элементов для баланса
        balanceTitle=findViewById(R.id.Balance);
        balance2Title=findViewById(R.id.DiagramOutlay);
        incomeDiagramm=Diagramm(diagramm);

        //Отрисовка диаграммы дохода
        incomeDiagramm?.DrawIncome();

        //Инициализация объекта для баланса
        balance=Balance(incomeDiagramm!!)

        //Получение текущей даты
        getCurrentDate()

        //Отрисовка карточек категоий
        catUpdate(incomeDiagramm?.incomeCategories as List<Category>)

        //Выбирается доход
        setIncome(View(this))

        //Функция с добавлением события выбора для сортировки
        sort(incomeDiagramm?.currentCat)

        //Получить категории по текущему году
        getOnYear()

    }
    fun sort(currentList: List<Category>?){ //Функция для добавления обработчиков выбора категорий
        spinCategories?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(incomeToggle)
                    when(parent?.getItemAtPosition(position).toString())
                    {
                        "возрастание %"->{;incomeDiagramm?.catSortUp(true,incomeDiagramm?.currentCat!!);catUpdate(incomeDiagramm?.currentCat as List<Category>)}
                        "убывание %"->{incomeDiagramm?.catSortDown(true,incomeDiagramm?.currentCat!!);catUpdate(incomeDiagramm?.currentCat as List<Category>)}
                    }
                else
                    when(parent?.getItemAtPosition(position).toString())
                    {
                        "возрастание %"->{incomeDiagramm?.catSortUp(false,incomeDiagramm?.currentCat!!);catUpdate(incomeDiagramm?.currentCat as List<Category>)}
                        "убывание %"->{incomeDiagramm?.catSortDown(false,incomeDiagramm?.currentCat!!);catUpdate(incomeDiagramm?.currentCat as List<Category>)}
                    }
            }
        }
    }
    fun btnDateAgo(view: View){ //Функция для перевода даты на предыдущий день
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        getCurrentDate()
        getOnDate()

    }
    fun btnDateForward(view: View){//Функция для перевода даты на следующий день
        calendar.add(Calendar.DAY_OF_YEAR,1);
        getCurrentDate()
        getOnDate()

    }
    fun getCurrentDate(){ //Функция для получений текущей даты
        dateText=findViewById(R.id.txtCalendar);
        dateText?.text=calendar.time.date.toString()+" "+getMonth(calendar.time.month)
    }
    fun getOnDate(){ //Функция для получения категорий созданных в выбранный день
        //Отрисовка карточек
        catUpdate(incomeDiagramm?.getOnDay(calendar,incomeToggle))

        //Обновление баланса
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();

        //Установка цвета для выбранной кнопки
        day.setTextColor(Color.parseColor("#01C38E"))
        week.setTextColor(unselectColor!!)
        month.setTextColor(unselectColor!!)
        year.setTextColor(unselectColor!!)

    }

    fun getOnWeek(){//Функция для получения категорий созданных в выбранную неделю
        //Отрисовка карточек
        catUpdate(incomeDiagramm?.getOnWeek(calendar,incomeToggle))

        //Обновление баланса
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();

        //Установка цвета для выбранной кнопки
        getCurrentDate()
        day.setTextColor(unselectColor!!)
        week.setTextColor(Color.parseColor("#01C38E"))
        month.setTextColor(unselectColor!!)
        year.setTextColor(unselectColor!!)
    }
    fun getOnMonth(){//Функция для получения категорий созданных в выбранный месяц
        //Отрисовка карточек
        catUpdate(incomeDiagramm?.getOnMonth(calendar,incomeToggle))

        //Обновление баланса
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();

        //Установка цвета для выбранной кнопки
        getCurrentDate()
        day.setTextColor(unselectColor!!)
        week.setTextColor(unselectColor!!)
        month.setTextColor(Color.parseColor("#01C38E"))
        year.setTextColor(unselectColor!!)
    }
    fun getOnYear(){//Функция для получения категорий созданных в выбранный год
        //Отрисовка карточек
        catUpdate(incomeDiagramm?.getOnYear(calendar,incomeToggle))

        //Обновление баланса
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();

        //Установка цвета для выбранной кнопки
        getCurrentDate()
        day.setTextColor(unselectColor!!)
        week.setTextColor(unselectColor!!)
        month.setTextColor(unselectColor!!)
        year.setTextColor(Color.parseColor("#01C38E"))
    }

    fun DayClick(view: View) {//Нажатие на кнопку выборки по дню
        getOnDate()
    }

    fun WeekClick(view: View) {//Нажатие на кнопку выборки по неделе
        getOnWeek()
    }

    fun MonthClick(view: View) {//Нажатие на кнопку выборки по месяцу
        getOnMonth()
    }

    fun YearClick(view: View) {//Нажатие на кнопку выборки по году
        getOnYear()
    }

    fun getMonth(index:Int):String{//Получение текстового представления месяца по его номеру
        when(index){
            0->return "января"
            1->return "февраля"
            2->return "марта"
            3->return "апреля"
            4->return "мая"
            5->return "июня"
            6->return "июля"
            7->return "августа"
            8->return "сентября"
            9->return "октября"
            10->return "ноября"
            11 ->return "декабря"
        }
        return "ошибка"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun Ok_button1(view: View){ //Подтверждения создания категории

        //Получение элемента для ввода суммы
        val input:EditText=findViewById(R.id.price2)

        //Переменна для значения
        var value:Float=0f

        //Получение элемента для выбора категории
        val spinner:Spinner=findViewById(R.id.spinner_cat2)

        //Проверка ввода
        if(input.text.toString() != ""){
            value=input.text.toString().toFloat()
        }
        //Сообщение если в поле ввода пусто
        if(value==0f)
        {
            val toast:Toast=Toast.makeText(this ,"Вы не ввели значение",Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            //Выбор текущих категорий
            incomeDiagramm?.currentCat=incomeDiagramm?.outlayCategories!!;

            //Отрисовка текущих категорий
            catUpdate(incomeDiagramm?.currentCat)
            //Отрисовка текущей диаграммы
            incomeDiagramm?.Draw(incomeDiagramm?.currentCat)

            //Кнопка добавить
            var add:View=findViewById(R.id.addCatOutlay);
            add.visibility=View.GONE
            var main:View=findViewById(R.id.mainCat);
            main.visibility=View.VISIBLE

            //обработка выбора категории
            when(spinner.selectedItemId.toInt())
            {
                0->incomeDiagramm?.catAdd2("Еда",1f,"#FFA726",value,calendar)
                1->incomeDiagramm?.catAdd2("Одежда",1f,"#66BB6A",value,calendar)
                2->incomeDiagramm?.catAdd2("Транспорт",1f,"#EF5350",value,calendar)
                3->incomeDiagramm?.catAdd2("Налоги",1f,"#29B6F6",value,calendar)
            }
        }

    }
    fun deleteBtn(view: View){//Кнопка очистки
        val input:EditText=findViewById(R.id.price)
        val spinner:Spinner=findViewById(R.id.spinner_cat)
        input.text=null;
        spinner.setSelection(0);
    }
    fun deleteBtn2(view: View){//Кнопка очистки
        val input:EditText=findViewById(R.id.price2)
        val spinner:Spinner=findViewById(R.id.spinner_cat2)
        input.text=null;
        spinner.setSelection(0);
    }
    fun profileClick(view: View){//Кнопка перехода в профиль
        var intent:Intent=Intent(this@MainActivity,ProfileActivity::class.java)
        startActivity(intent)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun Ok_button(view: View){//Подтверждения создания категории
        //Получение элемента для ввода суммы
        val input:EditText=findViewById(R.id.price)
        //Значение
        var value:Float=0f
        //Получение элемента для выбора категории
        val spinner:Spinner=findViewById(R.id.spinner_cat)

        //Проверка поля ввода
        if(input.text.toString() != ""){
            value=input.text.toString().toFloat()
        }

        //Вывод соответствующего сообщения
        if(value==0f)
        {
            val toast:Toast=Toast.makeText(this ,"Вы не ввели значение",Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            //Выбор текущих категорий
            incomeDiagramm?.currentCat=incomeDiagramm?.incomeCategories!!;
            //Их отрисовка
            catUpdate(incomeDiagramm?.currentCat)
            //Выбор текущей диграммы
            incomeDiagramm?.Draw(incomeDiagramm?.currentCat)
            //Кнопка добавить
            var add:View=findViewById(R.id.addCatIncome);
            add.visibility=View.GONE
            var main:View=findViewById(R.id.mainCat);
            main.visibility=View.VISIBLE

            //Обработка выбора категории
            when(spinner.selectedItemId.toInt())
            {
                0->incomeDiagramm?.catAdd("Зарплата",1f,"#FFA722",value,calendar)
                1->incomeDiagramm?.catAdd("Поадрки",1f,"#66BB6A",value,calendar)
                2->incomeDiagramm?.catAdd("Вклад",1f,"#EF5350",value,calendar)
            }
        }

    }

    fun clickR( view: View){//Нажатие на кнопку +

        if(incomeToggle)
        {
            var add:View=findViewById(R.id.addCatIncome);
            add.visibility=View.VISIBLE
            var main:View=findViewById(R.id.mainCat);
            main.visibility=View.GONE;
        }
        else
        {
            var add:View=findViewById(R.id.addCatOutlay);
            add.visibility=View.VISIBLE
            var main:View=findViewById(R.id.mainCat);
            main.visibility=View.GONE;
        }

    }

    @SuppressLint("ResourceAsColor")
    fun setIncome(view: View) {//Выбор дохода
        //Получение элементов
        var incomeTitle:TextView=findViewById(R.id.income);
        var outlayTitle:TextView=findViewById(R.id.outlay);

        //Установка цвета для кнопок
        incomeTitle.setTextColor(parseColor("#FFFFFF"))
        outlayTitle.setTextColor(parseColor("#70FFFFFF"))

        //Выбор дохода
        incomeToggle=true

        //Отрисовка дохода
        incomeDiagramm?.DrawIncome();

        //Обновление баланса
        balanceTitle.text=(balance.balance)?.toInt().toString()
        balance2Title.text=balance.incomeBalance?.toInt().toString();
        balance.balanceUpdate(incomeDiagramm!!)

        //Сортировка по стандарту
        incomeDiagramm?.catSortUp(true,incomeDiagramm?.incomeCategories!!)

        //Сброс выбранной сортировки
        spinCategories?.setSelection(0);

        //Отрисовка карточек категорий
        catUpdate(incomeDiagramm?.incomeCategories as List<Category>)

        //Установка текущих категорий
        incomeDiagramm?.currentCat=incomeDiagramm?.incomeCategories!!

    }
    @SuppressLint("ResourceAsColor")
    fun setOutlay(view: View){//Выбор расхода
        //Получение элементов
        var incomeTitle:TextView=findViewById(R.id.income);
        var outlayTitle:TextView=findViewById(R.id.outlay);

        //Установка цвета для кнопок
        incomeTitle.setTextColor(parseColor("#70FFFFFF"))
        outlayTitle.setTextColor(parseColor("#FFFFFF"))

        //Выбор расхода
        incomeToggle=false

        //Отрисовка расхода
        incomeDiagramm?.DrawOutlay()

        //Обновление баланса
        balanceTitle.text=(balance.balance)?.toInt().toString()
        balance2Title.text=balance.expensesBalance?.toInt().toString();
        balance.balanceUpdate(incomeDiagramm!!)

        //Сортировка по стандарту
        incomeDiagramm?.catSortUp(false,incomeDiagramm?.outlayCategories!!)

        //Сброс выбранной сортировки
        spinCategories?.setSelection(0);

        //Отрисовка карточек категорий
        catUpdate(incomeDiagramm?.outlayCategories as List<Category>)

        //Установка текущих категорий
        incomeDiagramm?.currentCat=incomeDiagramm?.outlayCategories!!
    }

    fun converterClick(view: View){//Нажатие на кнопку для перехода в конвертер валют
        val convert:Intent= Intent(this@MainActivity,convector::class.java)
        startActivity(convert);
    }

    fun clickL( view: View){//Нажатие на кнопку календаря(Получение текущей даты)
        calendar=Calendar.getInstance()
        getCurrentDate()
        getOnDate()
    }


    fun catUpdate(currentList: List<Category>?){//Отрисовка карточек категорий
        //Получение контейнера
        layoutContainer=findViewById(R.id.infCategories);
        //Очиста контейнера
        layoutContainer.removeAllViews()

        //Получение количества категорий
        var count:IntRange? =null
        count =currentList?.indices

        //Обновление баланса
        balance.balanceUpdate(incomeDiagramm!!);
        balanceTitle.text=(balance.balance)?.toInt().toString()
        balance2Title.text=balance.incomeBalance?.toInt().toString();

        if (count != null) {
            for (i in count.iterator()){
                lateinit var items:List<Category>;
                balance.getBalance(currentList!!)

                //Получение объекста категорий
                items = incomeDiagramm?.getCatValue(currentList,balance)as List<Category>;

                //Создание новой карточки и задание размера и отступа
                val newRelativeLayout = layoutInflater.inflate(R.layout.categorie_card, null) as RelativeLayout
                val params = ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0,i*120,0,0);
                newRelativeLayout.layoutParams=params

                //Изменение карточки в зависимости от категории
                if(items!=null)
                {
                    newRelativeLayout.getChildAt(0).setBackgroundColor(parseColor((items.get(i)).color.toString()))
                    (newRelativeLayout.getChildAt(1)as TextView).text=(items.get(i)as Category).name.toString()
                    (newRelativeLayout.getChildAt(2)as TextView).text=(items.get(i)as Category).value?.toInt().toString()+"%"
                    (newRelativeLayout.getChildAt(3)as TextView).text=(items.get(i)as Category).price?.toInt().toString()+"₽"
                }
                //Добавление карточки на экран
                layoutContainer.addView(newRelativeLayout)
                newRelativeLayout.setId(i);
            }
        }

    }
}