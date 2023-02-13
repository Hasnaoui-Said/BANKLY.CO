package next.bankly.co.operations.service;

import next.bankly.co.operations.dao.OperationDoa;
import next.bankly.co.operations.exception.BadRequestException;
import next.bankly.co.operations.models.TypeOperation;
import next.bankly.co.operations.models.domain.ResponseObject;
import next.bankly.co.operations.models.entity.Operation;
import next.bankly.co.operations.rest.required.facade.WalletRequiredRest;
import next.bankly.co.operations.rest.required.vo.WalletVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    OperationDoa operationDao;

    @Autowired
    WalletRequiredRest walletRequiredRest;

    @Override
    public Operation save(Operation operation) {
        // To do: check if wallet id exist
        ResponseObject<WalletVo> walletVoResponseObject;
        try {
            walletVoResponseObject = this.walletRequiredRest.findByUuid(operation.getWalletId()).getBody();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        System.out.println("-->");
        System.out.println(walletVoResponseObject);
        if (!walletVoResponseObject.isSuccess() || walletVoResponseObject.getData() == null)
            throw new BadRequestException("No Data found with this UUID");

        WalletVo wallet = walletVoResponseObject.getData();
        // To do: valid operation if depot, withdrawingFunds, transfer
        Double balance;
        ResponseObject<WalletVo> walletUpdate = new ResponseObject<>();
        if (operation.getType().equals(TypeOperation.depot)) {
            balance = Double.parseDouble(wallet.getBalance()) + operation.getAmount();
            System.out.println("balance " + balance);
            walletUpdate = this.walletRequiredRest.updateBalance(wallet.getUuid(), balance).getBody();
        } else if (operation.getType().equals(TypeOperation.withdrawingFunds)) {
            balance = Double.parseDouble(wallet.getBalance()) - operation.getAmount();
            if (balance < 0)
                throw new BadRequestException("Error: Insufficient balance.");
            walletUpdate = this.walletRequiredRest.updateBalance(wallet.getUuid(), balance).getBody();
        }else
            throw new BadRequestException("Error: Not implement yet.");

        if (!walletUpdate.isSuccess())
            throw new BadRequestException(walletUpdate.getMessage());

        operation.setCreateDate(new Date());
        operation.setUuid(UUID.randomUUID());
        Operation operation1 = this.operationDao.save(operation);

        return operation1;
    }

    @Override
    public List<Operation> findAllByWalletId(String walletId) {
        return operationDao.findAllByWalletId(walletId);
    }

    @Override
    public List<Operation> findAllByType(String type) {
        return operationDao.findAllByType(TypeOperation.valueOf(type));
    }

    @Override
    public Operation findByUuid(UUID uuid) {
        return operationDao.findByUuid(uuid);
    }

    @Override
    public List<Operation> findAllByTypeAndWalletId(String type, String walletId) {
        return operationDao.findAllByTypeAndWalletId(TypeOperation.valueOf(type), walletId);
    }
}
