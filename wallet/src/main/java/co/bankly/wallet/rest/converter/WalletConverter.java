package co.bankly.wallet.rest.converter;

import co.bankly.wallet.models.entity.Wallet;
import co.bankly.wallet.rest.vo.WalletVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WalletConverter {
    public Wallet toBean(WalletVo vo){
        Wallet bean = new Wallet();
        bean.setName(vo.getName());
        bean.setHolder(vo.getHolder());
        if (vo.getBalance() != null)
            bean.setBalance(Double.parseDouble(vo.getBalance()));
        return bean;
    }
    public WalletVo toVo(Wallet bean){
        WalletVo vo = new WalletVo();
        vo.setName(bean.getName());
        vo.setHolder(bean.getHolder());
        vo.setUuid(bean.getUuid());
        vo.setBalance(bean.getBalance().toString());
        vo.setCreateAtt(bean.getCreateAtt());
        return vo;
    }

    public List<WalletVo> toVos(List<Wallet> all) {
        List<WalletVo> vos = new ArrayList<>();
        all.forEach(wallet -> vos.add(this.toVo(wallet)));
        return vos;
    }
}
