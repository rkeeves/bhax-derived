package prog2.patterns.creational.builder;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Chapter ch = new Chapter();
		ch.title = "foo";
		ch.paragraphs.addAll(Arrays.asList("bar","bar2"));
		ChapterDataMaker cdm = new ChapterDataMaker(new FakeJSONDataBuilder());
		System.out.println(cdm.make(ch));
		cdm.setBuilder(new FakeXMLDataBuilder());
		System.out.println(cdm.make(ch));
	}

}
