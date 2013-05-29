package dao;

import java.util.List;

import model.Registry;

public interface PetDAO {
	public Boolean inserir(Registry registry) throws Exception;
	public List<Registry> listarTodos() throws Exception;
}
