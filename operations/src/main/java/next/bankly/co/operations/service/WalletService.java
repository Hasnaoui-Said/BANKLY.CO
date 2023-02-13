package next.bankly.co.operations.service;

import next.bankly.co.operations.models.domain.ResponseObject;
import next.bankly.co.operations.rest.required.facade.WalletRequiredRest;
import next.bankly.co.operations.rest.required.vo.WalletVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    WalletRequiredRest walletRequiredRest;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<List<WalletVo>>> findAll() {
        return walletRequiredRest.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<WalletVo>> findByUuid(UUID uuid) {
        return walletRequiredRest.findByUuid(uuid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
    public ResponseEntity<ResponseObject<WalletVo>> findByName(String name) {
        return walletRequiredRest.findByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/holder/{holder}")
    public ResponseEntity<ResponseObject<WalletVo>> findByHolder(String holder) {
        return walletRequiredRest.findByHolder(holder);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exists/name/{name}")
    public ResponseEntity<ResponseObject<Boolean>> existsByName(String name) {
        return walletRequiredRest.existsByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exists/holder/{holder}")
    public ResponseEntity<ResponseObject<Boolean>> existsByHolder(String holder) {
        return walletRequiredRest.existsByHolder(holder);
    }
}
