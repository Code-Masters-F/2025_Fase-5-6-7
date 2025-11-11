package br.com.fiap.view;

import br.com.fiap.service.ContaInternaService;

import java.util.Scanner;

public class MenuContaInterna {
    public static final String OPCOES_CONTA_INTERNA = """
    ================= MENU CONTA INTERNA =================
    
      1 - Consultar Conta Interna
      2 - Realizar transferência de saldo entre Contas Internas
      3 - Consultar histórico de transações de Contas Internas
      0 - Voltar para o menu principal

    Escolha uma opção: """;


    public static void exibirMenuContaInterna(Scanner scanner) {
        String opcao;
        do {
            System.out.println(OPCOES_CONTA_INTERNA);
            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> ContaInternaService.consultarContaInterna(scanner);
                case "2" -> ContaInternaService.transferirEntreContasInternas(scanner);
                case "3" -> ContaInternaService.consultarHistoricoTransacoes(scanner);
                case "0" -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }

        } while (!opcao.equals("0"));
    }



}
