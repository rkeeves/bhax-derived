package prog2.ica;

public interface Initializable {
	
	default void sayMyName() {
		System.out.println("Initializable");
	}
	
	void initialize();
}
