package prog2.patterns.creational.factory;

public class BossIsAroundWorkSong implements WorkSong {

	@Override
	public String getWorkSong() {
		return "How doth the little busy bee\r\n" + 
				"    Improve each shining hour, \r\n" + 
				"And gather honey all the day\r\n" + 
				"    From every opening flower!";
	}

}
