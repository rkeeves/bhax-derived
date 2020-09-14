package prog2.ica;

/**
 * 
 * Abstract classes can provide a contract about behavior without
 * implementation. Abstract classes deal with the object's internal state.
 * Abstract classes can deal with actual implementation.
 * Abstract classes cannot be instantiated.
 *
 */
public abstract class BaseService implements Service{

	private static Long last_id = 0L;
	
	public BaseService(){
		this.id = last_id++;
	}
	
	public Long getId() {
		return id;
	};
	
	public void initialize() {
		System.out.println("Service "+id+" pre initialize.");
		onInit();
		System.out.println("Service "+id+" post initialize.");
	};
	
	protected abstract void onInit();

	public void destroy() {
		System.out.println("Service "+id+" pre destroy.");
		onDestroy();
		System.out.println("Service "+id+" post destroy.");
	}
	
	protected abstract void onDestroy();

	private Long id;
}
