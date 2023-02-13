package co.bankly.wallet.rest.required.facade;

import co.bankly.wallet.models.domain.ResponseObject;
import co.bankly.wallet.rest.required.vo.OperationVo;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "microservice-operations-v1")
@RibbonClient(name = "microservice-operations-v1")
public interface OperationRestRequired {
    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/operation/wallet/{id}")
    ResponseEntity<ResponseObject<List<OperationVo>>> findAllByWalletId(@PathVariable String id);
}
