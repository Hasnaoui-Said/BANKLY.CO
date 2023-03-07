package co.bankly.wallet.service;

import co.bankly.wallet.exception.BadRequestException;
import co.bankly.wallet.models.domain.ResponseObject;
import co.bankly.wallet.models.entity.Wallet;
import co.bankly.wallet.repository.WalletRepository;
import co.bankly.wallet.rest.required.facade.OperationRestRequired;
import co.bankly.wallet.rest.required.vo.OperationVo;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class WalletServiceImpl {
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    OperationRestRequired operationRestRequired;

    public Wallet findByUuid(String uuid) {
        return walletRepository.findById(uuid).orElseThrow(()-> {
            throw new BadRequestException(String.format("wallet with this parameter uuid: %s not found!!",uuid));
        });
    }

    public Wallet findByName(String name) {
        return walletRepository.findByName(name).orElse(null);
    }

    public Wallet findByHolder(String name) {
        return walletRepository.findByHolder(name).orElse(null);
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public Wallet insert(Wallet entity) {
        entity.setCreateAtt(new Date());
        entity.setUpdateAtt(new Date());
        return walletRepository.insert(entity);
    }

    public Boolean existsByName(String name) {
        return walletRepository.existsByName(name);
    }

    public Boolean existsByHolder(String older) {
        return walletRepository.existsByHolder(older);
    }

    public Wallet updateBalance(String uuid, Double balance) {
        Optional<Wallet> wallet = walletRepository.findById(uuid);
        if (!wallet.isPresent())
            throw new IllegalArgumentException("Wallet not found with uuid: " + uuid);

        Wallet walletExisting = wallet.get();
        walletExisting.setBalance(balance);
        walletExisting.setUpdateAtt(new Date());
        return walletRepository.save(walletExisting);
    }

    public Wallet update2(String uuid, Double balance) {
        Wallet wallet = this.findByUuid(uuid);
        if (wallet == null)
            throw new BadRequestException("Wallet with this parameter uuid " + uuid + "not found");
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDbFactory(MongoClients.create(), "wallet"));
        mongoOps.updateFirst(new Query(where("uuid").is(uuid)), new Update().inc("balance", balance), Wallet.class);
        // Wallet wlt = mongoOps.findOne(query(where("name").is("test")), Wallet.class);
        return wallet;
    }

    public Page<?> findAllOperations(String idWallet, int page, int size) {
        this.findByUuid(idWallet); // throw exception if null
        ResponseEntity<ResponseObject<Page<?>>> responseEntity = this.operationRestRequired.findAllByWalletId(idWallet, page, size);
        if (responseEntity.getBody() == null)
            throw new BadRequestException("Some error clocked");
        return responseEntity.getBody().getData();
    }
}
