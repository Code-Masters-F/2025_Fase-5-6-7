package br.com.fiap.view;

import java.util.Scanner;

public class MenuConta {
    public static final String MENU_CONTA = """
    ================= MENU CONTA =================
    
      1 - Gerenciar Conta Interna
      2 - Gerenciar Conta Externa
      0 - Voltar para o menu principal

    Escolha uma opção: """;

    public static void exibirMenuConta(Scanner scanner) {
        String opcao;
        do {
            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    MenuContaInterna.exibirMenuContaInterna(scanner);
                    break;
                case "2":
                    MenuContaExterna.exibirMenuContaExterna(scanner);
                    break;
                case "0":
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (!opcao.equals("0"));

    }
}
