package prog2.ica;

public class HelloService extends BaseService{


	@Override
	protected void onInit() {
		System.out.println("Hello World!");
	}

	@Override
	protected void onDestroy() {
		System.out.println("Bye World!");
	}

}
