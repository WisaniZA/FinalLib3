package com.lib.dao;

import com.lib.bean.Role;
import java.util.List;

/**
 *
 * @author user
 */
public interface RoleDao{
  boolean removeRole(int roleId);

  boolean updateRole(Role role);

  boolean addRole(Role role);

  Role getRole(int roleId, boolean active);

  List<Role> getRoles(boolean active);

  boolean setRole(int roleId, boolean active);
}
