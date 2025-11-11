package br.com.fiap.view;

import br.com.fiap.service.ContaExternaService;

import java.util.Scanner;

public class MenuContaExterna {
    public static final String OPCOES_CONTA_EXTERNA = """
    ================= MENU CONTA EXTERNA =================
    
      1 - Cadastrar Conta Externa
      0 - Voltar para o menu principal

    Escolha uma opção: """;


    public static void exibirMenuContaExterna(Scanner scanner) {
        String opcao;
        do {
            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    ContaExternaService.cadastrarContaExterna(scanner);
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
