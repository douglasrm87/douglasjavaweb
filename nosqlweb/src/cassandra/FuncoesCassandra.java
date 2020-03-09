package cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;

import crud.Emp;
import crud.Retorno;

//https://www.ibm.com/developerworks/br/library/ba-set-up-apache-cassandra-architecture/index.html
/*
Teoria KeySpace
https://docs.datastax.com/en/archived/cql/3.3/cql/cql_reference/cqlCreateKeyspace.html
https://www.tutorialspoint.com/cassandra/cassandra_create_keyspace.htm
 */
/*
TOP CRUD em Java
http://www.devinline.com/2018/04/apache-cassandra-crud-operation-with-java-client.html
 */
//http://www.devjavasource.com/cassandra/cassandra-crud-operation-using-java/
public class FuncoesCassandra {
	private static String KEY_SPACE_NAME = "\"faculdadekeyspace\"";
	private static String REPLICAION_STRATEGY = "SimpleStrategy";
	private static Integer REPLICATION_FACTOR = 1;

	Cluster cluster = null;
	Session session = null;

	public FuncoesCassandra() {
		iniciar();
	}

	private void iniciar() {
		Retorno retorno = new Retorno();
		System.out.println("Inicio cassandra");
		boolean ret = createKeyspace(FuncoesCassandra.KEY_SPACE_NAME, FuncoesCassandra.REPLICAION_STRATEGY,
				FuncoesCassandra.REPLICATION_FACTOR);
		try {
			this.cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
			this.session = this.cluster.connect(KEY_SPACE_NAME);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void encerrarCassandra() {
		this.cluster.close();
		this.session.close();
	}

	public int gravarRegistroCassandra(Emp empregado) {
		// Insert new User into users table
		System.out.println("\n*********Inserir dados *************");
		System.out.println("Prerando para gravar.");
		System.out.println("ID: " + empregado.getId());
		System.out.println("Nome: " + empregado.getName());
		System.out.println("Endereço: " + empregado.getCountry());
		System.out.println("Senha: " + empregado.getPassword());

		this.session.execute("INSERT INTO \"" + CQLQueryConstants.TAB_USUARIO + "\" (id, endereco, nome) VALUES ("
				+ empregado.getId() + ", '" + empregado.getCountry() + "', '" + empregado.getName() + "')");
		System.out.println("Listar todos os registros.");
		selecionarTodosRegistros();
		return 0;
	}

	public List<Emp> selecionarTodosRegistros() {
		// Use select to get the users table data
		ResultSet results = this.session.execute("SELECT * FROM \"" + CQLQueryConstants.TAB_USUARIO + "\"");
		List<Emp> list = new ArrayList<>();
		for (Row row : results) {
			Emp e = new Emp();
			e.setCountry(row.getString("endereco"));
			e.setName(row.getString("nome"));
			e.setId(row.getInt("id"));
			e.setEmail("teste@teste.com.br");
			e.setPassword("123456");
			list.add(e);
			System.out.format("%s %d %s\n", row.getString("nome"), row.getInt("id"), row.getString("endereco"));
		}
		return list;
	}

	public Boolean createKeyspace(String keyspaceName, String replicationStrategy, int replicationFactor) {
		StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append(keyspaceName)
				.append(" WITH replication = {").append("'class':'").append(replicationStrategy)
				.append("','replication_factor':").append(replicationFactor).append("};");
		String query = sb.toString();
		CASConnectionFactory.getInstance().getSession().execute(query);
		CASConnectionFactory.getInstance().getSession().close();
		return Boolean.TRUE;
	}

	public Boolean createTabela() {

		Session sessionL = CASConnectionFactory.getInstance().getSession(KEY_SPACE_NAME);
		sessionL.execute(CQLQueryConstants.CREATE_TABELA_USUARIO);
		sessionL.close();
		return Boolean.TRUE;
	}

	public Boolean inserirTabelaUsuario(int id, String nome, String endereco) {
		Session sessionL = CASConnectionFactory.getInstance().getSession(KEY_SPACE_NAME);
		PreparedStatement prepared = session.prepare(CQLQueryConstants.INSERT_USUARIO);
		BoundStatement boundStmt = prepared.bind(id, nome, endereco);
		sessionL.execute(boundStmt);
		sessionL.close();
		return Boolean.TRUE;
	}
}
