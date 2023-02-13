package next.bankly.co.operations.rest.required.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@ToString
public class WalletVo {

    private String uuid;
    private String balance;
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 30)
    private String name;

    @NotNull @NotEmpty @Size(min = 6, max = 30)
    private String holder;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createAtt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date updateAtt;
}
