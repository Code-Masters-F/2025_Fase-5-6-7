package br.com.fiap.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Pessoa {

    private String cpf;
    private String nome;

    private LocalDate dataNascimento;


    public Pessoa(String cpf, String nome, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Pessoa() {

    }

    public int getIdade() {
        LocalDate hoje = LocalDate.now();
        Period idade = Period.between(dataNascimento, hoje);
        return idade.getYears();
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setCpf(String novoCpf) {
        this.cpf = novoCpf;
    }

    public void setDataNascimento(LocalDate novaDataNascimento) {
        this.dataNascimento = novaDataNascimento;
    }

    /**
     * Retorna a data de nascimento no formato yyyy-MM-dd
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", idade=" + getIdade() +
                '}';
    }

}
