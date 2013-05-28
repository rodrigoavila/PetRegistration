package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Registry;

public class PetDAOImpl implements PetDAO {

	// public final String PATH = "database.txt";
	private String path;
	
	public PetDAOImpl(String path) {
		this.path = path;
	}
	
	
	@Override
	/**
	 * Converte objeto em String (CSV)
	 * Posições:
	 * 	1. tipoPet
	 * 	2. nomeDono 
	 * 	3. nomePet
	 * 	4. raca
	 * 	5. endereco
	 * 	6. telefone
	 * 	7. valor
	 * 	8. dataRegistro [aaaa-mm-dd]
	 */
	public Boolean inserir(Registry registry) throws IOException {
		String linha = registry.getTipoPet() + "," + 
					   registry.getNomeDono() + "," + 
					   registry.getNomePet() + "," +
					   registry.getRaca() + "," +
					   registry.getEndereco() + "," +
					   registry.getTelefone() + "," +
					   registry.getValorServico() + "," +
					   registry.getDataRegistro();
		
		File file = new File(path);
		if ( !file.exists() ) {
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file.getAbsolutePath());
		writer.write(linha);
		writer.close();
			
		return true;
	}

	@Override
	/**
	 * Instancia o bando de dados (File)
	 * Le linha por linha deste arquivo
	 * Criar uma lista vazia do tipo List<Registry>
	 * Converte as linhas em objetos Registry
	 * Adiciona objeto por objeto nesta lista
	 * Retorna a lista
	 * 
	 */
	public boolean listarTodos() throws IOException {
		// TODO
		
		FileReader leArquivo = new FileReader(path);		 
		
		BufferedReader bufArquivo = new BufferedReader(leArquivo);
		
		List<Registry> registros = new ArrayList<Registry>();
				 
		while (bufArquivo.ready()) {
		
		String linha = bufArquivo.readLine();
		 
		}
		 
		bufArquivo.close();
		leArquivo.close();
		 
		return true;
	}
		
}
