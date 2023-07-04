package com.amiya.spring6restmvc.repository;

import com.amiya.spring6restmvc.bootstrap.BootstrapData;
import com.amiya.spring6restmvc.entities.Beer;
import com.amiya.spring6restmvc.model.BeerStyle;
import com.amiya.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;


import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;



    @Test
    void Test1(){
        Beer savedBeer=beerRepository.save(Beer.builder()
                .beerName("Something")
                        .beerStyle(BeerStyle.IPA)
                        .upc("342534")
                        .price(new BigDecimal("1165.54"))
                .build());
        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
     }

    @Test
    void nameTooLongTest(){

        assertThrows(ConstraintViolationException.class,()->{
            Beer savedBeer=beerRepository.save(Beer.builder()
                    .beerName("Something12345678901234567890123456789012345678901234567890")
                    .beerStyle(BeerStyle.IPA)
                    .upc("342534")
                    .price(new BigDecimal("1165.54"))
                    .build());
            beerRepository.flush();

        });
    }

    @Test
    void testGetBeerListByName(){
        Page<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(list.getContent().size()).isEqualTo(336);
    }

}