package cassandra;

public class CQLQueryConstants {
	public static final String CREATE_ORDER_TABLE = "CREATE TABLE \"ORDERS\" \n" + "(\n" + "   order_number varchar,\n"
			+ "   order_date varchar,\n" + "   item_id varchar,\n" + "   item_price double,\n"
			+ "   delivery_date varchar,\n" + "   PRIMARY KEY (order_number)\n" + ");";
	public static String TAB_USUARIO = "USUARIO";
	public static final String CREATE_TABELA_USUARIO = "CREATE TABLE \"" + TAB_USUARIO + "\" \n" + "(\n"
			+ "   nome varchar,\n" + "   endereco varchar,\n" + "   id int, \n" + "   PRIMARY KEY (id)\n" + ");";

	public static final String SCHEDULE_DELIVERY_TABLE = "CREATE TABLE ORDER_DELIVERY \n" + "(\n"
			+ "   shipment_number varchar,\n" + "   order_number varchar,\n" + "   expected_delivery_date int,\n"
			+ "   customer_id varchar,\n" + "   delivery_mode varchar,\n"
			+ "   PRIMARY KEY (shipment_number,order_number)\n" + ");";

	public static final String CREATE_NEW_ORDER = "INSERT into \"ORDERS\" (" + "order_number, " + "order_date,"
			+ "item_id," + "item_price," + "delivery_date) " + "VALUES (?,?,?,?,?)";

	public static final String INSERT_USUARIO = "INSERT into \"" + TAB_USUARIO + "\" (" + "id, " + "nome,"
			+ "endereco) " + "VALUES (?,?,?)";

	public static final String UPDATE_ORDER_DELIVERY_DATE = "UPDATE \"ORDERS\"  SET " + "delivery_date = ?"
			+ "WHERE order_number = ?";

	public static final String DELETE_ORDER = "DELETE FROM \"ORDERS\" WHERE order_number=? IF EXISTS;";

	public static final String SELECT_ORDER = "SELECT * FROM \"ORDERS\" WHERE order_number= ?;";

	public static final String SELECT_ORDER_ALL = "SELECT * FROM \"ORDERS\";";
}
