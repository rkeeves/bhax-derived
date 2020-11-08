package com.rkeeves.writer;

import com.rkeeves.model.City;
import com.rkeeves.model.CityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CitySVGWriter implements Closeable {

    private final CityRepository cityRepository;

    private final FileOutputStream fileOutputStream;

    private Map<String, String> createColorsOfStates(Set<String> stateCodes) {
        var rnd = new Random();
        return stateCodes.stream()
                .collect(Collectors.toMap(hexColor -> hexColor, hexColor -> String.format("#%06X", rnd.nextInt(0xFFFFFF))));
    }

    public void run() throws TransformerException {
        var colorsOfStates = createColorsOfStates(cityRepository.getStates());
        var document = createDOMSource();
        var root = createRootElement(document);

        cityRepository
                .getStates()
                .forEach(stateCode -> processState(document,
                        root,
                        stateCode,
                        colorsOfStates.get(stateCode),
                        cityRepository.getCitiesForState(stateCode)));
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(fileOutputStream));
    }

    private Document createDOMSource() {
        return SVGDOMImplementation
                .getDOMImplementation()
                .createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
    }

    private Element createRootElement(Document document) {
        Element rootElement = document.getDocumentElement();
        rootElement.setAttributeNS(null, "width", "800");
        rootElement.setAttributeNS(null, "height", "600");
        return rootElement;
    }

    private Document processState(Document document, Element root, String stateCode, String stateColor, List<City> cityList) {
        Element stateElement = createStateElement(document, stateCode);
        cityList.forEach(city -> createCityElement(document, stateElement, city, stateColor));
        root.appendChild(stateElement);
        return document;
    }

    private Element createStateElement(Document document, String stateCode) {
        Element stateElement = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        Element descElement = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "desc");
        descElement.setTextContent(stateCode);
        stateElement.appendChild(descElement);
        return stateElement;
    }

    private Element createCityElement(Document document, Element parent, City city, String color) {
        Element element = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "circle");
        element.setAttributeNS(null, "cy", city.getX());
        element.setAttributeNS(null, "cx", city.getY());
        element.setAttributeNS(null, "fill", color);
        element.setAttributeNS(null, "r", "1");
        parent.appendChild(element);
        return element;
    }

    @Override
    public void close() throws IOException {
        fileOutputStream.close();
    }
}
