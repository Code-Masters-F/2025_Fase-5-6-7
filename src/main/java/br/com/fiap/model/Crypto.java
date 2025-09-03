package br.com.fiap.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Crypto {

    private int id;
    private String nome;
    private String sigla;
    private LocalDate dataLancamento;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public Crypto(String nome, String sigla, int id, String dataLancamento) {
        this.nome = nome;
        this.sigla = sigla;
        this.dataLancamento = LocalDate.parse(dataLancamento, formatter);
        this.id = id;
    }

    public Crypto(String nome, String sigla, int id, LocalDate dataLancamento) {
        this.nome = nome;
        this.sigla = sigla;
        this.dataLancamento = dataLancamento;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public String getDataLancamento() {
        return dataLancamento.format(formatter);
    }

    public int getId() {
        return id;
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