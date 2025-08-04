package com.finance.app;

import java.time.LocalDate;

public abstract class Conta {
    private final int id;
    private final String numeroConta;
    private final String agencia;
    private final LocalDate dataAbertura;

    public Conta(String numeroConta, String agencia, int id) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.dataAbertura = LocalDate.now();
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public int getId() {
        return id;
    }

    public abstract double getSaldo();

    public abstract boolean depositar(float valor);
}

