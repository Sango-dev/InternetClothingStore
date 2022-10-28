package ua.com.alevel.internetclothingstore.service;

import ua.com.alevel.internetclothingstore.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    List<BrandDTO> findAll();
}
