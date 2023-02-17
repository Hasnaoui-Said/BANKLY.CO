package co.bankly.wallet.rest.provided.facade;

import co.bankly.wallet.exception.BadRequestException;
import co.bankly.wallet.models.domain.ResponseObject;
import co.bankly.wallet.models.entity.Wallet;
import co.bankly.wallet.rest.provided.converter.WalletConverter;
import co.bankly.wallet.rest.provided.vo.WalletVo;
import co.bankly.wallet.rest.required.vo.OperationVo;
import co.bankly.wallet.service.WalletServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("${api.endpoint}/wallet")
@CrossOrigin(origins = "http://localhost:9090")
public class WalletController {

    @Autowired
    WalletServiceImpl walletService;
    @Autowired
    WalletConverter walletConverter;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid WalletVo wallet, BindingResult bindingResult) {
        ResponseEntity<ResponseObject<?>> resp = isNotValid(bindingResult);
        if (resp != null) return resp;
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

    @RequestMapping(method = RequestMethod.GET, value = "/update/{uuid}/{balance}")
    ResponseEntity<ResponseObject<?>> updateBalance(@PathVariable String uuid, @PathVariable Double balance) {
        try {
            Wallet walletSave = this.walletService.updateBalance(uuid, balance);
            ResponseObject<WalletVo> responseObject = new ResponseObject<>(true,
                    "Wallet updated successfully !!", this.walletConverter.toVo(walletSave));
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (BadRequestException e) {
            ResponseObject<String> responseObject = new ResponseObject<>(false,
                    e.getMessage(), uuid);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return notValid("uuid", e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<List<WalletVo>>> findAll() {
        List<Wallet> all = this.walletService.findAll();
        ResponseObject<List<WalletVo>> responseObject = new ResponseObject<>(true,
                "Find all!", this.walletConverter.toVos(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/operations/{idWallet}")
    public ResponseEntity<ResponseObject<?>> operations(@PathVariable String idWallet) {
        try {
            List<OperationVo> all = this.walletService.findAllOperations(idWallet);
            ResponseObject<List<OperationVo>> responseObject = new ResponseObject<>(true,
                    "Find all operation by id wallet!", all);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        }catch (BadRequestException e){
            ResponseObject<?> responseObject = new ResponseObject<>(false,
                    e.getMessage(), null);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> findByUuid(@PathVariable String uuid) {
        Wallet byUuid = this.walletService.findByUuid(uuid);
        ResponseObject<WalletVo> responseObject = new ResponseObject<>(true,
                "Wallet By Uuid!", this.walletConverter.toVo(byUuid));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

    private ResponseEntity<ResponseObject<?>> notValid(String key, IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put(key, e.getMessage());
        ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                "Data not valid!!", errors);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseObject<?>> isNotValid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "Data not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
    public ResponseEntity<ResponseObject<WalletVo>> findByName(@PathVariable String name) {
        Wallet byName = this.walletService.findByName(name);
        ResponseObject<WalletVo> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", this.walletConverter.toVo(byName));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/holder/{holder}")
    public ResponseEntity<ResponseObject<WalletVo>> findByHolder(@PathVariable String holder) {
        Wallet all = this.walletService.findByHolder(holder);
        ResponseObject<WalletVo> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", this.walletConverter.toVo(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exists/name/{name}")
    public ResponseEntity<ResponseObject<Boolean>> existsByName(@PathVariable String name) {
        ResponseObject<Boolean> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", walletService.existsByName(name));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exists/holder/{holder}")
    public ResponseEntity<ResponseObject<Boolean>> existsByHolder(@PathVariable String holder) {
        ResponseObject<Boolean> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", walletService.existsByHolder(holder));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sold/{uuid}")
    ResponseEntity<ResponseObject<Double>> sold(@PathVariable String uuid) {
        try {
            Wallet wallet = walletService.findByUuid(uuid);
            String message = "Check sold " + uuid;
            boolean success = true;
            HttpStatus httpStatus = HttpStatus.OK;
            ResponseObject<Double> responseObject = new ResponseObject<>(success,
                    message, wallet.getBalance());
            return new ResponseEntity<>(responseObject, httpStatus);

        } catch (NoSuchElementException e) {
            ResponseObject<Double> responseObject = new ResponseObject<>(false,
                    e.getMessage(), null);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }
}
