//package com.projeto.sprint.projetosprint.util.chaveValor;
//
//import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialPorColetaDTO;
//import com.projeto.sprint.projetosprint.util.data.AnoMes;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class ChaveValorMapper {
//    public static List<ChaveValor> of(Map<String, Double> map) {
//        List<ChaveValor> list = new ArrayList<>();
//        for (Map.Entry<String, Double> m : map.entrySet()) {
//            list.add(new ChaveValor(m.getKey(), m.getValue()));
//        }
//        return list;
//    }
//
//    public static List<MaterialPorColetaDTO> mapperMaterialPorColeta(Map<String, List<ChaveValor>> map){
//        List<MaterialPorColetaDTO> list = new ArrayList<>();
//        for (Map.Entry<String, List<ChaveValor>> m : map.entrySet()) {
//            String anoMes = m.getKey();
//            List<ChaveValor> chaveValor = m.getValue();
//
//            list.add(new MaterialPorColetaDTO(anoMes, chaveValor));
//        }
//        return list;
//    }
//
//}
