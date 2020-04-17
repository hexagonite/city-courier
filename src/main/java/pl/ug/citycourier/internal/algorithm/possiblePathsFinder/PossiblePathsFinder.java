package pl.ug.citycourier.internal.algorithm.possiblePathsFinder;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.algorithm.dto.PathToDelivery;
import pl.ug.citycourier.internal.algorithm.dto.ShortestCourierPath;

public interface PossiblePathsFinder {

    ShortestCourierPath chooseShortestPossiblePath(CourierInAlgorithm courier, PathToDelivery pathToDeliver);
}
