package next.bankly.co.operations.service;

import next.bankly.co.operations.models.entity.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface OperationService {
    Operation save(Operation operation);

    Page<Operation> findAllByWalletId(String walletId, PageRequest pageRequest);

    List<Operation> findAllByType(String type);

    Operation findByUuid(UUID uuid);

    List<Operation> findAllByTypeAndWalletId(String type, String walletId);
}
