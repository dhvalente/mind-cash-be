package br.com.mindcash.financial.driven.account;

public final class Selects {

    private Selects() {}

    public static final String FIND_BY_ID =
            "SELECT BIN_TO_UUID(idt_account) AS idt_account " +
                    "FROM account " +
                    "WHERE idt_account = UUID_TO_BIN(?)";
}
