package com.finance.app;

public class Cliente extends Pessoa{

    private int id;
    ContaCliente contaCliente;
    private String email;

    // Construtor 1
    Cliente(String cpf, String nome, String email, String dataNascimento, int id) {
        super(cpf, nome, dataNascimento); // dd/MM/yyyy
        this.email = email;
        this.id = id;
    }

    // Construtor 2
    Cliente(Pessoa pessoa, String email, int id) {
        super(pessoa.getCpf(), pessoa.getNome(), pessoa.getDataNascimento());
        this.email = email;
        this.id = id;
    }


    public void criarConta(String numeroConta, String agencia, int id) {
        this.contaCliente = new ContaCliente(numeroConta, agencia, this, id);
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
