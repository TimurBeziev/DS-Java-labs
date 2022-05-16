package server;

import server.repositories.InfoRepository;
import server.repositories.ProductsRepository;
import server.repositories.StocksRepository;
import server.tables.Item;
import server.tables.Product;
import server.tables.Stock;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;

public class XMLReadWriter {

    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfoRepository infoRepository;


    XMLReadWriter() {
        productsRepository = new ProductsRepository();
        stocksRepository = new StocksRepository();
        infoRepository = new InfoRepository();
    }


    public void writeProductsRepository(String path, ProductsRepository productsRepository) throws XMLStreamException, FileNotFoundException {
        System.out.println(productsRepository.getProducts());
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        FileOutputStream fos = new FileOutputStream(path);
        XMLStreamWriter writer = output.createXMLStreamWriter(fos);

        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("products");
        for (Product product : productsRepository.getProducts()) {
            writer.writeStartElement("product");
            writer.writeAttribute("id", String.valueOf(product.getUniqueID()));
            writer.writeAttribute("product_name", product.getName());
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndDocument();
    }

    public void writeStocksRepository(String path, StocksRepository stocksRepository) throws XMLStreamException, FileNotFoundException {
        System.out.println(stocksRepository.getStocks());
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        FileOutputStream fos = new FileOutputStream(path);
        XMLStreamWriter writer = output.createXMLStreamWriter(fos);

        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("stocks");
        for (Stock stock : stocksRepository.getStocks()) {
            writer.writeStartElement("stock");
            writer.writeAttribute("id", String.valueOf(stock.getUniqueID()));
            writer.writeAttribute("stock_name", stock.getName());
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndDocument();
    }

    public void writeInfosRepository(String path, InfoRepository infoRepository) throws XMLStreamException, FileNotFoundException {
        System.out.println(infoRepository.getInfo());
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        FileOutputStream fos = new FileOutputStream("/Users/timurbeziev/Uni/Java/6th sem/Lab45/src/main/resources/xmlFiles/infos.xml");
        XMLStreamWriter writer = output.createXMLStreamWriter(fos);

        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("info");
        for (Item item : infoRepository.getInfo()) {
            writer.writeStartElement("item");
            writer.writeAttribute("id", Integer.toString(item.getUniqueID()));
            writer.writeAttribute("product_name", item.getProduct().getName());
            writer.writeAttribute("stock_name", item.getStock().getName());
            writer.writeAttribute("price", String.valueOf(item.getPrice()));
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndDocument();
    }

    public void readProductRepository(String path) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (element.getName().getLocalPart().equals("product")) {
                    String uniqueID = element.getAttributeByName(new QName("id")).getValue();
                    String name = element.getAttributeByName(new QName("product_name")).getValue();
                    if (uniqueID != null && name != null) {
                        productsRepository.addProduct(Integer.parseInt(uniqueID), name);
                    }
                }
            }

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
            }
        }
    }

    public void readStocksRepository(String path) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (element.getName().getLocalPart().equals("stock")) {
                    String uniqueID = element.getAttributeByName(new QName("id")).getValue();
                    String name = element.getAttributeByName(new QName("stock_name")).getValue();
                    if (uniqueID != null && name != null) {
                        stocksRepository.addStock(Integer.parseInt(uniqueID), name);
                    }
                }
            }

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
            }
        }
    }

    public void readInfosRepository(String path) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (element.getName().getLocalPart().equals("stock")) {
                    int uniqueID = Integer.parseInt(element.getAttributeByName(new QName("id")).getValue());
                    String productName = element.getAttributeByName(new QName("product_name")).getValue();
                    String stockName = element.getAttributeByName(new QName("stock_name")).getValue();
                    double price = Double.parseDouble(element.getAttributeByName(new QName("price")).getValue());
                    infoRepository.add(
                            uniqueID,
                            productsRepository.getProductByName(productName),
                            stocksRepository.getStockByName(stockName),
                            price
                    );
                }
            }

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
            }
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
