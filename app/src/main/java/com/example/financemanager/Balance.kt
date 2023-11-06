package com.example.financemanager

class Balance {
    var balance:Float?=null;
    var incomeBalance:Float?=null;
    var expensesBalance:Float?=null;
    var currentBalance:Float?=null;

    constructor(income: Diagramm){
        balanceUpdate(income)
    }

    fun balanceUpdate(income:Diagramm){
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
    fun getBalance(current: List<Category>){
        var sum=0.0f;
        for (i in current)
        {
            sum =sum + i.price!!;
        }
        currentBalance=sum;
    }
}