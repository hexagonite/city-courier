package pl.ug.citycourier.internal.pack;

import pl.ug.citycourier.internal.delivery.Delivery;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "pack")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PackSize packSize;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public PackSize getPackSize() {
        return packSize;
    }

    public void setPackSize(PackSize packSize) {
        this.packSize = packSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
