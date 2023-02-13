package co.bankly.micusers.service;

import co.bankly.micusers.models.domain.ResponseObject;
import co.bankly.micusers.rest.required.facade.WalletRestRequired;
import co.bankly.micusers.rest.required.vo.WalletVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    WalletRestRequired walletRestRequired;

    public ResponseEntity<ResponseObject<List<WalletVo>>> findAll() {
        return walletRestRequired.findAll();
    }

    public ResponseEntity<ResponseObject<Double>> sold(String uuid) {
        try {
            ResponseEntity<ResponseObject<Double>> response = walletRestRequired.sold(uuid);
            return response;
        }catch (Exception e){
            ResponseObject<Double> body = new ResponseObject<>();
            body.setData(null);
            body.setMessage("No Content found, 'No value of wallet with this parameter {uuid = "+uuid+"} present.'");
            body.setSuccess(false);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }
}
