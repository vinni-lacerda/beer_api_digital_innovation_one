package one.digitalinnovation.beerstock.builder;

import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.enums.BeerType;

public class BeerDTOBuilder {
    private static final long ID = 1L;
    private static final String NAME = "Heineken";
    private static final int QUANTITY = 10;
    private static final int MAX = 50;
    private static final String BRAND = "Heineken";
    private static final String TYPE = "ALE";

    private BeerDTO beerDTO;

    public BeerDTOBuilder() {
        beerDTO = new BeerDTO();
        beerDTO.setId(ID);
        beerDTO.setName(NAME);
        beerDTO.setQuantity(QUANTITY);
        beerDTO.setMax(MAX);
        beerDTO.setBrand(BRAND);
        beerDTO.setType(BeerType.valueOf(TYPE));
    }

    public static BeerDTOBuilder builder() {
        return new BeerDTOBuilder();
    }

    // 🔥 Nome que o teste espera
    public BeerDTO build() {
        return this.beerDTO;
    }
}
