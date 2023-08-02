package com.lib.service.impl;

import com.lib.bean.Role;
import com.lib.dao.RoleDao;
import com.lib.dao.impl.RoleDaoImpl;
import com.lib.service.RoleService;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class RoleServiceImpl implements RoleService{
  private final RoleDao roleDao;

  public RoleServiceImpl(BasicDataSource bds){
    roleDao = new RoleDaoImpl(bds);
  }

  @Override
  public boolean removeRole(int roleId){
    return roleId < 0 ? false : roleDao.removeRole(roleId);
  }

  @Override
  public boolean updateRole(Role role){
    return role == null ? false : roleDao.updateRole(role);
  }

  @Override
  public boolean addRole(Role role){
    return role == null ? false : roleDao.addRole(role);
  }

  @Override
  public Role getRole(int roleId, boolean active){
    return roleId < 0 ? null : roleDao.getRole(roleId, active);
  }

  @Override
  public List<Role> getRoles(boolean active){
    return roleDao.getRoles(active);
  }
}
