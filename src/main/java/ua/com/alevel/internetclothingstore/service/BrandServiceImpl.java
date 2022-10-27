package ua.com.alevel.internetclothingstore.service;

import org.springframework.stereotype.Service;
import ua.com.alevel.internetclothingstore.dao.BrandDao;
import ua.com.alevel.internetclothingstore.dto.BrandDTO;
import ua.com.alevel.internetclothingstore.mapper.BrandMapper;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandMapper brandMapper = BrandMapper.MAPPER;

    private final BrandDao brandRepository;

    public BrandServiceImpl(BrandDao brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDTO> findAll() {
        return brandMapper.fromBrandList(brandRepository.findAll());
    }
}
