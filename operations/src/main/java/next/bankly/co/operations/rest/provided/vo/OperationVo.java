package next.bankly.co.operations.rest.provided.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@ToString
public class OperationVo {
    @NotEmpty
    @NotNull
    private String typeOperation;
    private String uuid;
    @NotEmpty
    @NotNull
    private String walletId;

    @NotNull @NotEmpty
    private String amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createDate;
}
