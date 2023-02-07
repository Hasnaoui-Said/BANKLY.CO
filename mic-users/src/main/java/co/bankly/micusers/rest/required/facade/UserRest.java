package co.bankly.micusers.rest.required.facade;

import co.bankly.micusers.exception.BadRequestException;
import co.bankly.micusers.models.domain.ResponseObject;
import co.bankly.micusers.models.entity.User;
import co.bankly.micusers.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint}/users")
public class UserRest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String hello() {
        return "micro service users works!!";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "User not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            User userSave = userDetailsService.save(user);
            ResponseObject<User> responseObject = new ResponseObject<>(false,
                    "Error", userSave);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (BadRequestException e) {
            ResponseObject<User> responseObject = new ResponseObject<>(false,
                    e.getMessage(), user);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> findAll() {
        ResponseObject<List<User>> responseObject = new ResponseObject<>(false,
                "User not valid!!", this.userDetailsService.findAll());
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> findByUuid(@PathVariable String uuid) {
        User user = this.userDetailsService.findByUuid(UUID.fromString(uuid));
        ResponseObject<User> responseObject = new ResponseObject<>(true,
                "Find by uuid user!!", user);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/username/{username}")
    public ResponseEntity<ResponseObject<?>> findByUsername(@PathVariable String username) {
        User byUsername = this.userDetailsService.findByUsername(username);
        ResponseObject<User> responseObject = new ResponseObject<>(true,
                "Find user by username!!", byUsername);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}")
    public ResponseEntity<ResponseObject<?>> findByEmail(@PathVariable String email) {
        User byUsername = this.userDetailsService.findByEmail(email);
        ResponseObject<User> responseObject = new ResponseObject<>(true,
                "Find user by email!!", byUsername);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

}
