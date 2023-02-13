package next.bankly.co.operations.rest.required.facade;

import next.bankly.co.operations.models.domain.ResponseObject;
import next.bankly.co.operations.rest.required.vo.WalletVo;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "microservice-wallet-v1")
@RibbonClient(name = "microservice-wallet-v1")
public interface WalletRequiredRest {

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/")
    ResponseEntity<ResponseObject<List<WalletVo>>> findAll();

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/uuid/{uuid}")
    ResponseEntity<ResponseObject<WalletVo>> findByUuid(@PathVariable String uuid);

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/name/{name}")
    ResponseEntity<ResponseObject<WalletVo>> findByName(@PathVariable String name);

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/holder/{holder}")
    ResponseEntity<ResponseObject<WalletVo>> findByHolder(@PathVariable String holder);

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/exists/name/{name}")
    ResponseEntity<ResponseObject<Boolean>> existsByName(@PathVariable String name);

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/exists/holder/{holder}")
    ResponseEntity<ResponseObject<Boolean>> existsByHolder(@PathVariable String holder);

    @RequestMapping(method = RequestMethod.GET, value = "${api.endpoint}/wallet/update/{uuid}/{balance}")
    ResponseEntity<ResponseObject<WalletVo>> updateBalance(@PathVariable String uuid, @PathVariable Double balance);
}
