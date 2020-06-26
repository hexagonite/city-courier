package pl.ug.citycourier.internal.location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ug.citycourier.internal.coordinate.CoordinatePair;
import pl.ug.citycourier.internal.coordinate.CoordinatePairDTO;
import pl.ug.citycourier.internal.coordinate.CoordinatePairRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @InjectMocks
    private LocationService testee;

    @Mock
    private LocationRepository locationRepository;
    @Mock
    private CoordinatePairRepository coordinatePairRepository;
    @Mock
    private LocationCreator locationCreator;

    @Mock
    private CoordinatePairDTO coordinatePairDTO;
    @Mock
    private CoordinatePair coordinatePair;
    @Mock
    private Location location;

    @BeforeEach
    public void init() {
        when(coordinatePairDTO.getLongitude()).thenReturn(10.0D);
        when(coordinatePairDTO.getLatitude()).thenReturn(10.0D);
    }

    @Test
    public void findOrCreateLocationByCoordinate_exists() {
        when(coordinatePairRepository.findByLatitudeEqualsAndLongitudeEquals(coordinatePairDTO.getLongitude(), coordinatePairDTO.getLatitude()))
                .thenReturn(Optional.of(coordinatePair));
        when(coordinatePair.getLocation()).thenReturn(location);

        Location result = testee.findOrCreateLocationByCoordinate(coordinatePairDTO);

        assertThat(result).isEqualTo(location);
        verify(coordinatePairRepository).findByLatitudeEqualsAndLongitudeEquals(anyDouble(), anyDouble());
    }

    @Test
    public void findOrCreateLocationByCoordinate_notExists() {
        when(coordinatePairRepository.findByLatitudeEqualsAndLongitudeEquals(coordinatePairDTO.getLongitude(), coordinatePairDTO.getLatitude()))
                .thenReturn(Optional.empty());
        when(locationCreator.createLocationFromCoordinatePair(coordinatePairDTO))
                .thenReturn(location);
        when(coordinatePairRepository.save(any())).thenReturn(coordinatePair);
        when(locationRepository.save(location)).thenReturn(location);

        Location result = testee.findOrCreateLocationByCoordinate(coordinatePairDTO);

        assertThat(result).isEqualTo(location);
        verify(coordinatePairRepository).findByLatitudeEqualsAndLongitudeEquals(anyDouble(), anyDouble());
        verify(locationCreator).createLocationFromCoordinatePair(coordinatePairDTO);
        verify(coordinatePairRepository).save(any());
        verify(locationRepository).save(location);
    }

}
