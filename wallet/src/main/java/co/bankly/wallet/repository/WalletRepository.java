package co.bankly.wallet.repository;

import co.bankly.wallet.models.entity.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByName(String name);
    Optional<Wallet> findByHolder(String name);
    Boolean existsByName(String name);
    Boolean existsByHolder(String older);
}
