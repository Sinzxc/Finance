package com.example.financemanager

class Balance {//Класс для хранения работы с балансом
    //Объявление полей
    var balance:Float?=null;
    var incomeBalance:Float?=null;
    var expensesBalance:Float?=null;
    var currentBalance:Float?=null;

    constructor(income: Diagramm){
        balanceUpdate(income)
    }

    fun balanceUpdate(income:Diagramm){//Функция для обновления основного баланса, баланса доходов и расходов
        var sum=0.0f;
        for (i in income.incomeCategories)
        {
            sum =sum + i.price!!;
        }
        incomeBalance=sum;
        sum=0.0f;
        for (i in income.outlayCategories)
        {
            sum =sum + i.price!!;
        }
        expensesBalance=sum;
        balance=incomeBalance as Float- expensesBalance as Float
        currentBalance=sum;
    }
    fun getBalance(current: List<Category>){//Функция для получения баланса по по задданным категориям
        var sum=0.0f;
        for (i in current)
        {
            sum =sum + i.price!!;
        }
        currentBalance=sum;
    }
}