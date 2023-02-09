package next.bankly.co.operations.service;

import next.bankly.co.operations.models.entity.Operation;

import java.util.List;
import java.util.UUID;

public interface OperationService {
    Operation save(Operation operation);

    List<Operation> findAllByWalletId(String walletId);

    List<Operation> findAllByType(String type);

    Operation findByUuid(UUID uuid);

    List<Operation> findAllByTypeAndWalletId(String type, String walletId);
}
