package com.example.financemanager


import android.graphics.Color
import android.os.Build
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class Diagramm {
    var pieChart: PieChart? = null
    var cat_names = mutableListOf<String>("Еда", "Одежда", "Транспорт", "Налоги", "Прочее");
    var incomeCategories = mutableListOf<Category>(
        Category("Зарплата", 0f, "#FFA726", 3850f, "2024-11-12"),
        Category("Поадрки", 0f, "#66BB6A", 30f, "2023-11-1"),
        Category("Вклад", 0f, "#EF5350", 300f, "2023-11-5"),
    )
    var outlayCategories = mutableListOf<Category>(
        Category("Еда", 0f, "#FFA726", 200f, "2023-11-1"),
        Category("Одежда", 0f, "#66BB6A", 30f, "2023-11-2"),
        Category("Транспорт", 0f, "#EF5350", 305f, "2023-12-3"),
        Category("Налоги", 0f, "#29B6F6", 380f, "2023-11-1"),
    )

    lateinit var layoutContainer: RelativeLayout
    lateinit var currentCat:List<Category>

    constructor(_pie: PieChart?) {
        pieChart = _pie;
        currentCat=incomeCategories;

    }

    fun Draw(currentList: List<Category>?) {
        pieChart?.clearChart();
        for (i in currentCat as List<Category>) {
            pieChart!!.addPieSlice(
                PieModel(
                    i.name, i.value as Float,
                    Color.parseColor(i.color as String)
                )
            )
        }

        pieChart!!.startAnimation()
    }

    fun DrawIncome() {
        pieChart?.clearChart();
        for (i in incomeCategories) {
            pieChart!!.addPieSlice(
                PieModel(
                    i.name, i.value as Float,
                    Color.parseColor(i.color as String)
                )
            )
        }

        pieChart!!.startAnimation()
    }

    fun DrawOutlay() {
        pieChart?.clearChart();
        for (i in outlayCategories) {
            pieChart!!.addPieSlice(
                PieModel(
                    i.name, i.value as Float,
                    Color.parseColor(i.color as String)
                )
            )
        }

        pieChart!!.startAnimation()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun catAdd(_name: String, _value: Float, _color: String, _price: Float,calendar: Calendar){
        currentCat=incomeCategories;
        incomeCategories += Category(
            _name, _value, _color, _price, ((calendar.time.year-100)+2000).toString()+"-"+(calendar.time.month+1).toString()+"-"+calendar.time.date.toString()
        )
        cat_names += _name;
        Draw(currentCat)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun catAdd2(_name: String, _value: Float, _color: String, _price: Float,calendar: Calendar){
        currentCat=outlayCategories;
       outlayCategories += Category(
            _name, _value, _color, _price, ((calendar.time.year-100)+2000).toString()+"-"+(calendar.time.month+1).toString()+"-"+calendar.time.date.toString()
        )
        cat_names += _name;
        Draw(currentCat)

    }


    fun getOnDay(calendar: Calendar, boolean: Boolean): List<Category> {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
        var temp = listOf<Category>()
        if (boolean) {
            for (i in incomeCategories) {

                    if ((formatter.parse(i.date).month == calendar.time.month) && (formatter.parse(i.date).date == calendar.time.date) && (formatter.parse(
                            i.date
                        ).year == calendar.time.year)
                    )
                        temp += i;
                else
                    {}

            }
        }
        if(!boolean){
            for (i in outlayCategories) {

                if ((formatter.parse(i.date).month == calendar.time.month) && (formatter.parse(i.date).date == calendar.time.date) && (formatter.parse(
                        i.date
                    ).year == calendar.time.year)
                )
                    temp += i;
                else
                {}
            }
        }
        currentCat=temp
        Draw(currentCat);
        return temp;
    }

    fun getOnWeek(Caalendar: Calendar, boolean: Boolean):List<Category>{
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
        var temp = listOf<Category>()
        var tempCaledar=Caalendar
        tempCaledar.add(Calendar.DAY_OF_YEAR,-(Caalendar.time.day-2))
        if (boolean) {
            for (j in 1..6)
            {
                for (i in incomeCategories) {
                    if(i.value==incomeCategories[0].value)
                    {
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).date == tempCaledar.time.date) && (formatter.parse(i.date).year == tempCaledar.time.year))
                            temp= listOf<Category>(Category(i.name,i.value,i.color,i.price,i.date))
                    }
                    else
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).date == tempCaledar.time.date) && (formatter.parse(i.date).year == tempCaledar.time.year))
                        {
                            temp+=i;
                        }
                }
                tempCaledar.add(Calendar.DAY_OF_YEAR,1)
            }
        } else {
            for (j in 1..6)
            {
                for (i in outlayCategories) {
                    if(i.value==incomeCategories[0].value)
                    {
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).date == tempCaledar.time.date) && (formatter.parse(i.date).year == tempCaledar.time.year))
                            temp= listOf<Category>(Category(i.name,i.value,i.color,i.price,i.date))
                    }
                    else
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).date == tempCaledar.time.date) && (formatter.parse(i.date).year == tempCaledar.time.year))
                        {
                            temp+=i;
                        }
                }
                tempCaledar.add(Calendar.DAY_OF_YEAR,1)
            }
        }
        currentCat=temp
        Draw(currentCat);
        tempCaledar.add(Calendar.DAY_OF_YEAR,-4)
        return temp
    }
    fun getOnMonth(calendar: Calendar, boolean: Boolean):List<Category>{
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
        var temp = listOf<Category>()
        var tempCaledar=calendar;
        if (boolean) {

                for (i in incomeCategories) {
                    if(i.value==incomeCategories[0].value)
                    {
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).year == tempCaledar.time.year))
                            temp= listOf<Category>(Category(i.name,i.value,i.color,i.price,i.date))
                    }
                    else
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).year == tempCaledar.time.year))
                        {
                            temp+=i;
                        }
                }

        } else {

                for (i in outlayCategories) {
                    if(i.value==incomeCategories[0].value)
                    {
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).year == tempCaledar.time.year))
                            temp= listOf<Category>(Category(i.name,i.value,i.color,i.price,i.date))
                    }
                    else
                        if ((formatter.parse(i.date).month == tempCaledar.time.month) && (formatter.parse(i.date).year == tempCaledar.time.year))
                        {
                            temp+=i;
                        }
                }

        }
        currentCat=temp
        Draw(currentCat);
        return temp
    }

    fun getOnYear(calendar: Calendar, boolean: Boolean):List<Category>{
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
        var temp = listOf<Category>()
        var tempCaledar=calendar;
        if (boolean) {

                for (i in incomeCategories) {
                    if(i.value==incomeCategories[0].value)
                    {
                        if ((formatter.parse(i.date).year == tempCaledar.time.year))
                            temp= listOf<Category>(Category(i.name,i.value,i.color,i.price,i.date))
                    }
                    else
                        if ((formatter.parse(i.date).year == tempCaledar.time.year))
                        {
                            temp+=i;
                        }
                }

        } else {

                for (i in outlayCategories) {
                    if(i.value==incomeCategories[0].value)
                    {
                        if ((formatter.parse(i.date).year == tempCaledar.time.year))
                            temp= listOf<Category>(Category(i.name,i.value,i.color,i.price,i.date))
                    }
                    else
                        if ((formatter.parse(i.date).year == tempCaledar.time.year))
                        {
                            temp+=i;
                        }
                }

        }
        currentCat=temp
        Draw(currentCat);
        return temp
    }

    fun catSortDown(income: Boolean, item: List<Category>) {
        currentCat=item.sortedBy { it.value }.reversed();
    }

    fun catSortUp(income: Boolean, item: List<Category>) {
        currentCat=item.sortedBy { it.value };
    }
    fun getCatValue(item: List<Category>?, balance: Balance):List<Category>{
        var temp= listOf<Category>();
        for (i in item?.iterator()!!)
        {
            i.value=((i.price!!*100)/balance.currentBalance!!).toBigDecimal()?.setScale(1,RoundingMode.UP)?.toFloat();
            if(i.value!! < 1f)
                i.value=1f;
        }
        return item
    }

//    temp[i].value= ((item[i].price!!*100)?.div(balance.currentBalance!!))?.toBigDecimal()?.setScale(1,RoundingMode.UP)?.toFloat();
    fun List<Any>.swap(index1: Int, index2: Int,boolean: Boolean,item:List<Category>) {
        var color=item[index1].color
        var price=item[index1].price;
        var date =item[index1].date;
        var value=item[index1].value;
        item[index1].date =item[index2].date
        item[index1].value=item[index2].value;
        item[index1].color=item[index2].color;
        item[index1].price=item[index2].price;

        item[index2].value = value
        item[index2].color = color
        item[index2].price = price
        item[index2].date =  date

    }

}
