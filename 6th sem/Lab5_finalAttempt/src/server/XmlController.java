package server;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;

public class XmlController implements JdbcInterface {
    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfoRepository infoRepository;
    private JAXBReadWriter jaxbReadWriter;
    private String productsRepoPath;
    private String stocksRepoPath;
    private String infosRepoPath;

    XmlController(String productsRepoPath, String stocksRepoPath, String infosRepoPath) {
        System.out.println("init controller");
        this.productsRepoPath = productsRepoPath;
        this.stocksRepoPath = stocksRepoPath;
        this.infosRepoPath = infosRepoPath;

        this.jaxbReadWriter = new JAXBReadWriter(infosRepoPath, productsRepoPath, stocksRepoPath);

        try {
            initializeXMLReadWriter();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeXMLReadWriter() throws XMLStreamException, FileNotFoundException {
        ProductsRepository productsRepository1 = new ProductsRepository();
        productsRepository1.addProduct("pr1");
        productsRepository1.addProduct("pr2");

        StocksRepository stocksRepository1 = new StocksRepository();
        stocksRepository1.addStock("st1");
        stocksRepository1.addStock("st2");
        InfoRepository infoRepository1 = new InfoRepository();
        infoRepository1.add(new Product(1, "product"), new Stock(1, "stock"), 100f);
        infoRepository1.add(new Product(2, "pr"), new Stock(1, "stock"), 10f);

        jaxbReadWriter.writeInfosRepository(infosRepoPath, infoRepository1);
        jaxbReadWriter.writeProductsRepository(productsRepoPath, productsRepository1);
//        jaxbReadWriter.writeStocksRepository(stocksRepoPath, stocksRepository1);

        this.productsRepository = jaxbReadWriter.getProductsRepository(productsRepoPath);
        System.out.println(productsRepository + "\n");
        this.stocksRepository = jaxbReadWriter.getStocksRepository(stocksRepoPath);
        System.out.println(stocksRepository);
        this.infoRepository = jaxbReadWriter.getInfoRepository(infosRepoPath);
        System.out.println(infoRepository);
    }

    @Override
    public void addProduct(String product, String stock, String price) throws RemoteException {
        if (product.isEmpty() || stock.isEmpty() || price.isEmpty()) {
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
        if (product.isEmpty() && stock.isEmpty() && price.isEmpty()) {
            System.out.println("clear repo");
            infoRepository.clearRepository();
        } else if (stock.isEmpty() && price.isEmpty()) {
            infoRepository.removeItem(productsRepository.getProductByName(product));
        } else if (price.isEmpty()) {
            infoRepository.removeItem(
                    productsRepository.getProductByName(product),
                    stocksRepository.getStockByName(stock)
            );
        } else if (!product.isEmpty() && !stock.isEmpty() && !price.isEmpty()) {
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
        if (pricePercentage.isEmpty()) {
            return;
        }

        double productPricePercentage = Double.parseDouble(pricePercentage) / 100;

        if (product.isEmpty() && stock.isEmpty()) {
            infoRepository.changePrice(productPricePercentage);
        } else if (stock.isEmpty()) {
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
        jaxbReadWriter.writeProductsRepository(productsRepoPath, productsRepository);
        jaxbReadWriter.writeStocksRepository(stocksRepoPath, stocksRepository);
        jaxbReadWriter.writeInfosRepository(infosRepoPath, infoRepository);
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
