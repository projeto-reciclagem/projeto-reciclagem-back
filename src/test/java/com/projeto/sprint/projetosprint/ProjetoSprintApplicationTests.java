package com.projeto.sprint.projetosprint;

import com.projeto.sprint.projetosprint.controller.cooperativa.CooperativaMapper;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.usuario.TipoUsuario;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.service.endereco.EnderecoService;
import com.projeto.sprint.projetosprint.service.usuario.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


//import static com.microsoft.sqlserver.jdbc.EncryptOption.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProjetoSprintApplicationTests {

	@InjectMocks
	private CooperativaService service;

	@Mock
	private UsuarioService usuarioService;

	@Mock
	private CooperativaRepository repository;

	@Mock
	private EnderecoService enderecoService;

	@Test
	public void testListarCooperativa() {
		Cooperativa cooperativa1 = new Cooperativa();
		cooperativa1.setId(1);
		cooperativa1.setNome("Cooperativa 1");

		Cooperativa cooperativa2 = new Cooperativa();
		cooperativa2.setId(2);
		cooperativa2.setNome("Cooperativa 2");

		List<Cooperativa> cooperativas = List.of(cooperativa1, cooperativa2);

		when(repository.findAll()).thenReturn(cooperativas);

		List<CooperativaResponseDTO> result = service.listarCooperativa();

		assertEquals(2, result.size());
		assertEquals("Cooperativa 1", result.get(0).getNome());
		assertEquals("Cooperativa 2", result.get(1).getNome());
	}

	@Test
	public void testCadastrarCooperativa() {
		CooperativaCriacaoDTO dados = new CooperativaCriacaoDTO();
		dados.setNome("Sol Nascente");
		dados.setCnpj("39104257000129");
		dados.setEmail("solnascente@gmail.com");
		dados.setSenha("12345678910");

		UsuarioCriacaoDTO usuarioDados = new UsuarioCriacaoDTO(dados.email, dados.senha);
		Usuario usuario = new Usuario();
		usuario.setId(42L);
		usuario.setTipoUsuario(TipoUsuario.COOPERATIVA);

		Cooperativa cooperativa = CooperativaMapper.of(dados);

		when(usuarioService.cadastrar(usuarioDados)).thenReturn(usuario);
		when(repository.save(cooperativa)).thenReturn(cooperativa);

		Cooperativa result = service.cadastrarCooperativa(dados);

		assertNotNull(result);
	}
}
