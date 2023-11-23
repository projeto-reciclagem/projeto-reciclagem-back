package com.projeto.sprint.projetosprint.util;

import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;

import java.util.List;

public class OrdenacaoCnpj {
    public static List<Cooperativa> ordenarPorCnpj(List<Cooperativa> lista)
    {
        Cooperativa aux;
        for (int i = 0; i < lista.size(); i++)
        {
            for (int j = 0; j < lista.size() - 1; j++)
            {
                if (lista.get(j).getCnpj().compareTo(lista.get(j + 1).getCnpj()) > 0)
                {
                    aux = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, aux);
                }
            }
        }
        return lista;
    }
}
