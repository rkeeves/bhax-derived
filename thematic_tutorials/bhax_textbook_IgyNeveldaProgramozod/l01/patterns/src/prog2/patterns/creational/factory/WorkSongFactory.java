package prog2.patterns.creational.factory;

public class WorkSongFactory {
	public WorkSong getWorkSong(boolean bossIsAround) {
		if (bossIsAround) {
			return new BossIsAroundWorkSong();
		}else {
			return new BossNotAroundWorkSong();
		}
	}
}
