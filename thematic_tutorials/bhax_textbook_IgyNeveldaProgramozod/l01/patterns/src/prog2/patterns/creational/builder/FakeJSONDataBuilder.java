package prog2.patterns.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class FakeJSONDataBuilder implements ChapterDataBuilder {

	@Override
	public void setTitle(String title) {
		this.title=title;
	}

	@Override
	public void addParagrapgh(String paragraph) {
		paragraphs.add(paragraph);
	}

	@Override
	public String getPersistentChapter() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append("title : '"+title+"', ");
		sb.append("paragraphs : [ ");
		if(paragraphs.isEmpty() == false) {
			for (String p : paragraphs) {
				sb.append("'").append(p).append("'").append(", ");
			}
			sb.delete(sb.length()-2, sb.length()-1);
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
	public String title = "";
	
	public final List<String> paragraphs = new ArrayList<>();
}
