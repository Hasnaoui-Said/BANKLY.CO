package co.bankly.wallet.service;

import co.bankly.wallet.models.entity.Wallet;
import co.bankly.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WalletServiceImpl {
    @Autowired
    WalletRepository walletRepository;

    public Wallet findByUuid(UUID uuid) {
        return walletRepository.findByUuid(uuid);
    }

    public Wallet findByName(String name) {
        return walletRepository.findByName(name);
    }

    public Wallet findByHolder(String name) {
        return walletRepository.findByHolder(name);
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public Wallet insert(Wallet entity) {
        entity.setCreateAtt(new Date());
        entity.setUpdateAtt(new Date());
        entity.setUuid(UUID.randomUUID());
        return walletRepository.insert(entity);
    }

    public Boolean existsByName(String name) {
        return walletRepository.existsByName(name);
    }

    public Boolean existsByHolder(String older) {
        return walletRepository.existsByHolder(older);
    }
}
