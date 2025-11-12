package br.com.fiap.service;

import br.com.fiap.dao.ClienteDao;
import br.com.fiap.dao.ContaExternaDao;
import br.com.fiap.dao.ContaInternaDao;
import br.com.fiap.dao.HistoricoTransacaoDao;
import br.com.fiap.model.*;
import br.com.fiap.utils.CondicoesUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public static void consultarContaInterna(Scanner scanner) {
        System.out.println("\n=== Consultar Conta Interna ===");
        System.out.print("Digite o ID do cliente: ");

        int idCliente;
        try {
            idCliente = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        try {
            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.consultarClientePorId(idCliente);

            if (cliente == null) {
                System.err.println("Cliente não encontrado com ID: " + idCliente);
                return;
            }

            ContaInternaDao contaDao = new ContaInternaDao();
            ContaInterna conta = contaDao.buscarContaInternaPorCliente(cliente);

            if (conta == null) {
                System.err.println("Este cliente não possui conta interna cadastrada.");
                return;
            }

            System.out.println("\n--- Dados da Conta Interna ---");
            System.out.println("ID da Conta: " + conta.getId());
            System.out.println("Número da Conta: " + conta.getNumeroConta());
            System.out.println("Agência: " + conta.getNumeroAgencia());
            System.out.printf("Saldo: R$ %,.2f%n", conta.getSaldo());
            System.out.println("\n--- Dados do Titular ---");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Email: " + cliente.getEmail());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar conta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void transferirEntreContasInternas(Scanner scanner) {
        System.out.println("\n=== Transferir Entre Contas Internas ===");

        // Conta origem
        System.out.print("Digite o ID da conta ORIGEM: ");
        int idOrigem;
        try {
            idOrigem = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        // Conta destino
        System.out.print("Digite o ID da conta DESTINO: ");
        int idDestino;
        try {
            idDestino = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        // Validação básica
        if (idOrigem == idDestino) {
            System.err.println("Conta de origem e destino não podem ser iguais!");
            return;
        }

        // Valor
        System.out.print("Digite o valor da transferência: R$ ");
        double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine().trim());
            if (valor <= 0) {
                System.err.println("Valor deve ser maior que zero!");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido!");
            return;
        }

        try {
            ContaInternaDao contaDao = new ContaInternaDao();
            contaDao.transferirParaContaInterna(idOrigem, idDestino, valor);
            System.out.println("\n✓ Transferência realizada com sucesso!");
            System.out.printf("Valor transferido: R$ %,.2f%n", valor);
            System.out.println("De: Conta ID " + idOrigem);
            System.out.println("Para: Conta ID " + idDestino);

        } catch (SQLException e) {
            System.err.println("\n✗ Erro ao realizar transferência: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void consultarHistoricoTransacoes(Scanner scanner) {
        System.out.println("\n=== Consultar Histórico de Transações ===");

        // Solicita ID da conta
        System.out.print("Digite o ID da conta interna: ");
        int idConta;
        try {
            idConta = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }


        System.out.println("\nData INICIAL do período:");
        LocalDate dataInicio = CondicoesUtils.lerData(scanner);

        System.out.println("\nData FINAL do período:");
        LocalDate dataFim = CondicoesUtils.lerData(scanner);

        if (dataInicio.isAfter(dataFim)) {
            System.err.println("A data inicial não pode ser posterior à data final!");
            return;
        }

        try {

            Instant from = dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant to = dataFim.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();


            HistoricoTransacaoDao dao = new HistoricoTransacaoDao();
            List<HistoricoTransacao> historico = dao.consultarHistoricoPorConta(idConta, from, to, 0, 100);

            // Exibe resultados
            if (historico.isEmpty()) {
                System.out.println("\nNenhuma transação encontrada no período.");
                return;
            }

            System.out.println("\n" + "=".repeat(120));
            System.out.printf("%-10s | %-20s | %-10s | %-15s | %-15s | %-30s%n",
                    "ID", "Data/Hora", "Tipo", "Valor", "Conta Destino", "Nome Contraparte");
            System.out.println("=".repeat(120));

            BigDecimal totalSaques = BigDecimal.ZERO;
            BigDecimal totalDepositos = BigDecimal.ZERO;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

            for (HistoricoTransacao t : historico) {
                String dataHoraStr = formatter.format(t.dataHora());
                String tipoStr = t.tipo().name();
                String valorStr = String.format("R$ %,.2f", t.valor());

                if (t.tipo() == TipoTransacaoFiat.SAQUE) {
                    totalSaques = totalSaques.add(t.valor());
                } else {
                    totalDepositos = totalDepositos.add(t.valor());
                }

                System.out.printf("%-10d | %-20s | %-10s | %15s | %15d | %-30s%n",
                        t.idTransacao(),
                        dataHoraStr,
                        tipoStr,
                        valorStr,
                        t.idContaContraparte(),
                        t.nomeContraparte());
            }

            System.out.println("=".repeat(120));
            System.out.println("\n--- RESUMO DO PERÍODO ---");
            System.out.printf("Total de transações: %d%n", historico.size());
            System.out.printf("Total em SAQUES:     R$ %,.2f%n", totalSaques);
            System.out.printf("Total em DEPÓSITOS:  R$ %,.2f%n", totalDepositos);
            System.out.printf("SALDO DO PERÍODO:    R$ %,.2f%n", totalDepositos.subtract(totalSaques));
            System.out.println();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar histórico: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
