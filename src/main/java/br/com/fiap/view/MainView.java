package br.com.fiap.view;

import br.com.fiap.service.ContaExternaService;
import br.com.fiap.service.ContaInternaService;
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
                    case "1": MenuCliente.exibirMenuCliente(scanner); break;
                    case "2": MenuCarteira.exibirMenuCarteira(scanner); break;
                    case "3": MenuConta.exibirMenuConta(scanner); break;
                    case "3": MenuContaCarteira.exibirMenuContaCarteira(scanner); break;
                    case "3": CriptomoedaService.cadastrarCriptomoeda(scanner); break;
                    case "4": ContaInternaService.enviarTransferenciaContaInternaParaExterna(scanner); break;
                    case "5": ContaExternaService.enviarTransferenciaContaExternaParaInterna(scanner); break;
                    case "6": CriptomoedaService.listarCriptomoedas(); break;
                    case "7": CriptomoedaService.comprarCriptomoeda(scanner); break;
                    case "8": CriptomoedaService.venderCriptomoeda(scanner); break;
                    case "0":
                        System.out.println("Saindo do sistema...");
                        scanner.close();
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
            }

        } while (!opcao.equals("0"));
    }
}
