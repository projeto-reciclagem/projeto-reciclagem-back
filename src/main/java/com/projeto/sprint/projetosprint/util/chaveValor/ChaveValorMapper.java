package com.projeto.sprint.projetosprint.util.chaveValor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChaveValorMapper {
    public static List<ChaveValor> of(Map<String, Double> map) {
        List<ChaveValor> list = new ArrayList<>();
        for (Map.Entry<String, Double> m : map.entrySet()) {
            list.add(new ChaveValor(m.getKey(), m.getValue()));
        }
        return list;
    }
}
