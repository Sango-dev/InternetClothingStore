package ua.com.alevel.internetclothingstore.service;

import org.springframework.stereotype.Service;
import ua.com.alevel.internetclothingstore.dao.BucketDao;
import ua.com.alevel.internetclothingstore.dao.ProductDao;
import ua.com.alevel.internetclothingstore.dto.BucketDTO;
import ua.com.alevel.internetclothingstore.dto.BucketDetailDTO;
import ua.com.alevel.internetclothingstore.model.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketDao bucketRepository;
    private final ProductDao productRepository;
    private final UserService userService;
    private final OrderService orderService;

    public BucketServiceImpl(BucketDao bucketRepository, ProductDao productRepository, UserService userService, OrderService orderService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Transactional
    @Override
    public Bucket createBucket(User user, List<String> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<String> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProducts(Bucket bucket, List<String> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findFirstByNickName(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }

        BucketDTO bucketDTO = new BucketDTO();
        Map<String, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }

        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    @Override
    @Transactional
    public void deleteProductFromBucket(String name, String productId) {
        User user = userService.findFirstByNickName(name);
        if (user == null || user.getBucket() == null) {
            throw new RuntimeException("You are not authorized or bucket is not existing!!!");
        }

        List<Product> products = user.getBucket().getProducts();
        if (products.isEmpty()) {
            throw new RuntimeException("Bucket is empty!!!");
        }

        int number = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                number = i;
                break;
            }
        }

        if (number != -1) {
            products.remove(number);
        } else {
            throw new RuntimeException("This bucket does not contain product with id: '" + productId + "'");
        }
    }

    @Override
    @Transactional
    public void clearBucket(String name) {
        User user = userService.findFirstByNickName(name);
        if (user == null || user.getBucket() == null) {
            throw new RuntimeException("You are not authorized or bucket is not existing!!!");
        }
        user.getBucket().getProducts().clear();
    }

    @Override
    @Transactional
    public String makeOrder(String name) {
        User user = userService.findFirstByNickName(name);

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        BucketDTO bucketDTO = getBucketByUser(name);
        List<OrderDetails> orderDetails = bucketDTO.getBucketDetails()
                .stream()
                .map(bucketDetailDTO -> new OrderDetails(order, productRepository.getOne(bucketDetailDTO.getProductId()), bucketDetailDTO.getAmount().intValue()))
                .collect(Collectors.toList());

        order.setSum(new BigDecimal(bucketDTO.getSum()));
        order.setDetails(orderDetails);

        String id = orderService.saveOrder(order);
        user.getBucket().getProducts().clear();
        return id;
    }
}
