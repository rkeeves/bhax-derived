package prog2.patterns.creational.builder;

public interface ChapterDataBuilder {
	
	void setTitle(String title);
	
	void addParagrapgh(String paragraph);
	
	String getPersistentChapter();
}
