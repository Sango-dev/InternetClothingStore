package ua.com.alevel.internetclothingstore.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.com.alevel.internetclothingstore.dto.BrandDTO;
import ua.com.alevel.internetclothingstore.model.Brand;

import java.util.List;

@Mapper
public interface BrandMapper {
    BrandMapper MAPPER = Mappers.getMapper(BrandMapper.class);

    Brand toBrand(BrandDTO dto);

    @InheritInverseConfiguration
    BrandDTO fromBrand(Brand brand);

    List<Brand> toBrandList(List<BrandDTO> dtos);

    List<BrandDTO> fromBrandList(List<Brand> brands);
}
