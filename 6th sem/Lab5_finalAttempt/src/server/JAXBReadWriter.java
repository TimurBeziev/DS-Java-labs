package server;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;

public class JAXBReadWriter {
    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfoRepository infoRepository;


    JAXBReadWriter(String infosXMLPath, String productsXMLPath, String stocksXMLPath) {
        productsRepository = new ProductsRepository();
        stocksRepository = new StocksRepository();
        infoRepository = new InfoRepository();
    }

    public void writeProductsRepository(String path, ProductsRepository productsRepository) throws XMLStreamException, FileNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductsRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(productsRepository, new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void writeStocksRepository(String path, StocksRepository stocksRepository) throws XMLStreamException, FileNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(StocksRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(stocksRepository, new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void writeInfosRepository(String path, InfoRepository infoRepository) throws XMLStreamException, FileNotFoundException {
        try {
            JAXBContext context = JAXBContext.newInstance(InfoRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(infoRepository, new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void readProductRepository(String path) throws FileNotFoundException, XMLStreamException {
        System.out.println("Read products repo");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductsRepository.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            productsRepository = (ProductsRepository) jaxbUnmarshaller.unmarshal(new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void readStocksRepository(String path) throws FileNotFoundException, XMLStreamException {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(StocksRepository.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            stocksRepository = (StocksRepository) jaxbUnmarshaller.unmarshal(new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void readInfosRepository(String path) throws FileNotFoundException, XMLStreamException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(InfoRepository.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            infoRepository = (InfoRepository) jaxbUnmarshaller.unmarshal(new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public ProductsRepository getProductsRepository(String path) throws XMLStreamException, FileNotFoundException {
        readProductRepository(path);
        return productsRepository;
    }

    public StocksRepository getStocksRepository(String path) throws XMLStreamException, FileNotFoundException {
        readStocksRepository(path);
        return stocksRepository;
    }

    public InfoRepository getInfoRepository(String path) throws XMLStreamException, FileNotFoundException {
        readInfosRepository(path);
        return infoRepository;
    }
}
