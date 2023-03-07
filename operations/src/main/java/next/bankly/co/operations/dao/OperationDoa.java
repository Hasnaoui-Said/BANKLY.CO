package next.bankly.co.operations.dao;

import next.bankly.co.operations.models.TypeOperation;
import next.bankly.co.operations.models.entity.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface OperationDoa extends JpaRepository<Operation, UUID> {
    Page<Operation> findAllByWalletIdOrderByCreateDateDesc(String walletId, PageRequest pageRequest);
    List<Operation> findAllByType(TypeOperation type);
    Operation findByUuid(UUID uuid);
    List<Operation> findAllByTypeAndWalletId(TypeOperation type, String walletId);
}
