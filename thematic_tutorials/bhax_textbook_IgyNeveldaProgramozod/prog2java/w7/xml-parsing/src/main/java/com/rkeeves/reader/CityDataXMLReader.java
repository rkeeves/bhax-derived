package com.rkeeves.reader;

import com.rkeeves.model.City;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class CityDataXMLReader {

    private final XMLStreamReader xmlStreamReader;

    private final Consumer<City> cityConsumer;

    public void run() throws XMLStreamException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(City.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        while (xmlStreamReader.hasNext()){
            switch (xmlStreamReader.next()){
                case XMLStreamConstants.END_DOCUMENT:
                    xmlStreamReader.close();
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    if (xmlStreamReader.getLocalName().equals("city")){
                        JAXBElement<City> unmarshalledObj = unmarshaller.unmarshal(xmlStreamReader, City.class);
                        offerCity(unmarshalledObj.getValue());
                    }
                    break;
            }
        }
    }

    private boolean offerCity(City city){
        cityConsumer.accept(city);
        return true;
    }
}
