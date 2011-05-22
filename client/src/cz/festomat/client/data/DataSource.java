package cz.festomat.client.data;

public class DataSource {

	private static IDataSource instance = null;
	
	private DataSource() {
	}
	
	public static IDataSource getInstance() {
		if(instance == null) {
			//pro testovaci ucely
			instance = new DataSourceImplTest();
			
			//ostre DS
			//instance = new DataSourceImpl();
		}
		return instance;
	}
	
}
