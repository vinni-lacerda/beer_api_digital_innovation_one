package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.exception.BeerStockExceededException;
import one.digitalinnovation.beerstock.mapper.BeerMapper;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    @Spy
    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    private BeerDTOBuilder beerDTOBuilder;

    @BeforeEach
    void setUp() {
        beerDTOBuilder = BeerDTOBuilder.builder();
    }

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
        // given
        BeerDTO expectedBeerDTO = beerDTOBuilder.build();
        Beer expectedSavedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(any(Beer.class))).thenReturn(expectedSavedBeer);

        // when
        BeerDTO created = beerService.createBeer(expectedBeerDTO);

        // then
        assertThat(created.getId(), is(expectedSavedBeer.getId()));
        assertThat(created.getName(), is(expectedSavedBeer.getName()));
        assertThat(created.getQuantity(), is(expectedSavedBeer.getQuantity()));
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
        BeerDTO expectedBeerDTO = beerDTOBuilder.build();
        Beer duplicatedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeerDTO));
        verify(beerRepository, never()).save(any(Beer.class));
    }

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundException {
        BeerDTO expectedBeerDTO = beerDTOBuilder.build();
        Beer expectedFoundBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(expectedFoundBeer));

        BeerDTO found = beerService.findByName(expectedBeerDTO.getName());

        assertThat(found.getName(), is(expectedFoundBeer.getName()));
        assertThat(found.getId(), is(expectedFoundBeer.getId()));
    }

    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {
        BeerDTO expectedBeerDTO = beerDTOBuilder.build();

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedBeerDTO.getName()));
    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {
        BeerDTO expectedBeerDTO = beerDTOBuilder.build();
        Beer expectedFoundBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

        List<BeerDTO> beerList = beerService.listAll();

        assertThat(beerList, is(not(empty())));
        assertThat(beerList.get(0).getName(), is(expectedFoundBeer.getName()));
    }

    @Test
    void whenListBeerIsCalledThenReturnAnEmptyList() {
        when(beerRepository.findAll()).thenReturn(Collections.emptyList());

        List<BeerDTO> beerList = beerService.listAll();

        assertThat(beerList, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenBeerShouldBeDeleted() throws BeerNotFoundException {
        BeerDTO expectedBeerDTO = beerDTOBuilder.build();
        Beer expectedFoundBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedFoundBeer.getId())).thenReturn(Optional.of(expectedFoundBeer));
        doNothing().when(beerRepository).deleteById(expectedFoundBeer.getId());

        beerService.deleteById(expectedFoundBeer.getId());

        verify(beerRepository, times(1)).deleteById(expectedFoundBeer.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {
        BeerDTO beerDTO = beerDTOBuilder.build();
        Beer beer = beerMapper.toModel(beerDTO);
        int quantityToIncrement = 5;
        int expectedQuantityAfterIncrement = beer.getQuantity() + quantityToIncrement;

        when(beerRepository.findById(beer.getId())).thenReturn(Optional.of(beer));
        when(beerRepository.save(beer)).thenReturn(beer);

        BeerDTO updated = beerService.increment(beer.getId(), quantityToIncrement);

        assertThat(updated.getQuantity(), is(expectedQuantityAfterIncrement));
    }

    @Test
    void whenIncrementIsGreaterThanMaxThenThrowException() {
        BeerDTO beerDTO = beerDTOBuilder.build();
        Beer beer = beerMapper.toModel(beerDTO);
        int quantityToIncrement = beer.getMax() + 1;

        when(beerRepository.findById(beer.getId())).thenReturn(Optional.of(beer));

        assertThrows(BeerStockExceededException.class,
                () -> beerService.increment(beer.getId(), quantityToIncrement));
    }
}
