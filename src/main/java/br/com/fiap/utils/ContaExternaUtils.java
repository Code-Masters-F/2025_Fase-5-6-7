package br.com.fiap.utils;
import java.util.Map;

public class ContaExternaUtils {

    private static final Map<Integer, String> BANCOS_EXTERNOS = Map.ofEntries(
            Map.entry(1, "Banco do Brasil S.A."),
            Map.entry(33, "Banco Santander (Brasil) S.A."),
            Map.entry(104, "Caixa Econômica Federal"),
            Map.entry(237, "Banco Bradesco S.A."),
            Map.entry(341, "Itaú Unibanco S.A."),
            Map.entry(399, "HSBC Bank Brasil S.A."),
            Map.entry(745, "Banco Citibank S.A."),
            Map.entry(422, "Banco Safra S.A."),
            Map.entry(756, "Bancoob"),
            Map.entry(655, "Banco Votorantim S.A.")
    );

    public static  void listarBancosExternosDisponiveis() {
        System.out.println("\n--- BANCOS DISPONÍVEIS ---");
        BANCOS_EXTERNOS.forEach((codigo, nome) ->
                System.out.printf("%03d - %s%n", codigo, nome)
        );
    }

    public static String obterNomeBancoPorCodigo(int codigo) {
        return BANCOS_EXTERNOS.get(codigo);
    }



}
