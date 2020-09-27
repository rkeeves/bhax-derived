package prog2.patterns.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class FakeXMLDataBuilder implements ChapterDataBuilder {

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
			sb.append("<chapter title=");
			sb.append("\"");
			sb.append(title);
			sb.append("\"");
			sb.append(">");
			sb.append("<paragraphs>");
			if(paragraphs.isEmpty() == false) {
				for (String p : paragraphs) {
					sb.append("<paragraph>").append(p).append("</paragraph>");
				}
			}
			sb.append("</paragraphs>");
			sb.append("</chapter>");
			return sb.toString();
		}
		public String title = "";
		
		public final List<String> paragraphs = new ArrayList<>();
	}