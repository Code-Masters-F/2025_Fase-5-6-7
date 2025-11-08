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

public class ContaInternaService {

    public static void enviarTransferenciaContaInternaParaExterna(Scanner scanner) throws SQLException {
        System.out.println("\n=== Transferência de Conta Interna para Conta Externa ===");

        System.out.print("Número da conta interna de origem: ");
        String numeroContaInterna = scanner.nextLine().trim();

        System.out.print("Agência da conta interna: ");
        String agenciaInterna = scanner.nextLine().trim();

        ContaInterna contaOrigem = ContaInternaDao.buscarContaInternaPorNumeroEAgencia(numeroContaInterna, agenciaInterna);

        if (contaOrigem == null) {
            System.err.println("Conta interna não encontrada. Verifique número/agência.");
            return;
        }

        System.out.print("Número da conta externa de destino: ");
        String numeroContaExterna = scanner.nextLine().trim();

        System.out.print("Agência da conta externa: ");
        String agenciaExterna = scanner.nextLine().trim();

        ContaExterna contaDestino = ContaExternaDao.buscarContaExternaPorNumeroEAgencia(numeroContaExterna, agenciaExterna);

        if (contaDestino == null) {
            System.err.println("Conta externa não encontrada. Verifique número/agência.");
            return;
        }

        System.out.print("Valor da transferência: ");
        BigDecimal valor;
        try {
            valor = new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido! Digite apenas números com ponto decimal (ex: 100.50");
            e.printStackTrace();
            return;
        }

        TransacaoConta transacao;
        try {
            transacao = new TransacaoConta(
                    contaDestino,
                    contaOrigem,
                    valor,
                    TipoTransacaoFiat.SAQUE,
                    LocalDateTime.now()
            );

            TransacaoContaService service = new TransacaoContaService();
            service.registrarTransacao(transacao);
            System.out.println("Transferência realizada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar transação: " + e.getMessage());
            e.printStackTrace();

        } catch(Exception e) {
            System.err.println("Erro ao criar objeto Transação: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
