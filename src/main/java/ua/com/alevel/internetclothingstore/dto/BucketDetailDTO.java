package ua.com.alevel.internetclothingstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.alevel.internetclothingstore.model.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailDTO {
    private String id;
    private String title;
    String pictureCode;
    private String productId;
    private Double price;
    private BigDecimal amount;
    private Double sum;

    public BucketDetailDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.pictureCode = product.getPictureCode();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(product.getPrice().toString());
    }
}
