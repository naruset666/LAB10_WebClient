package com.example.lab10.client;

import com.example.lab10.model.Product;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WebClientTestRunner {

    private final ProductWebClient webClient;

    public WebClientTestRunner(ProductWebClient webClient) {
        this.webClient = webClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testWebClient() {

        // 1. ดึงสินค้าทั้งหมด
        webClient.getAllProducts()
                .doOnNext(product ->
                        System.out.println("Product: " + product.getName()))
                .subscribe();

        // 2. ดึงสินค้าตาม ID
        webClient.getProductById("1")
                .doOnNext(product ->
                        System.out.println("Found: " + product.getName()))
                .subscribe();

        // 3. ดึงสินค้าตาม Category
        webClient.getByCategory("Electronics")
                .doOnNext(product ->
                        System.out.println("Category: " + product.getName()))
                .subscribe();

        // 4. ดึงราคาหลังส่วนลด
        webClient.getDiscountedPrice("1")
                .doOnNext(price ->
                        System.out.println("Discounted Price: " + price))
                .subscribe();

        // 5. สร้างสินค้าใหม่
        Product product = new Product(
                null,
                "WebClient Product",
                "Electronics",
                "Test Brand",
                10,
                1000.0,
                "MEMBER"
        );

        webClient.createProduct(product)
                .doOnNext(saved ->
                        System.out.println("Created: " + saved.getName()))
                .subscribe();
    }
}