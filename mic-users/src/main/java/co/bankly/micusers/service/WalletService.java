package co.bankly.micusers.service;

import co.bankly.micusers.models.domain.ResponseObject;
import co.bankly.micusers.rest.required.facade.WalletRestRequired;
import co.bankly.micusers.rest.required.vo.WalletVo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<ResponseObject<?>> findByUuid(String uuid) {
        return walletRestRequired.findByUuid(uuid);
    }

    public ResponseEntity<ResponseObject<WalletVo>> findByName(String name) {
        return walletRestRequired.findByName(name);
    }

    public ResponseEntity<ResponseObject<WalletVo>> findByHolder(String holder) {
        return walletRestRequired.findByHolder(holder);
    }

    public ResponseEntity<ResponseObject<Boolean>> existsByName(String name) {
        return walletRestRequired.existsByName(name);
    }

    public ResponseEntity<ResponseObject<Boolean>> existsByHolder(String holder) {
        return walletRestRequired.existsByHolder(holder);
    }
}
