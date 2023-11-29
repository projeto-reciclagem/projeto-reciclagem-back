package com.projeto.sprint.projetosprint.util;

import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;

import java.util.ArrayList;
import java.util.List;

public class PilhaMaterialPreco {
    public static List<MaterialPreco> listMenorPreco(List<MaterialPreco> listMaterialPreco){
        int tamanhoPilha = listMaterialPreco.size();
        PilhaObj<MaterialPreco> pilhaMaterialPreco = new PilhaObj<>(tamanhoPilha);

        for (MaterialPreco material : listMaterialPreco){
            pilhaMaterialPreco.push(material);
        }
        listMaterialPreco = new ArrayList<>();
        for (int i = 0; i < tamanhoPilha; i++){
            listMaterialPreco.add(pilhaMaterialPreco.pop());
        }
        return listMaterialPreco;
    }
}
