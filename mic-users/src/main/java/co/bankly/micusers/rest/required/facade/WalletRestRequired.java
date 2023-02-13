package co.bankly.micusers.rest.required.facade;

import co.bankly.micusers.models.domain.ResponseObject;
import co.bankly.micusers.rest.required.vo.WalletVo;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "microservice-wallet-v1")
@RibbonClient(name = "microservice-wallet-v1")
public interface WalletRestRequired {
    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet")
    ResponseEntity<ResponseObject<List<WalletVo>>> findAll();

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/sold/{uuid}")
    ResponseEntity<ResponseObject<Double>> sold(@PathVariable String uuid);

}
