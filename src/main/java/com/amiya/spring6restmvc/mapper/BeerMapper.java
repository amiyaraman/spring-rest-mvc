package com.amiya.spring6restmvc.mapper;


import com.amiya.spring6restmvc.entities.Beer;
import com.amiya.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);


}
