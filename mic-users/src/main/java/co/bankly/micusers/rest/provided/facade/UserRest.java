package co.bankly.micusers.rest.provided.facade;

import co.bankly.micusers.exception.BadRequestException;
import co.bankly.micusers.models.domain.ResponseObject;
import co.bankly.micusers.models.entity.User;
import co.bankly.micusers.rest.converter.UserConverter;
import co.bankly.micusers.rest.provided.vo.UserVo;
import co.bankly.micusers.rest.required.facade.WalletRestRequired;
import co.bankly.micusers.rest.required.vo.WalletVo;
import co.bankly.micusers.service.UserDetailsServiceImpl;
import co.bankly.micusers.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("${api.endpoint}/users")
public class UserRest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserConverter userConverter;

    @Autowired
    WalletService walletService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String hello() {
        return "micro service users works!!";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid UserVo user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "User not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            User userSave = userDetailsService.save(this.userConverter.toBean(user));
            ResponseObject<UserVo> responseObject = new ResponseObject<>(false,
                    "Error", this.userConverter.toVo(userSave));
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            ResponseObject<UserVo> responseObject = new ResponseObject<>(false,
                    e.getMessage(), user);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> findAll() {
        List<User> all = this.userDetailsService.findAll();
        ResponseObject<List<UserVo>> responseObject = new ResponseObject<>(false,
                "Find all!!", this.userConverter.toVos(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> findByUuid(@PathVariable String uuid) {
        User user = this.userDetailsService.findByUuid(UUID.fromString(uuid));
        ResponseObject<UserVo> responseObject = new ResponseObject<>(true,
                "Find by uuid user!!", this.userConverter.toVo(user));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/username/{username}")
    public ResponseEntity<ResponseObject<?>> findByUsername(@PathVariable String username) {
        User byUsername = this.userDetailsService.findByUsername(username).orElse(null);
        ResponseObject<UserVo> responseObject = new ResponseObject<>(true,
                "Find user by username!!", this.userConverter.toVo(byUsername));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}")
    public ResponseEntity<ResponseObject<?>> findByEmail(@PathVariable String email) {
        User user = this.userDetailsService.findByEmail(email);
        ResponseObject<UserVo> responseObject = new ResponseObject<>(true,
                "Find user by email!!", this.userConverter.toVo(user));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    // wallet
    @RequestMapping(method = RequestMethod.GET, value = "/wallet/sold/{uuid}")
    public ResponseEntity<ResponseObject<Double>> sold(@PathVariable String uuid) {
        return walletService.sold(uuid);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/wallet/")
    public ResponseEntity<ResponseObject<List<WalletVo>>> findALlWallet() {
        return walletService.findAll();
    }
}
