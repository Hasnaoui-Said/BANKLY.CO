package co.bankly.wallet.rest.required.facade;

import co.bankly.wallet.models.domain.ResponseObject;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-operations-v1")
@RibbonClient(name = "microservice-operations-v1")
public interface OperationRestRequired {
    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/operation/wallet/{id}")
    ResponseEntity<ResponseObject<Page<?>>> findAllByWalletId(@PathVariable String id,
                                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                                             @RequestParam(name = "size", defaultValue = "5") int size);
}
