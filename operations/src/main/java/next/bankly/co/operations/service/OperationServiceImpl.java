package next.bankly.co.operations.service;

import next.bankly.co.operations.dao.OperationDoa;
import next.bankly.co.operations.models.TypeOperation;
import next.bankly.co.operations.models.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OperationServiceImpl implements OperationService{
    @Autowired
    OperationDoa operationDao;

    @Override
    public Operation save(Operation operation) {
        // To do: check if wallet id exist
        // To do: valid operation if depot, withdrawingFunds, checkBalance, transfer
        operation.setCreateDate(new Date());
        operation.setUuid(UUID.randomUUID());
        return this.operationDao.save(operation);
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
