package br.com.fiap.view;

import java.util.Scanner;

public class MenuConta {
    public static final String OPCOES_CONTA = """
    ================= MENU CONTA =================
    
      1 - Gerenciar Conta Interna
      2 - Gerenciar Conta Externa
      0 - Voltar para o menu principal

    Escolha uma opção: """;


    public static void exibirMenuConta(Scanner scanner) {
        String opcao;
        do {
            System.out.println(OPCOES_CONTA);
            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> MenuContaInterna.exibirMenuContaInterna(scanner);
                case "2" -> MenuContaExterna.exibirMenuContaExterna(scanner);
                case "0" -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (!opcao.equals("0"));

    }
}
