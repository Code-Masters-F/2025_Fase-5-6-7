package br.com.fiap.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Carteira {

    private ContaInterna dono;
    private int id;
    Set<PosseClienteCrypto> listaDeCryptos = new HashSet<>();

    public Carteira(ContaInterna dono, int id) {
        this.dono = dono;
        this.id = id;
    }

    public Carteira() {
    }

    public ContaInterna getContaCliente() {
        return dono;
    }

    public void setContaCliente(ContaInterna novoDono) {
        this.dono = novoDono; 
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    /**
     * Busca na lista de cryptos uma moeda pelo seu id
     * @param idCrypto id da Crypto cadastrada na main
     * @return retorna a criptomoeda caso o usuário a possua (em forma de
     * PosseClienteCrypto que alem da moeda possui a quantidade), ou nulo
     * caso contrario
     */
    protected PosseClienteCrypto buscarCryptoEmPosse(int idCrypto) {
        for (PosseClienteCrypto posseCrypto : listaDeCryptos) {
            if (posseCrypto.getCrypto().getId() == idCrypto) {
                return posseCrypto;
            }
        }
        return null;
    }

//    void venderCrypto(int idCrypto, double quantidade) {
//
//        PosseClienteCrypto posseCrypto = buscarCryptoEmPosse(idCrypto);
//
//        if (posseCrypto == null) {
//            System.out.println("O cliente ainda não possui essa criptomoeda na carteira!");
//        } else if (posseCrypto.getQuantidade() >= quantidade) {
//            posseCrypto.setQuantidade(posseCrypto.getQuantidade() - quantidade);
//
//            TransacaoCrypto transacao = new TransacaoCrypto(dono, quantidade, Main.getValorUnitarioCrypto(idCrypto),
//                    posseCrypto.getCrypto(), "venda");
//
//            dono.getTransacoesCrypto().add(transacao);
//
//            System.out.println("Venda realizada com sucesso!");
//        } else {
//            System.out.println("Quantidade de venda maior do que se possui na carteira!");
//        }
//    }

    public Map<Integer, Double> getCriptos() {
        Map<Integer, Double> mapaCriptos = new HashMap<>();
        for (PosseClienteCrypto posse : listaDeCryptos) {
            mapaCriptos.put(posse.getCrypto().getId(), posse.getQuantidade());
        }
        return mapaCriptos;
    }

//    /**
//     * @param idCrypto id da cryptomoeda cadastrada no sistema
//     * @param quantidade quantidade fracionada que se quer comprar dessa criptomoeda
//     */
//    void comprarCrypto(int idCrypto, double quantidade) {
//
//        PosseClienteCrypto posseCrypto = buscarCryptoEmPosse(idCrypto);
//
//        // se for nulo é porque o cliente ainda não tem essa criptomoeda na carteira
//        if(posseCrypto == null) {
//            for (Crypto crypto : Main.todasCryptosCadastradas) {
//                if (crypto.getId() == idCrypto) {
//                    posseCrypto = new PosseClienteCrypto(crypto);
//                    posseCrypto.setQuantidade(quantidade);
//                    listaDeCryptos.add(posseCrypto);
//
//                    TransacaoCrypto transacao = new TransacaoCrypto(dono, quantidade, Main.getValorUnitarioCrypto(idCrypto),
//                            crypto, "compra");
//
//                    dono.getTransacoesCrypto().add(transacao);
//
//                    System.out.println("Compra realizada com sucesso!");
//                    return;
//                }
//            }
//        }else {
//            posseCrypto.setQuantidade(posseCrypto.getQuantidade() + quantidade);
//            System.out.println("Compra realizada com sucesso!");
//        }
//    }

}
