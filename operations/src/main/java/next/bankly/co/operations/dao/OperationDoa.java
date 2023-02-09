package next.bankly.co.operations.dao;

import next.bankly.co.operations.models.TypeOperation;
import next.bankly.co.operations.models.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperationDoa extends JpaRepository<Operation, UUID> {
    List<Operation> findAllByWalletId(String walletId);
    List<Operation> findAllByType(TypeOperation type);
    Operation findByUuid(UUID uuid);
    List<Operation> findAllByTypeAndWalletId(TypeOperation type, String walletId);
}
