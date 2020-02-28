package cassandra;

public class CASConnectionFactory {
	private static CASConnection INSTANCE;
	 private final static String node = "localhost";
	 private final static Integer port = 9042;

	 /**
	  * Get instance of GetBalanceCashOutUtil
	  * 
	  * @return
	  */
	 public static CASConnection getInstance() {
	  if (INSTANCE == null) {
	   synchronized (CASConnectionFactory.class) {
	    if (INSTANCE == null) {
	     INSTANCE = new CASConnection(node,port);
	    }
	   }
	  }
	  return INSTANCE;
	 }
}
