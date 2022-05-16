package server;

import server.repositories.InfoRepository;
import server.repositories.ProductsRepository;
import server.repositories.StocksRepository;
import shared.JdbcInterface;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;

public class StaxController implements JdbcInterface {

    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfoRepository infoRepository;
    private XMLReadWriter xmlReadWriter;
    private String productsRepoPath;
    private String stocksRepoPath;
    private String infosRepoPath;


    StaxController(String productsRepoPath, String stocksRepoPath, String infosRepoPath) {
        System.out.println("init controller");
        this.productsRepoPath = productsRepoPath;
        this.stocksRepoPath = stocksRepoPath;
        this.infosRepoPath = infosRepoPath;

        this.xmlReadWriter = new XMLReadWriter();

        try {
            initializeXMLReadWriter();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeXMLReadWriter() throws XMLStreamException, FileNotFoundException {
        xmlReadWriter.readProductRepository(productsRepoPath);
        xmlReadWriter.readStocksRepository(stocksRepoPath);
        xmlReadWriter.readInfosRepository(infosRepoPath);

        this.productsRepository = xmlReadWriter.getProductsRepository();
        this.stocksRepository = xmlReadWriter.getStocksRepository();
        this.infoRepository = xmlReadWriter.getInfoRepository();
    }

    @Override
    public void addProduct(String product, String stock, String price) throws RemoteException {
        if (product.isBlank() || stock.isBlank() || price.isBlank()) {
            System.out.println("can't add product");
            return;
        }
        double productPrice = Double.parseDouble(price);

        stocksRepository.addStock(stock);
        productsRepository.addProduct(product);
        infoRepository.add(
                productsRepository.getProductByName(product),
                stocksRepository.getStockByName(stock),
                productPrice
        );
        try {
            writeToXml();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeProduct(String product, String stock, String price) throws RemoteException {
        if (product.isBlank() && stock.isBlank() && price.isBlank()) {
            System.out.println("clear repo");
            infoRepository.clearRepository();
        } else if (stock.isBlank() && price.isBlank()) {
            infoRepository.removeItem(productsRepository.getProductByName(product));
        } else if (price.isBlank()) {
            infoRepository.removeItem(
                    productsRepository.getProductByName(product),
                    stocksRepository.getStockByName(stock)
            );
        } else if (!product.isBlank() && !stock.isBlank() && !price.isBlank()) {
            infoRepository.removeItem(
                    productsRepository.getProductByName(product),
                    stocksRepository.getStockByName(stock),
                    Double.parseDouble(price)
            );
        }
        try {
            writeToXml();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePrice(String product, String stock, String pricePercentage) throws RemoteException {
        if (pricePercentage.isBlank()) {
            return;
        }

        double productPricePercentage = Double.parseDouble(pricePercentage) / 100;

        if (product.isBlank() && stock.isBlank()) {
            infoRepository.changePrice(productPricePercentage);
        } else if (stock.isBlank()) {
            infoRepository.changePrice(
                    productsRepository.getProductByName(product),
                    productPricePercentage
            );

        } else {
            infoRepository.changePrice(
                    productsRepository.getProductByName(product),
                    stocksRepository.getStockByName(stock),
                    productPricePercentage
            );
        }
        try {
            writeToXml();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeToXml() throws XMLStreamException, FileNotFoundException {
        xmlReadWriter.writeProductsRepository(productsRepoPath, productsRepository);
        xmlReadWriter.writeStocksRepository(stocksRepoPath, stocksRepository);
        xmlReadWriter.writeInfosRepository(infosRepoPath, infoRepository);
    }

    @Override
    public String getInfoTable() throws RemoteException {
        return infoRepository.toString();
    }

    @Override
    public String getProductsTable() throws RemoteException {
        return productsRepository.toString();
    }

    @Override
    public String getStocksTable() throws RemoteException {
        return stocksRepository.toString();
    }
}
