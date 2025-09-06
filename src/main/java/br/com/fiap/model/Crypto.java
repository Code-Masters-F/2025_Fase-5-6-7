package br.com.fiap.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Crypto {

    private int id;
    private String nome;
    private String sigla;
    private LocalDate dataLancamento;
    private double precoUnitario;

    //private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public Crypto(String nome, String sigla, int id, LocalDate dataLancamento) {
        this.nome = nome;
        this.sigla = sigla;
        this.dataLancamento = dataLancamento;
        this.id = id;
    }

    public Crypto(String nome, String sigla, LocalDate dataLancamento) {
        this.nome = nome;
        this.sigla = sigla;
        this.dataLancamento = dataLancamento;
    }

    public Crypto() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    public void setPrecoUnitario(double novoPrecoUnitario) {
        this.precoUnitario = novoPrecoUnitario;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String novaSigla) {
        this.sigla = novaSigla;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    @Override
    public String toString() {
        return "Crypto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", simbolo='" + sigla + '\'' +
                ", anoLancamento=" + getDataLancamento() +
                '}';
    }
}