package com.lib.service;

import com.lib.bean.Role;
import java.util.List;

/**
 *
 * @author user
 */
public interface RoleService{
  boolean removeRole(int roleId);

  boolean updateRole(Role role);

  boolean addRole(Role role);

  Role getRole(int roleId, boolean active);

  List<Role> getRoles(boolean active);
}
