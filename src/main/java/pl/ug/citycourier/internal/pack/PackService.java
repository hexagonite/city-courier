package pl.ug.citycourier.internal.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackService {

    private PackRepository packRepository;

    @Autowired
    public PackService(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    public Pack createPackFromDTO(PackDTO packDTO) {
        Pack newPack = new Pack();
        newPack.setPackSize(packDTO.getPackSize());
        newPack.setDescription(packDTO.getDescription());
        return packRepository.save(newPack);
    }

}
