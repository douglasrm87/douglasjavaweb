package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cassandra.ExemploCassandra;
import cassandra.FuncoesCassandra;

public class EmpDao {

	private static final String NOSQL = "NOSQL";
	private static FuncoesCassandra cassandra = new FuncoesCassandra();

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	public static int gravarCassandra(Emp e, String tpBanco) {
		int status = 0;
		if (NOSQL.equals(tpBanco)) {
			// Adicionar neste ponto a conex�o e grava��o dos dados do cassandra

			cassandra.gravarRegistroCassandra(e);

		} else {
			// Conex�o para banco de dados Oracle - Relacional
			try (Connection con = EmpDao.getConnection();
					PreparedStatement ps = con
							.prepareStatement("insert into user905(name,password,email,country) values (?,?,?,?)");) {

				ps.setString(1, e.getName());
				ps.setString(2, e.getPassword());
				ps.setString(3, e.getEmail());
				ps.setString(4, e.getCountry());

				status = ps.executeUpdate();

				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return status;
	}

	public static int update(Emp e) {
		int status = 0;
		try (Connection con = EmpDao.getConnection();
				PreparedStatement ps = con
						.prepareStatement("update user905 set name=?,password=?,email=?,country=? where id=?");) {

			ps.setString(1, e.getName());
			ps.setString(2, e.getPassword());
			ps.setString(3, e.getEmail());
			ps.setString(4, e.getCountry());
			ps.setInt(5, e.getId());

			status = ps.executeUpdate();

			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return status;
	}

	public static int delete(int id) {
		int status = 0;
		try (Connection con = EmpDao.getConnection();
				PreparedStatement ps = con.prepareStatement("delete from user905 where id=?");) {

			ps.setInt(1, id);
			status = ps.executeUpdate();

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public static Emp getEmployeeById(int id) {
		Emp e = new Emp();

		try (Connection con = EmpDao.getConnection();
				PreparedStatement ps = con.prepareStatement("select * from user905 where id=?");
				ResultSet rs = ps.executeQuery();) {

			ps.setInt(1, id);

			if (rs.next()) {
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setPassword(rs.getString(3));
				e.setEmail(rs.getString(4));
				e.setCountry(rs.getString(5));
			}
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return e;
	}

	public static Retorno listarTodosEmpregados(String tpBanco) {
		Retorno retorno = new Retorno();
		if (NOSQL.equals(tpBanco)) {
			// Adicionar neste ponto a conex�o e grava��o dos dados do cassandra

			retorno.setListaRegistros(cassandra.selecionarTodosRegistros());

		} else {
			List<Emp> list = new ArrayList<Emp>();

			int cassandra = 0;
			if (cassandra == 0) {
				Emp e = new Emp();
				e.setId(1);
				e.setName("Douglas Mendes");
				e.setPassword("secreto");
				e.setEmail("douglas.mendes@estacio.br");
				e.setCountry("Brasil");
				list.add(e);
			} else {

				try (Connection con = EmpDao.getConnection();
						PreparedStatement ps = con.prepareStatement("select * from user905");
						ResultSet rs = ps.executeQuery();) {

					while (rs.next()) {
						Emp e = new Emp();
						e.setId(rs.getInt(1));
						e.setName(rs.getString(2));
						e.setPassword(rs.getString(3));
						e.setEmail(rs.getString(4));
						e.setCountry(rs.getString(5));
						list.add(e);
					}
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return retorno;
	}
}