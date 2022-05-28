package server;

import server.repositories.InfoRepository;
import server.repositories.ProductsRepository;
import server.repositories.StocksRepository;
import server.tables.Item;
import server.tables.Product;
import server.tables.Stock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;

public class JAXBReadWriter {
    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfoRepository infoRepository;

    private final String productsXMLPath = "JaxbProducts.xml";
    private final String stocksXMLPath = "JaxbStocks.xml";
    private final String infosXMLPath = "JaxbInfos.xml";



    JAXBReadWriter() {
        productsRepository = new ProductsRepository();
        stocksRepository = new StocksRepository();
        infoRepository = new InfoRepository();
    }

    public void writeProductsRepository(String path, ProductsRepository productsRepository) throws XMLStreamException, FileNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductsRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(productsRepository, new File(productsXMLPath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void writeStocksRepository(String path, StocksRepository stocksRepository) throws XMLStreamException, FileNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(StocksRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(stocksRepository, new File(stocksXMLPath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void writeInfosRepository(String path, InfoRepository infoRepository) throws XMLStreamException, FileNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(InfoRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(infoRepository, new File(infosXMLPath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void readProductRepository(String path) throws FileNotFoundException, XMLStreamException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductsRepository.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
             productsRepository = (ProductsRepository) jaxbUnmarshaller.unmarshal( new File(productsXMLPath) );
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void readStocksRepository(String path) throws FileNotFoundException, XMLStreamException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductsRepository.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            stocksRepository = (StocksRepository) jaxbUnmarshaller.unmarshal( new File(stocksXMLPath) );
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void readInfosRepository(String path) throws FileNotFoundException, XMLStreamException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductsRepository.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            infoRepository = (InfoRepository) jaxbUnmarshaller.unmarshal( new File(infosXMLPath) );
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public ProductsRepository getProductsRepository() {
        return productsRepository;
    }

    public StocksRepository getStocksRepository() {
        return stocksRepository;
    }

    public InfoRepository getInfoRepository() {
        return infoRepository;
    }
}
