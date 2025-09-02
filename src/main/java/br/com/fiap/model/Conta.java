package br.com.fiap.model;

import java.time.LocalDate;

public abstract class Conta {
    private final int id;
    private final int numeroConta;
    private final int agencia;
    private final LocalDate dataAbertura;

    public Conta(int numeroConta, int agencia, int id) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.dataAbertura = LocalDate.now();
        this.id = id;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public int getAgencia() {
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

