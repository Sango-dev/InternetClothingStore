package ua.com.alevel.internetclothingstore.service;

import ua.com.alevel.internetclothingstore.dto.BucketDTO;
import ua.com.alevel.internetclothingstore.model.Bucket;
import ua.com.alevel.internetclothingstore.model.User;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<String> productIds);

    void addProducts(Bucket bucket, List<String> productIds);

    BucketDTO getBucketByUser(String name);

    void deleteProductFromBucket(String name, String productId);

    void clearBucket(String name);

    String makeOrder(String name);
}
