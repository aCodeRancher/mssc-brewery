package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale")
                .build();
    }
    @Override
    public List<BeerDto> getBeers() {
         List<BeerDto> beers = new ArrayList<>();

         BeerDto beer1 =  BeerDto.builder().id(UUID.randomUUID())
                .beerName("Sunny Beer")
                .beerStyle("sweet")
                .build();
        BeerDto beer2 =  BeerDto.builder().id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale")
                .build();
        beers.add(beer1);
        beers.add(beer2);
        return beers;
    }
}
