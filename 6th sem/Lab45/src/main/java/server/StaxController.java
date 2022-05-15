package server;


import server.repositories.InfoRepository;
import server.repositories.ProductsRepository;
import server.repositories.StocksRepository;
import shared.JdbcInterface;

import java.rmi.RemoteException;
import java.sql.Statement;

public class StaxController implements JdbcInterface {

    private ProductsRepository productsRepository;
    private StocksRepository stocksRepository;
    private InfoRepository infoRepository;

    StaxController(String productsPath, String stocksPath, String infoPath) {
        productsRepository = new ProductsRepository();
        stocksRepository = new StocksRepository();
        infoRepository = new InfoRepository();
        System.out.println("init controller");
    }

    StaxController() {
        productsRepository = new ProductsRepository();
        stocksRepository = new StocksRepository();
        infoRepository = new InfoRepository();
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

        System.out.println(infoRepository);
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
    }

    @Override
    public void changePrice(String product, String stock, String pricePercentage) throws RemoteException {
        if (pricePercentage.isBlank()) {
            return;
        }

        Double productPricePercentage = Double.parseDouble(pricePercentage) / 100;

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
