package br.com.fiap.view;

import br.com.fiap.service.CarteiraService;
import br.com.fiap.service.ContaCarteiraService;
import br.com.fiap.service.ContaExternaService;
import br.com.fiap.service.ContaInternaService;

import java.util.Scanner;

public class MenuContaCarteira {

    private static final String SUB_MENU_CONTA_CARTEIRA = """
    ========== GERENCIAR CONTAS E CARTEIRAS ==========

      1 - Consultar conta interna
      2 - Consultar carteira de criptomoedas
      3 - Transferir entre contas internas
      4 - Adicionar saldo à conta
      5 - Consultar histórico de transações
      6 - Cadastrar conta externa
      0 - Voltar ao menu principal

    Escolha uma opção: """;


    /*
      Sub-menu para gerenciar contas e carteiras
    */
    public static void exibirMenuContaCarteira(Scanner scanner) {
        String opcao;
        do {
            System.out.print(SUB_MENU_CONTA_CARTEIRA);
            opcao = scanner.nextLine().trim();
            System.out.println();
            try {
                switch (opcao) {
                    case "1": ContaInternaService.consultarContaInterna(scanner); break;
                    case "2": CarteiraService.consultarCarteira(scanner); break;
                    case "3": ContaInternaService.transferirEntreContasInternas(scanner); break;
                    case "4": ContaCarteiraService.adicionarSaldoConta(scanner); break;
                    case "5": ContaInternaService.consultarHistoricoTransacoes(scanner); break;
                    case "6": ContaExternaService.cadastrarContaExterna(scanner); break;
                    case "0":
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
                e.printStackTrace();
            }
        } while (!opcao.equals("0"));
    }
}
