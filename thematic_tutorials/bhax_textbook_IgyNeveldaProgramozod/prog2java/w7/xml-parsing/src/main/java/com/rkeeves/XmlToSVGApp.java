package com.rkeeves;

import com.rkeeves.model.CityRepository;
import com.rkeeves.reader.CityDataXMLReader;
import com.rkeeves.writer.CitySVGWriter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XmlToSVGApp {

    CityRepository cityRepository = new CityRepository();

    public void read() throws IOException, XMLStreamException {
        try(FileInputStream inputStream = new FileInputStream("./data/input.xml")){
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(inputStream);
            var cityXMLEventReader = new CityDataXMLReader(reader, cityRepository);
            cityXMLEventReader.run();
        }
    }

    public void write(String fileName) throws IOException, TransformerException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            CitySVGWriter citySVGWriter = new CitySVGWriter(cityRepository,fileOutputStream);
            citySVGWriter.run();
        }
    }

    public static void main(String[] args)  {
        XmlToSVGApp xmlToSVGApp = new XmlToSVGApp();
        try {
            xmlToSVGApp.read();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            xmlToSVGApp.write("./data/out.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
