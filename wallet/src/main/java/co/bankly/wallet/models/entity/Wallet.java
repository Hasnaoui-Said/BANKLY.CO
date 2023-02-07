package co.bankly.wallet.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Document("wallet")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    private UUID uuid;
    private Double balance = 0D;
    @NotNull @NotEmpty @Size(min = 6, max = 30)
    private String name;

    @NotNull @NotEmpty @Size(min = 6, max = 30)
    private String holder;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date CreateAtt;
}
