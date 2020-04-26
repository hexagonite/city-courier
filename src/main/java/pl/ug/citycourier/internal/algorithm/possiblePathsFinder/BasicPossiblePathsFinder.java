package pl.ug.citycourier.internal.algorithm.possiblePathsFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.ug.citycourier.internal.algorithm.dto.*;
import pl.ug.citycourier.internal.pathfinder.Pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static pl.ug.citycourier.internal.courier.CourierTaskType.DELIVER_PACK;
import static pl.ug.citycourier.internal.courier.CourierTaskType.PICK_UP_PACK;

@Component
public class BasicPossiblePathsFinder implements PossiblePathsFinder {

    private Pathfinder pathfinder;

    @Autowired
    public BasicPossiblePathsFinder(@Qualifier("linearPathfinder") Pathfinder pathfinder) {
        this.pathfinder = pathfinder;
    }

    // CS - courier start
    // AS - delivery A start
    // AF - delivery A finish
    // BS - delivery B start
    // BF - delivery B finish
    @Override
    public ShortestCourierPath chooseShortestPossiblePath(CourierInAlgorithm courier, PathToDelivery pathToDelivery) {
        List<Path> paths = new ArrayList<>();
        var AS = courier.getFirstDelivery().getDeliveryInAlgorithm().getStart();
        var AF = courier.getFirstDelivery().getDeliveryInAlgorithm().getDestination();
        var BS = pathToDelivery.getDeliveryInAlgorithm().getStart();
        var BF = pathToDelivery.getDeliveryInAlgorithm().getDestination();

        var CS_AS = courier.getFirstDelivery().getPath();
        var AS_AF = pathfinder.findShortestPath(AS, AF);
        var AF_BS = pathfinder.findShortestPath(AF, BS);
        var BS_BF = pathfinder.findShortestPath(BS, BF);
        var AS_BS = pathfinder.findShortestPath(AS, BS);
        var BF_AF = pathfinder.findShortestPath(BF, AF);
        var BS_AF = pathfinder.findShortestPath(BS, AF);
        var AF_BF = pathfinder.findShortestPath(AF, BF);
        var CS_BS = pathToDelivery.getPath();
        var BF_AS = pathfinder.findShortestPath(BF, AS);
        var BS_AS = pathfinder.findShortestPath(BS, AS);
        var AS_BF = pathfinder.findShortestPath(AS, BF);

        paths.add(new Path(CS_AS, AS_AF, AF_BS, BS_BF));
        paths.add(new Path(CS_AS, AS_BS, BS_BF, BF_AF));
        paths.add(new Path(CS_AS, AS_BS, BS_AF, AF_BF));
        paths.add(new Path(CS_BS, BS_BF, BF_AS, AS_AF));
        paths.add(new Path(CS_BS, BS_AS, AS_AF, AF_BF));
        paths.add(new Path(CS_BS, BS_AS, AS_BF, BF_AF));

        int minIndex = IntStream.range(0, paths.size()).boxed()
                .min(comparing(paths::get))
                .get();

        List<LocationWithType> locationsWithType = new ArrayList<>();
        Collections.addAll(locationsWithType,
                new LocationWithType(AS, PICK_UP_PACK),
                new LocationWithType(AF, DELIVER_PACK),
                new LocationWithType(BS, PICK_UP_PACK),
                new LocationWithType(BF, DELIVER_PACK));

        return new ShortestCourierPath(
                new PathToDelivery(paths.get(minIndex), pathToDelivery.getDeliveryInAlgorithm()),
                locationsWithType,
                minIndex);
    }
}
