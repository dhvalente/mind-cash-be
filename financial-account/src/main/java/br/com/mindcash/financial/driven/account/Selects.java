package br.com.mindcash.financial.driven.account;

public final class Selects {

    private Selects() {}

    public static final String SELECT_ALL = "SELECT * FROM expenses";
    public static final String FIND_BY_ID = "SELECT idt_account FROM account WHERE idt_account = :id";

}