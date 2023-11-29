package com.projeto.sprint.projetosprint.util.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;

@Getter
@AllArgsConstructor
public class AnoMes {
    private int ano;
    private Month mes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnoMes anoMes = (AnoMes) o;
        return ano == anoMes.ano && mes == anoMes.mes;
    }

    @Override
    public int hashCode() {
        return 31 * ano + (mes != null ? mes.hashCode() : 0);
    }
}
