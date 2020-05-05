package com.springboot.vhrend.service.system.basic;

import com.springboot.vhrend.mapper.HrRoleMapper;
import com.springboot.vhrend.mapper.MenuMapper;
import com.springboot.vhrend.mapper.RoleMapper;
import com.springboot.vhrend.model.Menu;
import com.springboot.vhrend.model.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    HrRoleMapper hrRoleMapper;
    @Resource
    MenuMapper menuMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }

    @Transactional
    public boolean updateRoles(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }
}