package dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Registry;

public class PetDAOTest {	
	
	private static final String DATABASE_PATH = "database-test.txt";
	PetDAO dao; 
	
	@Before
	public void setUp() {
		resetarBancoDeTest(DATABASE_PATH);
		dao = new PetDAOImpl(DATABASE_PATH);
	}

	private void resetarBancoDeTest(String databasePath) {
		File file = new File(databasePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Test
	public void inserir() {		 
		Registry registry = preencherRegistro();
		
		try {
			Boolean resultado = dao.inserir(registry);
			assertTrue("Registro não inserido", resultado);		
		} catch (Exception e) {
			fail("Erro ao inserir registro: " + e.getMessage());
		}
	}

	@Test
	public void listarTodos() {
		try {
			Registry registry = preencherRegistro();
			dao.inserir(registry);
						
			List<Registry> registros = dao.listarTodos();
			
			assertNotNull("Lista de registros nula", registros);			
			assertTrue("Lista de registros vazia", registros.size() > 0);
			assertEquals(1, registros.size());
		} catch (Exception e) {
			fail("Erro ao listar todos registros: " + e.getMessage());
		}
	}

	private Registry preencherRegistro() {
		Registry registry = new Registry();
		registry.setTipoPet("cachorro");
		registry.setNomeDono("Fulano de Tal");
		registry.setNomePet("Toto");
		registry.setRaca("Tchumba");
		registry.setEndereco("227 Shippen St. #2, Weehawken, NJ");
		registry.setTelefone("+1-201-580-1213");
		
		BigDecimal valor = new BigDecimal("100.50");
		registry.setValorServico(valor);
		
		Date dataRegistro = new Date(System.currentTimeMillis());
		registry.setDataRegistro(dataRegistro);
		return registry;
	}
	
}
