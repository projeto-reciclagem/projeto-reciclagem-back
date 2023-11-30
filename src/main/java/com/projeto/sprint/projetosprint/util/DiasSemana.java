package com.projeto.sprint.projetosprint.util;

import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiasSemana {

    public static List<ChaveValor> converterDias(Map<String, Double> map) {
        Map<String, String> diasTraduzidos = new HashMap<>();
        diasTraduzidos.put("SUNDAY", "DOMINGO");
        diasTraduzidos.put("MONDAY", "SEGUNDA");
        diasTraduzidos.put("TUESDAY", "TERÇA");
        diasTraduzidos.put("WEDNESDAY", "QUARTA");
        diasTraduzidos.put("THURSDAY", "QUINTA");
        diasTraduzidos.put("FRIDAY", "SEXTA");
        diasTraduzidos.put("SATURDAY", "SÁBADO");

        List<ChaveValor> listaChaveValor = new ArrayList<>();

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String diaEmIngles = entry.getKey();
            double valor = entry.getValue();

            String diaEmPortugues = diasTraduzidos.getOrDefault(diaEmIngles, diaEmIngles);

            listaChaveValor.add(new ChaveValor(diaEmPortugues, valor));
        }
        listaChaveValor.sort((cv1, cv2) -> {
            List<String> ordemDiasSemana = List.of(
                    "DOMINGO", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SÁBADO"
            );

            return ordemDiasSemana.indexOf(cv1.getChave()) - ordemDiasSemana.indexOf(cv2.getChave());
        });
        return listaChaveValor;
    }
}
