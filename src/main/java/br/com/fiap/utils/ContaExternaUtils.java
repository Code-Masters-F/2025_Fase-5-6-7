package br.com.fiap.utils;

import br.com.fiap.model.ContaExterna;

import java.util.List;

public class ContaExternaUtils {

    static public void listarBancosExternosDisponiveis() {
        final String BANCOS_EXTERNOS = """
                001 - Banco do Brasil S.A.
                033 - Banco Santander (Brasil) S.A.
                104 - Caixa Econômica Federal
                237 - Banco Bradesco S.A.
                341 - Itaú Unibanco S.A.
                399 - HSBC Bank Brasil S.A. (OBS: incorporado ao Bradesco em 2016)
                745 - Banco Citibank S.A.
                422 - Banco Safra S.A.
                756 - Bancoob (Banco Cooperativo do Brasil S.A.)
                655 - Banco Votorantim S.A.
                """;

        System.out.println(BANCOS_EXTERNOS);
    }



}
