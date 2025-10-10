package br.com.fiap.model;

import java.util.ArrayList;
import java.util.List;

public class ContaInterna extends Conta {

    private Cliente cliente;
    private double saldo; // saldo em R$
    private List<TransacaoCrypto> transacoesCryptos;
    private List<TransacaoConta> transacoesContas;
    private Carteira carteira;

    // Construtor usado para trazer informações do banco. Use o ClienteDao primeiro para conseguir o cliente
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

//    public boolean transferirParaContaExterna(double valor, int contaDestino, int agenciaDestino) {
//        if (this.saldo < valor) {
//            return false;
//        }
//        TransacaoConta transacao = new TransacaoConta();
//        this.saldo -= valor;
//        transacoesContas.add(transacao);
//        return true;
//    }

//    public void receberTransferencia(double valor) {
//        this.saldo += valor;
//        TransacaoConta transacaoRecebida = new TransacaoConta(valor, 0, this.getNumeroConta(), 0, this.getAgencia());
//        transacoesContas.add(transacaoRecebida);
//    }

//    public boolean receberTransacaoConta(double valor, int contaOrigem, int agenciaOrigem) {
//        TransacaoConta transacao = new TransacaoConta(valor, contaOrigem, this.getNumeroConta(), agenciaOrigem, this.getAgencia());
//        saldo += valor;
//        transacoesContas.add(transacao);
//        return true;
//    }

    /**
     * Usada para tranferencias entre os clientes cadastrados no sistema mesmo sistema
     * @param contaRemetente conta do cliente cadastrado no nosso sistema em forma de ContaCliente
     * @param valor o valor que será transferido
     * @return retorna verdadeiro se a operação for bem-sucedida
     */
//    public boolean transferirMesmoSistema(ContaCliente contaRemetente, double valor) {
//        TransacaoConta transacao = new TransacaoConta(valor, this.getNumeroConta(), contaRemetente.getNumeroConta(),
//                this.getAgencia(), contaRemetente.getAgencia());
//        saldo -= valor;
//        transacoesContas.add(transacao);
//
//        // ajusta o saldo do outro cliente e põe essa mesma tansação na lista do outro cliente
//        contaRemetente.receberTransacaoMesmoSistema(transacao, valor);
//        return true;
//    }

    /**
     * Só pode ser usado por transferirMesmoSistema e apenas por transferirMesmoSistema.
     * Para passar o valor para outro cliente cadastrado no mesmo sistema
     */
//    private boolean receberTransacaoMesmoSistema(TransacaoConta transacao, double valor) {
//        saldo += valor;
//        transacoesContas.add(transacao);
//        return true;
//    }

//    public void comprarCrypto(double quantidadeCrypto, int idCrypto) {
//        if (this.saldo >= Main.getValorUnitarioCrypto(idCrypto) * quantidadeCrypto) {
//            carteira.comprarCrypto(idCrypto, quantidadeCrypto);
//            this.saldo -= Main.getValorUnitarioCrypto(idCrypto) * quantidadeCrypto;
//        } else {
//            System.out.println("Saldo insuficiente para compra desta quantidade de criptomoeda!");
//        }
//    }

//    public void venderCrypto(double quantidadeCrypto, int idCrypto) {
//        carteira.venderCrypto(idCrypto, quantidadeCrypto);
//        this.saldo += Main.getValorUnitarioCrypto(idCrypto) * quantidadeCrypto;
//    }

//    public void exibirTransacoesContas() {
//        for (TransacaoConta transacao : transacoesContas) {
//            System.out.println(transacao);
//        }
//    }

//    public void exibirTransacoesCryptos() {
//        for (TransacaoCrypto transacao : transacoesCryptos) {
//            System.out.println(transacao);
//        }
//    }

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


