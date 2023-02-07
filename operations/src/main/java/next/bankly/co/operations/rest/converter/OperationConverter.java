package next.bankly.co.operations.rest.converter;

import next.bankly.co.operations.models.TypeOperation;
import next.bankly.co.operations.models.entity.Operation;
import next.bankly.co.operations.rest.required.vo.OperationVo;
import org.springframework.stereotype.Component;

@Component
public class OperationConverter {

    public Operation toBean(OperationVo vo){
        Operation bean = new Operation();
        bean.setType(TypeOperation.valueOf(vo.getTypeOperation()));
        bean.setAmount(Double.valueOf(vo.getAmount()));
        bean.setWalletId(vo.getWalletId());
        return bean;
    }
    public OperationVo toVo(Operation bean){
        OperationVo vo = new OperationVo();
        vo.setTypeOperation(bean.getType().toString());
        vo.setAmount(bean.getAmount().toString());
        vo.setWalletId(bean.getWalletId());
        vo.setUuid(bean.getUuid().toString());
        vo.setCreateDate(bean.getCreateDate());
        return vo;
    }
}
