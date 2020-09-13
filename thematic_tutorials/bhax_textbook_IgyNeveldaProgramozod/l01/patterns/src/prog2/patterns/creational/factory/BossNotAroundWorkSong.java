package prog2.patterns.creational.factory;

public class BossNotAroundWorkSong implements WorkSong{

	@Override
	public String getWorkSong() {
		return "Well you wake up in the mornin you hear the work bell ring\r\n" + 
				"And they march you to the table to see the same old thing.\r\n" + 
				"Aint no food upon the table and no pork up in the pan.\r\n" + 
				"But you better not complain boy you get in trouble with the man.";
	}

}
