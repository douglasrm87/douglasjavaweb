package crud;

import java.util.ArrayList;
import java.util.List;

public class Retorno {
	int status = 0;
	List<Emp> listaRegistros = new ArrayList<>();
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Emp> getListaRegistros() {
		return this.listaRegistros;
	}
	public void setListaRegistros(List<Emp> listaRegistros) {
		this.listaRegistros = listaRegistros;
	}

	
}
