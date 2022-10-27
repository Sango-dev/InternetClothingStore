package ua.com.alevel.internetclothingstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.internetclothingstore.model.Bucket;

public interface BucketDao extends JpaRepository<Bucket, String> {
}
