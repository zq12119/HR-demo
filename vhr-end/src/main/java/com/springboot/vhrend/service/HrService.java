package com.springboot.vhrend.service;

import com.springboot.vhrend.mapper.HrMapper;
import com.springboot.vhrend.model.Hr;
import com.springboot.vhrend.utils.HrUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class HrService implements UserDetailsService {
    @Resource
    private HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if(hr == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(), keywords);
    }

    public int updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    public int updateHrStatus(Hr hr) {
        return hrMapper.updateStatus(hr.getId(), hr.isEnabled());
    }

    public int deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }
}