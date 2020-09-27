package prog2.patterns.creational.builder;

public class ChapterDataMaker {
	
	public ChapterDataMaker(ChapterDataBuilder chapterBuilder) {
		super();
		this.chapterBuilder = chapterBuilder;
	}
	
	String make(Chapter ch){
		chapterBuilder.setTitle(ch.title);
		ch.paragraphs.forEach((p)->chapterBuilder.addParagrapgh(p));
		return chapterBuilder.getPersistentChapter();
	}

	ChapterDataBuilder setBuilder(ChapterDataBuilder v) {
		ChapterDataBuilder t = chapterBuilder;
		chapterBuilder = v;
		return t;
	}
	private ChapterDataBuilder chapterBuilder;
}
