package server;

import server.repositories.InfoRepository;
import server.repositories.ProductsRepository;
import server.repositories.StocksRepository;
import server.tables.Product;
import server.tables.TestContainer;
import shared.JdbcInterface;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.rmi.RemoteException;

/**
 * My attempt to do javadoc
 * <br>
 * <i><b>Have a nice day!</b></i>
 * <p style="color:red;">RED COLOR</p>
 * <p style="color:green;">GREEN COLOR</p>
 * <p style="color:blue;">BLUE COLOR</p>
 *
 * @author Timur Beziev
 * @see java.rmi.Remote
 */
public class XmlController implements JdbcInterface {

    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfoRepository infoRepository;
    private JAXBReadWriter jaxbReadWriter;
    private String productsRepoPath;
    private String stocksRepoPath;
    private String infosRepoPath;

    /**
     * Constructs a new StAX Controller
     *
     * @param productsRepoPath local path of products repository
     * @param stocksRepoPath   local path of stocks repository
     * @param infosRepoPath    local path of info repository
     */
    XmlController(String productsRepoPath, String stocksRepoPath, String infosRepoPath) {
        System.out.println("init controller");
        this.productsRepoPath = productsRepoPath;
        this.stocksRepoPath = stocksRepoPath;
        this.infosRepoPath = infosRepoPath;

        this.jaxbReadWriter = new JAXBReadWriter();

        try {
            initializeXMLReadWriter();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * <b>Initialize XML reading and writing class</b>
     *
     * @throws XMLStreamException
     * @throws FileNotFoundException
     */
    private void initializeXMLReadWriter() throws XMLStreamException, FileNotFoundException {
        jaxbReadWriter.readProductRepository(productsRepoPath);
        jaxbReadWriter.readStocksRepository(stocksRepoPath);
        jaxbReadWriter.readInfosRepository(infosRepoPath);

        this.productsRepository = jaxbReadWriter.getProductsRepository();
        this.stocksRepository = jaxbReadWriter.getStocksRepository();
        this.infoRepository = jaxbReadWriter.getInfoRepository();
    }

    /**
     * <b> This method add product with current price to info repositories</b>
     * <br>
     * if stocks and products repositories doesn't contain product info, it adds there too
     *
     * @param product name of product
     * @param stock   name of stock which contains product
     * @param price   current price of product
     * @throws RemoteException remote method call exception
     */
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

    /**
     * <b> Remove product from repositories</b>
     *
     * @param product name of product
     * @param stock   name of stock which contains product
     * @param price   price of current product
     * @throws RemoteException remote method call exception
     */
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

    /**
     * Change price for selected product
     *
     * @param product         name of product
     * @param stock           name of stock which contains product
     * @param pricePercentage change price of current product by this percentage
     * @throws RemoteException remote method call exception
     */
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

    /**
     * Method that writes object from repositories to XML file
     *
     * @throws XMLStreamException
     * @throws FileNotFoundException
     */
    private void writeToXml() throws XMLStreamException, FileNotFoundException {
        jaxbReadWriter.writeProductsRepository(productsRepoPath, productsRepository);
        jaxbReadWriter.writeStocksRepository(stocksRepoPath, stocksRepository);
        jaxbReadWriter.writeInfosRepository(infosRepoPath, infoRepository);
    }

    /**
     * Returns string of all Items from info repository
     *
     * @return {@link String String} list of Items
     * @throws RemoteException remote method call exception
     */

    @Override
    public String getInfoTable() throws RemoteException {
        return infoRepository.toString();
    }

    /**
     * Returns string of all products from products repository
     *
     * @return {@link String String} list of products
     * @throws RemoteException remote method call exception
     */

    @Override
    public String getProductsTable() throws RemoteException {
        return productsRepository.toString();
    }

    /**
     * Returns string of all stocks from stock repository
     *
     * @return {@link String String} list of stocks
     * @throws RemoteException remote method call exception
     */

    @Override
    public String getStocksTable() throws RemoteException {
        return stocksRepository.toString();
    }
}
