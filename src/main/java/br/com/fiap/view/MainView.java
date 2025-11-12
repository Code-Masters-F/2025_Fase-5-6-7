package br.com.fiap.view;

import br.com.fiap.service.CriptomoedaService;

import java.util.*;

public class MainView {
    public static void main(String[] args) {
        String opcao;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print(MenuPrincipal.MENU_PRINCIPAL);
            opcao = scanner.nextLine().trim();
            System.out.println();
            try {
                switch (opcao) {
                    case "1" -> MenuCliente.exibirMenuCliente(scanner);
                    case "2" -> MenuCarteira.exibirMenuCarteira(scanner);
                    case "3" -> MenuConta.exibirMenuConta(scanner);
                    case "4" -> MenuCriptomoeda.exibirMenuCriptomoeda(scanner);
                    case "0" ->  {
                        System.out.println("Saindo do sistema...");
                        scanner.close();
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
            }

        } while (!opcao.equals("0"));
    }
}
