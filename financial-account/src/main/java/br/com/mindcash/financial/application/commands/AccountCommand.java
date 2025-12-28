package br.com.mindcash.financial.application.commands;

sealed public interface AccountCommand permits RegisterIncome, RegisterExpense{
}