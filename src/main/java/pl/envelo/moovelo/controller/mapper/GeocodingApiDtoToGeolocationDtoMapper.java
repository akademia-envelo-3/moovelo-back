package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.location.LocationDto;
import pl.envelo.moovelo.controller.dto.location.geocoding.AddressComponent;
import pl.envelo.moovelo.controller.dto.location.geocoding.GeocodingApiDto;
import pl.envelo.moovelo.enums.AddressComponentTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GeocodingApiDtoToGeolocationDtoMapper {

    public static LocationDto map(GeocodingApiDto geocodingApiDto) {
        List<AddressComponent> addressComponents = geocodingApiDto.getResults().get(0).getAddressComponents();
        Map<String, String> addressComponentsMap = convertListToMap(addressComponents);

        return LocationDto.builder()
                .altitude(geocodingApiDto.getResults().get(0).getGeometry().getLocation().getLng())
                .latitude(geocodingApiDto.getResults().get(0).getGeometry().getLocation().getLat())
                .street(addressComponentsMap.get(getDescriptionForEnum(AddressComponentTypes.STREET_NAME)))
                .streetNumber(String.valueOf(addressComponentsMap.get(getDescriptionForEnum(AddressComponentTypes.STREET_NUMBER))))
                .postCode(addressComponentsMap.get(getDescriptionForEnum(AddressComponentTypes.POSTAL_CODE)))
                .city(addressComponentsMap.get(getDescriptionForEnum(AddressComponentTypes.CITY)))
                .build();
    }

    private static String getDescriptionForEnum(AddressComponentTypes componentType) {
        return componentType.getDescription().toLowerCase(Locale.ROOT);
    }

    private static Map<String, String> convertListToMap(List<AddressComponent> addressComponents) {
        Map<String, String> convertedMap = new HashMap<>();
        for (int i = 0; i < addressComponents.size(); i++) {
            convertedMap.put(createKeyFromTypesField(addressComponents, i),
                    createValueFromLongNameField(addressComponents, i));
        }
        return convertedMap;
    }

    private static String createKeyFromTypesField(List<AddressComponent> addressComponents, int i) {
        int firstElementFromTypesArray = 0;
        return addressComponents.get(i).getTypes().get(firstElementFromTypesArray);
    }

    private static String createValueFromLongNameField(List<AddressComponent> addressComponents, int i) {
        return addressComponents.get(i).getLongName();
    }
}

