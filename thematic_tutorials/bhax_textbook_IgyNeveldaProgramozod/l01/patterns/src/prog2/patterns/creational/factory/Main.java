package prog2.patterns.creational.factory;

public class Main {
	
	/*
	 * This example program will print out two different worksongs,
	 * based on whether the boss is around.
	 */
	public static void main(String[] args) {
		WorkSongFactory fact = new WorkSongFactory();
		System.out.println("***** Boss is around? *****");
		System.out.println(fact.getWorkSong(true).getWorkSong());
		System.out.println("***** Boss is NOT around? *****");
		System.out.println(fact.getWorkSong(false).getWorkSong());

	}

}
