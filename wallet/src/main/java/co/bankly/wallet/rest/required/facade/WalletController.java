package co.bankly.wallet.rest.required.facade;

import co.bankly.wallet.exception.BadRequestException;
import co.bankly.wallet.models.domain.ResponseObject;
import co.bankly.wallet.models.entity.Wallet;
import co.bankly.wallet.rest.converter.WalletConverter;
import co.bankly.wallet.rest.vo.WalletVo;
import co.bankly.wallet.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.endpoint}/wallet")
public class WalletController {

    @Autowired
    WalletServiceImpl walletService;
    @Autowired
    WalletConverter walletConverter;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid WalletVo wallet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "User not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            Wallet walletSave = this.walletService.insert(this.walletConverter.toBean(wallet));
            ResponseObject<WalletVo> responseObject = new ResponseObject<>(true,
                    "Wallet saved successfully !!", this.walletConverter.toVo(walletSave));
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (BadRequestException e) {
            ResponseObject<WalletVo> responseObject = new ResponseObject<>(false,
                    e.getMessage(), wallet);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> findAll() {
        List<Wallet> all = this.walletService.findAll();
        ResponseObject<List<WalletVo>> responseObject = new ResponseObject<>(false,
                "Find all!", this.walletConverter.toVos(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> findByUuid(@PathVariable UUID uuid) {
        Wallet byUuid = this.walletService.findByUuid(uuid);
        ResponseObject<WalletVo> responseObject = new ResponseObject<>(true,
                "Wallet By Uuid!", this.walletConverter.toVo(byUuid));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
    public ResponseEntity<ResponseObject<?>> findByName(@PathVariable String name) {
        Wallet byName = this.walletService.findByName(name);
        ResponseObject<WalletVo> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", this.walletConverter.toVo(byName));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/holder/{holder}")
    public ResponseEntity<ResponseObject<?>> findByHolder(@PathVariable String holder) {
        List<Wallet> all = this.walletService.findAll();
        ResponseObject<List<WalletVo>> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", this.walletConverter.toVos(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exists/name/{name}")
    public ResponseEntity<?> existsByName(@PathVariable String name) {
        ResponseObject<Boolean> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", walletService.existsByName(name));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exists/holder/{holder}")
    public ResponseEntity<ResponseObject<?>> existsByHolder(@PathVariable String holder) {
        ResponseObject<Boolean> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", walletService.existsByHolder(holder));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
