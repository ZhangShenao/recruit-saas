package recruit.saas.auth.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recruit.saas.auth.service.entity.Users;
import recruit.saas.auth.service.mapper.UsersMapper;
import recruit.saas.auth.service.service.UsersService;
import recruit.saas.common.constants.UsersConstants;
import recruit.saas.common.enums.Sex;
import recruit.saas.common.enums.UsersRole;
import recruit.saas.common.enums.UsersShowWhichName;
import recruit.saas.common.utils.DateTimeUtils;
import recruit.saas.common.utils.DesensitizationUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/2/22 2:59 PM
 * Description 用户服务实现类
 */
@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    public Optional<Users> queryByMobile(String mobile) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getMobile, mobile);
        return Optional.ofNullable(usersMapper.selectOne(wrapper));
    }

    @Override
    @Transactional  //DB写操作,开启事务
    public Users createByMobile(String mobile) {
        //创建默认用户信息
        Users entity = new Users();
        entity.setMobile(mobile);
        String desensitizationMobile = DesensitizationUtils.commonDisplay(mobile);
        String name = String.format(UsersConstants.DEFAULT_USER_NAME_FORMAT, desensitizationMobile); //生成默认用户名
        entity.setNickname(name);
        entity.setRealName(name);
        entity.setShowWhichName(UsersShowWhichName.NICKNAME.getCode()); //默认展示昵称
        entity.setSex(Sex.SECRET.getCode()); //性别默认保密
        entity.setFace(UsersConstants.DEFAULT_AVATAR);  //设置默认头像
        entity.setEmail("");    //邮箱默认为空
        LocalDate birthday = DateTimeUtils.parseLocalDate("1900-01-01", DateTimeUtils.DATE_PATTERN);
        entity.setBirthday(birthday);   //默认生日
        entity.setCountry(UsersConstants.DEFAULT_COUNTRY);
        entity.setProvince(UsersConstants.DEFAULT_PROVINCE);
        entity.setCity(UsersConstants.DEFAULT_CITY);
        entity.setDistrict(UsersConstants.DEFAULT_DISTRICT);
        entity.setDescription(UsersConstants.DEFAULT_DESCRIPTION);
        entity.setStartWorkDate(LocalDate.now());   //默认参加工作时间为当天
        entity.setPosition(UsersConstants.DEFAULT_POSITION);
        entity.setRole(UsersRole.CANDIDATE.getCode());  //默认角色为求职者
        entity.setHrInWhichCompanyId("");   //绑定公司默认为空
        entity.setHrSignature("");  //HR签名默认为空
        entity.setHrTags("");   //HR标签默认为空
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedTime(now);
        entity.setUpdatedTime(now);

        //创建用户并返回
        usersMapper.insert(entity);
        return entity;
    }
}
