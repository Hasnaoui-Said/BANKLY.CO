package co.bankly.wallet.rest.required.facade;

import co.bankly.wallet.exception.BadRequestException;
import co.bankly.wallet.models.domain.ResponseObject;
import co.bankly.wallet.models.entity.Wallet;
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

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid Wallet wallet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "User not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            Wallet walletSave = this.walletService.insert(wallet);
            ResponseObject<Wallet> responseObject = new ResponseObject<>(false,
                    "Error", walletSave);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (BadRequestException e) {
            ResponseObject<Wallet> responseObject = new ResponseObject<>(false,
                    e.getMessage(), wallet);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> findAll() {
        ResponseObject<List<Wallet>> responseObject = new ResponseObject<>(false,
                "User not valid!!", this.walletService.findAll());
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

    public ResponseEntity<ResponseObject<?>> findByUuid(UUID uuid) {
        ResponseObject<Wallet> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", this.walletService.findByUuid(uuid));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
    public ResponseEntity<ResponseObject<?>> findByName(@PathVariable String name) {
        ResponseObject<Wallet> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", this.walletService.findByName(name));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/holder/{holder}")
    public ResponseEntity<ResponseObject<?>> findByHolder(@PathVariable String holder) {
        ResponseObject<List<Wallet>> responseObject = new ResponseObject<>(true,
                "Wallet By Holder!", this.walletService.findAll());
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
