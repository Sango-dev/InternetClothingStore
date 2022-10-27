package ua.com.alevel.internetclothingstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.alevel.internetclothingstore.dao.BucketDao;
import ua.com.alevel.internetclothingstore.dao.ProductDao;
import ua.com.alevel.internetclothingstore.dto.ProductDTO;
import ua.com.alevel.internetclothingstore.mapper.ProductMapper;
import ua.com.alevel.internetclothingstore.model.Bucket;
import ua.com.alevel.internetclothingstore.model.Product;
import ua.com.alevel.internetclothingstore.model.User;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductDao productRepository;
    private final UserService userService;
    private static Page page;
    private final BucketService bucketService;

    public ProductServiceImpl(ProductDao productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    //TODO DONE
    @Override
    public Page getPage(){
        return page;
    }

    //TODO DONE
    @Override
    public List<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        page = productPage;
        List<Product> listOfProducts = productPage.getContent();
        return mapper.fromProductList(listOfProducts);
    }

    //TODO DONE
    @Override
    @Transactional
    public void addToUserBucket(String productId, String nickName) {
        User user = userService.findFirstByNickName(nickName);
        if (user == null) {
            throw new RuntimeException("User not found : " + nickName);
        }

        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }

    //TODO DONE
    @Override
    public List<ProductDTO> findAllByBrandId(String id) {
        return mapper.fromProductList(productRepository.findAllByBrandId(id));
    }

    //TODO DONE
    @Override
    public List<ProductDTO> findAllByCategoryId(String id) {
        return mapper.fromProductList(productRepository.findAllByCategoryId(id));
    }

    //TODO DONE
    @Override
    public List<ProductDTO> findAllByWord(String word) {
        return mapper.fromProductList(productRepository.findAllByWord(word.toLowerCase()));
    }

    //TODO DONE
    @Override
    public ProductDTO findById(String id) {
        if (id == null) {
            throw new RuntimeException("ID must not be empty!!!");
        }
        final Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            return mapper.fromProduct(byId.get());
        } else {
            return new ProductDTO();
        }
    }

    //TODO DONE
    @Override
    @Transactional
    public void updateProduct(String prodId, ProductDTO productDTO) {
        Product product = productRepository.findById(prodId).get();
        if (product == null) {
            throw new RuntimeException("Product is not found with ID : " + prodId);
        }
        final boolean availability = product.getAvailable();
        final String code = product.getCode();
        product = mapper.toProduct(productDTO);
        product.setAvailable(availability);
        product.setCode(code);
        if (product.getTitle().isBlank()) {
            product.setTitle("none");
        }
        if (product.getDescription().isBlank()) {
            product.setDescription("none");
        }
        if (product.getPictureCode().isBlank()) {
            product.setPictureCode("/images/products/prodisout.png");
        }
        productRepository.save(product);
    }

    //TODO DONE
    @Override
    @Transactional
    public void addProduct(ProductDTO dto) {
        Product product = mapper.toProduct(dto);
        product.setAvailable(true);
        product.setCode(product.getTitle() + new Random().nextInt(100000));
        if (product.getTitle().isBlank()) {
            product.setTitle("none");
        }
        if (product.getDescription().isBlank()) {
            product.setDescription("none");
        }
        if (product.getPictureCode().isBlank()) {
            product.setPictureCode("/images/products/prodisout.png");
        }
        productRepository.save(product);
    }

    //TODO DONE
    @Override
    @Transactional
    public void updateStateOfAvailability(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setAvailable(!product.get().getAvailable());
        }
    }
}
