package com.amiya.spring6restmvc.bootstrap;

import com.amiya.spring6restmvc.entities.Beer;
import com.amiya.spring6restmvc.model.BeerCSVRecord;
import com.amiya.spring6restmvc.model.BeerDTO;
import com.amiya.spring6restmvc.model.BeerStyle;
import com.amiya.spring6restmvc.repository.BeerRepository;
import com.amiya.spring6restmvc.services.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;

    private final BeerCsvService beerCsvService;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if(beerRepository.count()<10){
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

            recs.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
            };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());

        });


    }
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0){
            Beer beer1= Beer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("Corona")
                    .beerStyle(BeerStyle.ALE)
                    .upc("123456")
                    .price(new BigDecimal("160.65"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2= Beer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("BroCode")
                    .beerStyle(BeerStyle.ALE)
                    .upc("789101")
                    .price(new BigDecimal("165"))
                    .quantityOnHand(120)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3= Beer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("Bira")
                    .beerStyle(BeerStyle.ALE)
                    .upc("123456")
                    .price(new BigDecimal("160.65"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            Beer beer4= Beer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("BroCode")
                    .beerStyle(BeerStyle.ALE)
                    .upc("789101")
                    .price(new BigDecimal("165"))
                    .quantityOnHand(120)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

    }


}