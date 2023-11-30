package com.projeto.sprint.projetosprint.util;

import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilaMaterialReciclado {
    public static List<ChaveValor> converterList(Map<String, Double> mapMateriaisReciclado) {
        List<ChaveValor> listOrdenacao = new ArrayList<>();
        FilaObj<ChaveValor> filaOrdenacao = new FilaObj<>(7);

        for (Map.Entry<String, Double> map : mapMateriaisReciclado.entrySet()){
            filaOrdenacao.insert(new ChaveValor(map.getKey(), map.getValue()));
        }

        while (!filaOrdenacao.isEmpty()){
            listOrdenacao.add(filaOrdenacao.poll());
        }
        return listOrdenacao;
    }
}
