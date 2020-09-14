package prog2.ica;

public class UserBasedHelloService extends BaseService {
	
	public UserBasedHelloService(String name){
		this.name=name;
	}
	
	@Override
	protected void onInit() {
		System.out.println("Hello "+name+"!");
	}

	@Override
	protected void onDestroy() {
		System.out.println("Bye "+name+"!");
	}
	
	private final String name;
}
