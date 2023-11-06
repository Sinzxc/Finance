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
    var diagramm: PieChart? =null
    var incomeDiagramm:Diagramm?=null;
    var outlayDiagramm:Diagramm?=null;
    var categories:RelativeLayout?=null
    var spinCategories:Spinner?=null;
    var incomeToggle=true;
    var dateText:TextView?=null;
    lateinit var calendar:Calendar;
    lateinit var buttonAddLayout:RelativeLayout
    lateinit var layoutContainer: RelativeLayout
    lateinit var balanceTitle:TextView
    lateinit var balance2Title:TextView
    lateinit var balance:Balance;
    lateinit var currentDate:Date;

    lateinit var day:TextView;
    lateinit var week:TextView;
    lateinit var month:TextView;
    lateinit var year: TextView;

    lateinit var selectColor: Color
    var unselectColor: Int?=null;



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.AppTheme)

        day=findViewById(R.id.day)
        week=findViewById(R.id.weeks)
        month=findViewById(R.id.month)
        year=findViewById(R.id.year)

        unselectColor=day.getCurrentTextColor()

        calendar=Calendar.getInstance(Locale("ru"))
        diagramm=findViewById(R.id.piechart);
        spinCategories=findViewById(R.id.spnCategory) as Spinner;
        balanceTitle=findViewById(R.id.Balance);
        balance2Title=findViewById(R.id.DiagramOutlay);
        incomeDiagramm=Diagramm(diagramm);
        outlayDiagramm=Diagramm(diagramm);
        var args=intent.extras;


        incomeDiagramm?.DrawIncome();
        balance=Balance(incomeDiagramm!!)
        getCurrentDate()
        catUpdate(incomeDiagramm?.incomeCategories as List<Category>)
        setIncome(View(this))
        sort(incomeDiagramm?.currentCat)
        getOnYear()

    }
    fun sort(currentList: List<Category>?){
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
    fun btnDateAgo(view: View){
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        getCurrentDate()
        getOnDate()

    }
    fun btnDateForward(view: View){
        calendar.add(Calendar.DAY_OF_YEAR,1);
        getCurrentDate()
        getOnDate()

    }
    fun getCurrentDate(){
        dateText=findViewById(R.id.txtCalendar);
        dateText?.text=calendar.time.date.toString()+" "+getMonth(calendar.time.month)
    }
    fun getOnDate(){
        catUpdate(incomeDiagramm?.getOnDay(calendar,incomeToggle))
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();
        day.setTextColor(Color.parseColor("#01C38E"))
        week.setTextColor(unselectColor!!)
        month.setTextColor(unselectColor!!)
        year.setTextColor(unselectColor!!)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
    }

    fun getOnWeek(){
        catUpdate(incomeDiagramm?.getOnWeek(calendar,incomeToggle))
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();
        getCurrentDate()
        day.setTextColor(unselectColor!!)
        week.setTextColor(Color.parseColor("#01C38E"))
        month.setTextColor(unselectColor!!)
        year.setTextColor(unselectColor!!)
    }
    fun getOnMonth(){
        catUpdate(incomeDiagramm?.getOnMonth(calendar,incomeToggle))
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();
        getCurrentDate()
        day.setTextColor(unselectColor!!)
        week.setTextColor(unselectColor!!)
        month.setTextColor(Color.parseColor("#01C38E"))
        year.setTextColor(unselectColor!!)
    }
    fun getOnYear(){
        catUpdate(incomeDiagramm?.getOnYear(calendar,incomeToggle))
        balance.getBalance(incomeDiagramm?.currentCat!!)
        balance2Title.text=balance?.currentBalance?.toInt().toString();
        getCurrentDate()
        day.setTextColor(unselectColor!!)
        week.setTextColor(unselectColor!!)
        month.setTextColor(unselectColor!!)
        year.setTextColor(Color.parseColor("#01C38E"))
    }

    fun DayClick(view: View) {
        getOnDate()
    }

    fun WeekClick(view: View) {
        getOnWeek()
    }

    fun MonthClick(view: View) {
        getOnMonth()
    }

    fun YearClick(view: View) {
        getOnYear()
    }

    fun getMonth(index:Int):String{
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
    fun Ok_button1(view: View){
        val input:EditText=findViewById(R.id.price2)
        var value:Float=0f
        val spinner:Spinner=findViewById(R.id.spinner_cat2)
        if(input.text.toString() != ""){
            value=input.text.toString().toFloat()
        }
        if(value==0f)
        {
            val toast:Toast=Toast.makeText(this ,"Вы не ввели значение",Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            incomeDiagramm?.currentCat=incomeDiagramm?.outlayCategories!!;
            catUpdate(incomeDiagramm?.currentCat)
            incomeDiagramm?.Draw(incomeDiagramm?.currentCat)
            var add:View=findViewById(R.id.addCatOutlay);
            add.visibility=View.GONE
            var main:View=findViewById(R.id.mainCat);
            main.visibility=View.VISIBLE
            when(spinner.selectedItemId.toInt())
            {
                0->incomeDiagramm?.catAdd2("Еда",1f,"#FFA726",value,calendar)
                1->incomeDiagramm?.catAdd2("Одежда",1f,"#66BB6A",value,calendar)
                2->incomeDiagramm?.catAdd2("Транспорт",1f,"#EF5350",value,calendar)
                3->incomeDiagramm?.catAdd2("Налоги",1f,"#29B6F6",value,calendar)
            }
        }

    }
    fun deleteBtn(view: View){
        val input:EditText=findViewById(R.id.price)
        val spinner:Spinner=findViewById(R.id.spinner_cat)
        input.text=null;
        spinner.setSelection(0);
    }
    fun deleteBtn2(view: View){
        val input:EditText=findViewById(R.id.price2)
        val spinner:Spinner=findViewById(R.id.spinner_cat2)
        input.text=null;
        spinner.setSelection(0);
    }
    fun profileClick(view: View){
        var intent:Intent=Intent(this@MainActivity,ProfileActivity::class.java)
        startActivity(intent)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun Ok_button(view: View){
        val input:EditText=findViewById(R.id.price)
        var value:Float=0f
        val spinner:Spinner=findViewById(R.id.spinner_cat)
        if(input.text.toString() != ""){
            value=input.text.toString().toFloat()
        }
        if(value==0f)
        {
            val toast:Toast=Toast.makeText(this ,"Вы не ввели значение",Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            incomeDiagramm?.currentCat=incomeDiagramm?.incomeCategories!!;
            catUpdate(incomeDiagramm?.currentCat)
            incomeDiagramm?.Draw(incomeDiagramm?.currentCat)
            var add:View=findViewById(R.id.addCatIncome);
            add.visibility=View.GONE
            var main:View=findViewById(R.id.mainCat);
            main.visibility=View.VISIBLE
            when(spinner.selectedItemId.toInt())
            {
                0->incomeDiagramm?.catAdd("Зарплата",1f,"#FFA722",value,calendar)
                1->incomeDiagramm?.catAdd("Поадрки",1f,"#66BB6A",value,calendar)
                2->incomeDiagramm?.catAdd("Вклад",1f,"#EF5350",value,calendar)
            }
        }

    }

    fun clickR( view: View){

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
    fun setIncome(view: View) {
        var incomeTitle:TextView=findViewById(R.id.income);
        var outlayTitle:TextView=findViewById(R.id.outlay);
        incomeTitle.setTextColor(parseColor("#FFFFFF"))
        outlayTitle.setTextColor(parseColor("#70FFFFFF"))
        incomeToggle=true
        incomeDiagramm?.DrawIncome();
        balanceTitle.text=(balance.balance)?.toInt().toString()
        balance2Title.text=balance.incomeBalance?.toInt().toString();
        incomeDiagramm?.catSortUp(true,incomeDiagramm?.incomeCategories!!)
        spinCategories?.setSelection(0);
        catUpdate(incomeDiagramm?.incomeCategories as List<Category>)
        balance.balanceUpdate(incomeDiagramm!!)
        incomeDiagramm?.currentCat=incomeDiagramm?.incomeCategories!!

    }
    @SuppressLint("ResourceAsColor")
    fun setOutlay(view: View){
        var incomeTitle:TextView=findViewById(R.id.income);
        var outlayTitle:TextView=findViewById(R.id.outlay);
        incomeTitle.setTextColor(parseColor("#70FFFFFF"))
        outlayTitle.setTextColor(parseColor("#FFFFFF"))
        incomeToggle=false
        incomeDiagramm?.DrawOutlay()
        balanceTitle.text=(balance.balance)?.toInt().toString()
        balance2Title.text=balance.expensesBalance?.toInt().toString();
        incomeDiagramm?.catSortUp(false,incomeDiagramm?.outlayCategories!!)
        spinCategories?.setSelection(0);
        catUpdate(incomeDiagramm?.outlayCategories as List<Category>)
        balance.balanceUpdate(incomeDiagramm!!)
        incomeDiagramm?.currentCat=incomeDiagramm?.outlayCategories!!
    }

    fun converterClick(view: View){
        val convert:Intent= Intent(this@MainActivity,convector::class.java)
        startActivity(convert);
    }

    fun clickL( view: View){
        calendar=Calendar.getInstance()
        getCurrentDate()
        getOnDate()
    }


    fun catUpdate(currentList: List<Category>?){
        layoutContainer=findViewById(R.id.infCategories);
        layoutContainer.removeAllViews()
        var count:IntRange? =null
        count =currentList?.indices
        balance.balanceUpdate(incomeDiagramm!!);
        balanceTitle.text=(balance.balance)?.toInt().toString()
        balance2Title.text=balance.incomeBalance?.toInt().toString();

        if (count != null) {
            for (i in count.iterator()){
                lateinit var items:List<Category>;
                balance.getBalance(currentList!!)
                items = incomeDiagramm?.getCatValue(currentList,balance)as List<Category>;

                val newRelativeLayout = layoutInflater.inflate(R.layout.categorie_card, null) as RelativeLayout
                val params = ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0,i*120,0,0);

                newRelativeLayout.layoutParams=params
                if(items!=null)
                {
                    newRelativeLayout.getChildAt(0).setBackgroundColor(parseColor((items.get(i)).color.toString()))
                    (newRelativeLayout.getChildAt(1)as TextView).text=(items.get(i)as Category).name.toString()
                    (newRelativeLayout.getChildAt(2)as TextView).text=(items.get(i)as Category).value?.toInt().toString()+"%"
                    (newRelativeLayout.getChildAt(3)as TextView).text=(items.get(i)as Category).price?.toInt().toString()+"₽"
                }
                layoutContainer.addView(newRelativeLayout)
                newRelativeLayout.setId(i);
            }
        }

    }
}