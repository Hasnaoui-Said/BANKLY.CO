package co.bankly.users.rest.converter;

import co.bankly.users.models.entity.User;
import co.bankly.users.rest.provided.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {
    public User toBean(UserVo vo) {
        User bean = new User();
        bean.setUsername(vo.getUsername());
        bean.setEmail(vo.getEmail());
        bean.setPassword(vo.getPassword());
        if (vo.getFirstName() != null)
            bean.setFirstName(vo.getFirstName());
        if (vo.getLastName() != null)
            bean.setLastName(vo.getLastName());
        return bean;
    }

    public UserVo toVo(User bean) {
        UserVo vo = new UserVo();
        if (bean == null)
            return null;
        vo.setUsername(bean.getUsername());
        vo.setEmail(bean.getEmail());
        vo.setPassword(bean.getPassword());
        vo.setFirstName(bean.getFirstName());
        vo.setLastName(bean.getLastName());
        vo.setCreateDate(bean.getCreateDate());
        vo.setUpdateDate(bean.getUpdateDate());
        vo.setUuid(bean.getUuid().toString());
        return vo;
    }
    public List<UserVo> toVos(List<User> beans) {
        List<UserVo> vos = new ArrayList<>();
        beans.forEach(user -> vos.add(this.toVo(user)));
        return vos;
    }
}
