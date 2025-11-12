package br.com.fiap.view;

import br.com.fiap.service.CriptomoedaService;

import java.util.Scanner;

public class MenuCriptomoeda {
    public static final String OPCOES_MENU_CRIPTOMOEDA = """
    ============== GERENCIAR CRIPTOMOEDAS ==============

      1 - Incluir nova criptomoeda
      2 - Alterar criptomoeda
      3 - Excluir criptomoeda
      4 - Exibir todas as criptomoedas
      5 - Exibir uma criptomoeda (por ID)
      0 - Voltar ao menu principal

    Escolha uma opção: """;

    public static void exibirMenuCriptomoeda(Scanner scanner) {
        String opcaoCripto = "";
        do {
            try {
                System.out.print(OPCOES_MENU_CRIPTOMOEDA);
                opcaoCripto = scanner.nextLine().trim();

                switch (opcaoCripto) {
                    case "1" -> CriptomoedaService.cadastrarCriptomoeda(scanner);
                    case "2" -> CriptomoedaService.alterarCriptomoeda(scanner);
                    case "3" -> CriptomoedaService.excluirCriptomoeda(scanner);
                    case "4" -> CriptomoedaService.listarCriptomoedas();
                    case "5" -> CriptomoedaService.exibirCriptomoedaPorId(scanner);
                    case "0" -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
                e.printStackTrace();
            }
        } while (!opcaoCripto.equals("0"));
    }
}
