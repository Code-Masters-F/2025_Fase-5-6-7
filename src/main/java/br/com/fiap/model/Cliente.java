package br.com.fiap.model;

import java.time.LocalDate;

public class Cliente extends Pessoa{

    private int id;
    ContaCliente contaCliente;
    private String email;

    // Construtor 1
    public Cliente(String cpf, String nome, String email, LocalDate dataNascimento, int id) {
        super(cpf, nome, dataNascimento); // dd/MM/yyyy
        this.email = email;
        this.id = id;
    }

    // Construtor 2
    public Cliente(Pessoa pessoa, String email, int id) {
        super(pessoa.getCpf(), pessoa.getNome(), pessoa.getDataNascimento());
        this.email = email;
        this.id = id;
    }

    public Cliente(String cpf, String nome, String email, LocalDate dataNascimento) {
        super(cpf, nome, dataNascimento);
        this.email = email;
    }

    // Construtor 3
    public Cliente() {
        super();

    }

    public String getEmail() {
        return email;
    }

    public ContaCliente getContaCliente() {
        return contaCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    public void setNome(String novoNome) {
        super.setNome(novoNome);
    }

    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    public void setCPF(String novoCpf) {
        super.setCpf(novoCpf);
    }

    public void setDataNascimento(LocalDate novaDataNascimento) {
        super.setDataNascimento(novaDataNascimento);
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", conta=" + contaCliente +
                "} " +
                super.toString()
                ;
    }
}
