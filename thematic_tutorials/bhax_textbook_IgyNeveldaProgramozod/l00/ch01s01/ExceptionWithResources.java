import java.io.IOException;

public class ExceptionWithResources {
	static class Pool implements AutoCloseable{

		public void visitPool() throws IOException{
			throw new IOException("Tried visiting pool, but failed");
		}
		
		@Override
		public void close() throws IOException {
			System.out.println("Closing pool");
		}
		
	}
	
	
	static class Pool2 implements AutoCloseable{

		public void visitPool() throws IOException{
			throw new IOException("Tried visiting pool, but failed");
		}
		
		@Override
		public void close() throws IOException {
			throw new IOException("Cant close pool");
		}
		
	}

	static class Pool3 implements AutoCloseable{

		public void visitPool() throws IOException{
			System.out.println("Visited pool");
		}
		
		@Override
		public void close() throws IOException {
			System.out.println("Closing pool");
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println("Pool");
		try (Pool pool = new Pool()) {
			pool.visitPool();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally {
			System.out.println("Finally");
		}
		System.out.println("Pool2");
		try (Pool2 pool = new Pool2()) {
			pool.visitPool();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally {
			System.out.println("Finally");
		}
		System.out.println("Pool3");
		try (Pool3 pool = new Pool3()) {
			pool.visitPool();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally {
			System.out.println("Finally");
		}
	}
}
