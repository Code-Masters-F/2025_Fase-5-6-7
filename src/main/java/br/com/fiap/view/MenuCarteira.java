package br.com.fiap.view;

import br.com.fiap.service.CarteiraService;

import java.util.Scanner;

public class MenuCarteira {
    private static final String OPCOES_CARTEIRA = """
    ============== OPÇÕES DE CARTEIRA ==============

      1 - Consultar Carteira
      0 - Voltar para o menu principal

    Escolha uma opção: """;


    public static void exibirMenuCarteira(Scanner scanner) {
        String opcao;
        do {
            System.out.println(OPCOES_CARTEIRA);
            opcao = scanner.nextLine().trim();
            System.out.println();

            switch (opcao) {
                case "1" -> CarteiraService.consultarCarteira(scanner);
                case "0" -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }




}
