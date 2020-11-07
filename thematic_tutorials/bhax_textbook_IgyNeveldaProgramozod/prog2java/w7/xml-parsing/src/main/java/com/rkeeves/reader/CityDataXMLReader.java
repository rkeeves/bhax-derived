package com.rkeeves.reader;

import com.rkeeves.model.City;
import lombok.RequiredArgsConstructor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class CityDataXMLReader {

    private final XMLStreamReader xmlStreamReader;

    private final Consumer<City> cityConsumer;

    public void run() throws XMLStreamException {
        var builder = City.builder();
        while(xmlStreamReader.hasNext()){
            int actual = xmlStreamReader.next();
            if (isStartElement(actual, "coordinateX")) {
                builder.x(xmlStreamReader.getElementText());
            } else if (isStartElement(actual, "coordinateY")) {
                builder.y(xmlStreamReader.getElementText());
            } else if (isStartElement(actual, "state")) {
                builder.stateCode(xmlStreamReader.getElementText());
            } else if (isEndElement(actual, "city")) {
                offerCity(builder.build());
            }
        }
    }

    private boolean isStartElement(int actual, String tagName) {
        return actual == XMLStreamReader.START_ELEMENT && tagName.equals(xmlStreamReader.getLocalName());
    }

    private boolean isEndElement(int actual, String tagName) {
        return actual == XMLStreamReader.END_ELEMENT && tagName.equals(xmlStreamReader.getLocalName());
    }

    private boolean offerCity(City city){
        cityConsumer.accept(city);
        return true;
    }
}
