package br.com.fiap.service;

import br.com.fiap.dao.ContaExternaDao;
import br.com.fiap.dao.ContaInternaDao;
import br.com.fiap.model.ContaExterna;
import br.com.fiap.model.ContaInterna;
import br.com.fiap.model.TipoTransacaoFiat;
import br.com.fiap.model.TransacaoConta;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ContaExternaService {

    public static void enviarTransferenciaContaExternaParaInterna(Scanner scanner) {
        System.out.println("\n=== Transferência de Conta Externa para Conta Interna ===");

        System.out.print("Digite o id da Conta Externa cadastrada: ");
        int idContaExterna = Integer.parseInt(scanner.nextLine().trim());

        ContaExterna contaExterna = null;
        try {
            contaExterna = ContaExternaDao.buscarContaExternaPorId(idContaExterna);

            if (contaExterna == null) {
                System.err.println("Conta externa não encontrada/não cadastrada. Verifique número/agência/id.");
                return;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao procurar Conta Externa no Banco: " + e.getMessage());
        }

        System.out.print("Digite o id da Conta Interna Cadastrada que deseja realizar a transferencia: ");
        int idContaInterna = Integer.parseInt(scanner.nextLine().trim());

        ContaInterna contaInterna = null;
        try {
            contaInterna = ContaInternaDao.buscarContaInternaPorId(idContaInterna);

            if (contaInterna == null) {
                System.err.println("Conta interna não encontrada/não cadastrada. Verifique número/agência/id.");
                return;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao procurar Conta Interna no Banco: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.print("Digite o valor que deseja transferir: ");
        BigDecimal valor = null;
        try {
            valor = new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor para BigDecimal: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        TransacaoConta transacao = null;
        try {
            transacao = new TransacaoConta();
            transacao.setContaInterna(contaInterna);
            transacao.setContaExterna(contaExterna);
            transacao.setValor(valor);
            transacao.setTipo(TipoTransacaoFiat.DEPOSITO);
            transacao.setDataHora(LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            TransacaoContaService service = new TransacaoContaService();
            service.registrarTransacao(transacao);
            System.out.println("Transferência realizada com sucesso!");
        } catch(SQLException e) {
            System.err.println("Erro ao registrar transação: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
