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
public class ExemploCassandra {
	private static String KEY_SPACE_NAME = "\"faculdadekeyspace\"";
	private static String REPLICAION_STRATEGY = "SimpleStrategy";
	private static Integer REPLICATION_FACTOR = 1;

	public static void main(String[] args) {
		new ExemploCassandra().processar(0, new Emp());
	}

	public Retorno processar(int op, Emp empregado) {
		Retorno retorno = new Retorno();
		System.out.println("Inicio cassandra");

		// https://docs.datastax.com/en/archived/cql/3.3/cql/cql_reference/cqlCreateKeyspace.html
		System.out.println("1 - Criar keyspace");
		System.out.println("2 - Criar tabela usuario");
		System.out.println("3 - Selecionar tabela usuario");
		System.out.println("4 - Inserir dados tabela usuario");
		System.out.println("5 - Atualizar dados tabela usuario");
		System.out.println("6 - Deletar dados tabela usuario");
		Scanner sc = new Scanner(System.in);
		if (op == 0) {
			System.out.println("Digite sua opção: ");
			op = sc.nextInt();
		}
		boolean ret = createKeyspace(KEY_SPACE_NAME, REPLICAION_STRATEGY, REPLICATION_FACTOR);
		try (final Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
				final Session session = cluster.connect(KEY_SPACE_NAME);) {

			switch (op) {
			case 1:
				System.out.println("Dados do KeySpace: " + session.getCluster().getMetadata().getKeyspaces());
				System.out.println("*********Cluster Information *************");
				System.out.println(" Nome do Cluster: " + cluster.getClusterName());
				System.out.println(" Versão do Driver: " + cluster.getDriverVersion());
				System.out.println(" Configuração do Cluster: " + cluster.getConfiguration());
				System.out.println(" Metadados do Cluster: " + cluster.getMetadata());
				System.out.println(" Métricas do Cluster: " + cluster.getMetrics());

				break;
			case 2:
				// criar tabela usuario
				try {
					boolean retTabela = createTabela(CQLQueryConstants.CREATE_TABELA_USUARIO, KEY_SPACE_NAME);
					if (retTabela) {
						System.out.println("Tabela criada com sucesso:" + CQLQueryConstants.CREATE_TABELA_USUARIO);
					}
				} catch (AlreadyExistsException e) {
					System.out.println("Tabela " + CQLQueryConstants.TAB_USUARIO + " já existe no banco de dados.");
					retorno.setStatus(1);
				}
				break;
			case 3:
				// Recuperar dados da tabela usuario
				System.out.println("\n*********Recuperar dados da tabela usuario *************");
				retorno.setListaRegistros(selecionarTodosRegistros(session));
				break;
			case 4:
				retorno.setStatus(gravarRegistroCassandra(empregado, session));
				break;
			case 5:
				// Atualizar dados
				System.out.println("\n*********Atualizar dados *************");
				session.execute("update \"" + CQLQueryConstants.TAB_USUARIO
						+ "\" set endereco = 'Av. Padre Germano Mayer' where id = 27");
				selecionarTodosRegistros(session);
				break;
			case 6:
				session.execute("delete FROM \"" + CQLQueryConstants.TAB_USUARIO + "\" where id = 27");
				selecionarTodosRegistros(session);
				break;

			default:
				break;
			}

			cluster.close();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			retorno.setStatus(1);
		}
		sc.close();
		return retorno;
	}

	private int gravarRegistroCassandra(Emp empregado, Session session) {
		// Insert new User into users table
		System.out.println("\n*********Inserir dados *************");
		System.out.println("Prerando para gravar.");
		System.out.println("ID: " + empregado.getId());
		System.out.println("Nome: " + empregado.getName());
		System.out.println("Endereço: " + empregado.getCountry());
		System.out.println("Senha: " + empregado.getPassword());

		session.execute("INSERT INTO \"" + CQLQueryConstants.TAB_USUARIO + "\" (id, endereco, nome) VALUES ("
				+ empregado.getId() + ", '" + empregado.getCountry() + "', '" + empregado.getName() + "')");
		System.out.println("Listar todos os registros.");
		selecionarTodosRegistros(session);
		return 0;
	}

	private List<Emp> selecionarTodosRegistros(final Session inSession) {
		// Use select to get the users table data
		ResultSet results = inSession.execute("SELECT * FROM \"" + CQLQueryConstants.TAB_USUARIO + "\"");
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

	private Boolean createKeyspace(String keyspaceName, String replicationStrategy, int replicationFactor) {
		StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append(keyspaceName)
				.append(" WITH replication = {").append("'class':'").append(replicationStrategy)
				.append("','replication_factor':").append(replicationFactor).append("};");
		String query = sb.toString();
		CASConnectionFactory.getInstance().getSession().execute(query);
		CASConnectionFactory.getInstance().getSession().close();
		return Boolean.TRUE;
	}

	private Boolean createTabela(String query, String keyspace) {
		Session session = CASConnectionFactory.getInstance().getSession(keyspace);
		session.execute(query);
		session.close();
		return Boolean.TRUE;
	}

	private Boolean inserirTabelaUsuario(String keyspace, int id, String nome, String endereco) {
		Session session = CASConnectionFactory.getInstance().getSession(keyspace);
		PreparedStatement prepared = session.prepare(CQLQueryConstants.INSERT_USUARIO);
		BoundStatement boundStmt = prepared.bind(id, nome, endereco);
		session.execute(boundStmt);
		session.close();
		return Boolean.TRUE;
	}
}
