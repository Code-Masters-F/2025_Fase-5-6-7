package br.com.fiap.model;

import java.util.ArrayList;
import java.util.List;

public class ContaInterna extends Conta {

    private Cliente cliente;
    private double saldo; // saldo em R$
    private List<TransacaoCrypto> transacoesCryptos;
    private List<TransacaoConta> transacoesContas;
    private Carteira carteira;

    public ContaInterna(String numeroConta, String agencia, Cliente cliente, int idConta, int idCarteira) {
        super(numeroConta, agencia, idConta);
        this.cliente = cliente;
        this.saldo = 0.0;
        this.transacoesCryptos = new ArrayList<>();
        this.transacoesContas = new ArrayList<>();
        this.carteira = new Carteira(this, idCarteira);
    }

    public ContaInterna(int idConta, String numeroConta, String agencia, double saldo, int id_carteira, Cliente cliente) {
        super(numeroConta, agencia, idConta);
        this.cliente = cliente;
        this.saldo = saldo;
        this.carteira = new Carteira(this, id_carteira);
    }


    public ContaInterna() {
        super();

    }

    @Override
    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    public void setId(int novoId) {
        this.id = novoId;
    }

   public void setCliente(Cliente cliente) {
        this.cliente = cliente;
   }

   public void setNumeroConta(String novoNumeroConta) {
        this.numeroConta = novoNumeroConta;
   }

   public void setNumeroAgencia(String novaAgencia) {
        this.agencia = novaAgencia;
   }

    @Override
    public boolean depositar(float valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public List<TransacaoCrypto> getTransacoesCrypto() {
        return transacoesCryptos;
    }

    public List<TransacaoConta> getTransacoesContas() {
        return transacoesContas;
    }

    public Cliente getCliente() {
        return cliente;
    }


    public Carteira getCarteira() {
        return carteira;
    }


    @Override
    public String toString() {
        return "ContaCliente {" +
                "id=" + getId() +
                ", numeroConta='" + getNumeroConta() + '\'' +
                ", agencia='" + getNumeroAgencia() + '\'' +
                ", saldo=" + saldo +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/A") +
                ", transacoesCryptos=" + transacoesCryptos.size() +
                ", transacoesContas=" + transacoesContas.size() +
                '}';
    }

   
}


