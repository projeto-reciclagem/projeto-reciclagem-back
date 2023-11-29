package com.projeto.sprint.projetosprint.util;

import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilaMaterialReciclado {
    public static List<ChaveValor> listMaterialMaisRecicladoPorDia(Map<String, Double> mapMateriaisReciclado) {

        String[] dias = new String[]{"DOMINGO", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SABÁDO"};

        List<ChaveValor> listOrdenacao = new ArrayList<>();

        FilaObj<String> filaDiasOrdenacao = new FilaObj<>(7);
        for (int i = 0; i < 7; i++){
            filaDiasOrdenacao.insert(dias[i]);
        }

        for (Map.Entry<String, Double> map : mapMateriaisReciclado.entrySet()){
            listOrdenacao.add(new ChaveValor(filaDiasOrdenacao.poll(),map.getValue()));
        }

        return listOrdenacao;
    }
}
