package com.projeto.sprint.projetosprint.service.material;

import com.projeto.sprint.projetosprint.controller.materialPreco.dto.MaterialPrecoCadastroDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;
import com.projeto.sprint.projetosprint.domain.repository.MaterialPrecoRepository;
import com.projeto.sprint.projetosprint.exception.ImportacaoExportacaoException;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.util.PilhaMaterialPreco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialPrecoService {
    private final MaterialPrecoRepository repository;
    private final CooperativaService cooperativaService;

    public List<MaterialPreco> listarMaterialPreco(){
        return this.repository.findAll();
    }

    public MaterialPreco cadastrarMaterialPreco(MaterialPrecoCadastroDTO materialPrecoDTO, int id) {

        MaterialPreco materialPreco = new MaterialPreco();

        if (id != 0) materialPreco.setId(id);

        Cooperativa cooperativa = this.cooperativaService.buscarCoperativaId(materialPrecoDTO.getFkCooperativa());

        materialPreco.setVlrMaterial(materialPrecoDTO.getVlrMaterial());
        materialPreco.setNome(materialPrecoDTO.getNome());
        materialPreco.setCooperativa(cooperativa);

        return this.repository.save(materialPreco);
    }

    public MaterialPreco buscarMaterialPrecoPorId(int id){
        return this.repository.findById(id).get();
    }

    public List<MaterialPreco> buscarMaterialPrecoPorIdCooperativa(int id) {
        List<MaterialPreco> listMaterialPreco = this.repository.findByCooperativaId(id);

        return PilhaMaterialPreco.listMenorPreco(listMaterialPreco);
    }

    public List<MaterialPreco> buscarMaterialCooperativaValor(int id) {
        List<MaterialPreco> listMaterialPreco = this.repository.findByCooperativaIdOrderByVlrMaterial(id);
        return listMaterialPreco;
    }

    public List<MaterialPreco> cadastrarMaterialPrecoUpload(MultipartFile file, int id){
        List<MaterialPreco> materiais = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            BufferedReader entrada = new BufferedReader(reader);

            String registro = entrada.readLine();

            while(registro != null){
                MaterialPreco novoMaterial = new MaterialPreco();
                Cooperativa cooperativa = cooperativaService.buscarCoperativaId(id);
                if (registro.equals("02")){
                    novoMaterial.setCooperativa(cooperativa);
                    int i = registro.indexOf(";");
                    novoMaterial.setNome(registro.substring(2, i));
                    novoMaterial.setVlrMaterial(Double.valueOf(registro.substring(i,registro.length())));
                    materiais.add(repository.save(novoMaterial));
                }
                registro = entrada.readLine();
            }
            entrada.close();
            return materiais;
        } catch (IOException e) {
            throw new ImportacaoExportacaoException(e.getMessage());
        }

    }

}
