package next.bankly.co.operations.rest.required.facade;


import next.bankly.co.operations.exception.BadRequestException;
import next.bankly.co.operations.models.TypeOperation;
import next.bankly.co.operations.models.domain.ResponseObject;
import next.bankly.co.operations.models.entity.Operation;
import next.bankly.co.operations.rest.converter.OperationConverter;
import next.bankly.co.operations.rest.required.vo.OperationVo;
import next.bankly.co.operations.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.schema.Enums;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("${api.endpoint}/operation")
public class OperationController {
    @Autowired
    OperationService operationService;
    @Autowired
    OperationConverter operationConverter;


    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid OperationVo operation, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !TypeOperation.contains(operation.getTypeOperation())) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            if (!TypeOperation.contains(operation.getTypeOperation())) {
                errors.put("typeOperation", "Cannot deserialize value of type!!");
            }
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false, "Data not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            Operation operat = this.operationService.save(operationConverter.toBean(operation));
            ResponseObject<OperationVo> responseObject = new ResponseObject<>(true, "Operation save successfully", operationConverter.toVo(operat));
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (BadRequestException e) {
            ResponseObject<OperationVo> responseObject = new ResponseObject<>(false, e.getMessage(), operation);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/wallet/{id}")
    public ResponseEntity<ResponseObject<?>> findAllByWalletId(@PathVariable String walletId) {
        List<Operation> operationList = operationService.findAllByWalletId(walletId);
        List<OperationVo> lists = new ArrayList<>();
        operationList.forEach(operation -> lists.add(operationConverter.toVo(operation)));
        ResponseObject<List<OperationVo>> responseObject = new ResponseObject<>(true, "find All by wallet id", lists);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/type/{type}")
    public ResponseEntity<ResponseObject<?>> findAllByType(@PathVariable String type) {
        if (!TypeOperation.contains(type)) {
            return getResponseObjectResponseEntity();
        }
        List<Operation> operationList = operationService.findAllByType(type);
        List<OperationVo> lists = new ArrayList<>();
        operationList.forEach(operation -> lists.add(operationConverter.toVo(operation)));
        ResponseObject<List<OperationVo>> responseObject = new ResponseObject<>(true, "find all by type of operation", lists);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> findByUuid(@PathVariable UUID uuid) {
        Operation operat = operationService.findByUuid(uuid);
        ResponseObject<OperationVo> responseObject = new ResponseObject<>(true, "data by uuid", operationConverter.toVo(operat));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/find/type/wallet")
    public ResponseEntity<ResponseObject<?>> findAllByTypeAndWalletId(@RequestBody OperationVo operationVo) {
        if (!TypeOperation.contains(operationVo.getTypeOperation())) {
            return getResponseObjectResponseEntity();
        }
        List<Operation> operationList = operationService.findAllByTypeAndWalletId(operationVo.getTypeOperation(), operationVo.getWalletId());
        List<OperationVo> lists = new ArrayList<>();
        operationList.forEach(operation -> lists.add(operationConverter.toVo(operation)));
        ResponseObject<List<OperationVo>> responseObject = new ResponseObject<>(true, "data by type and wallet id", lists);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    private ResponseEntity<ResponseObject<?>> getResponseObjectResponseEntity() {
        Map<String, String> errors = new HashMap<>();
        errors.put("typeOperation", "Cannot deserialize value of type operation,!!");
        ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false, "Data not valid!!", errors);
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }
}
