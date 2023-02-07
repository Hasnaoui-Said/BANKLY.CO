package next.bankly.co.operations.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import next.bankly.co.operations.models.TypeOperation;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "operation")
@Data
@ToString
public class Operation implements Serializable {
    @Id
    private UUID uuid;
    @Enumerated(EnumType.STRING) @Valid
    private TypeOperation type;
    @NotEmpty @NotNull
    private String walletId;
    @NotNull
    @Min(value = 0)
    @Max(value = 2000)
    private Double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createDate;
}
