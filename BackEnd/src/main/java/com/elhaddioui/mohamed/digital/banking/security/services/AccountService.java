package com.elhaddioui.mohamed.digital.banking.security.services;

import com.elhaddioui.mohamed.digital.banking.security.entities.AppRole;
import com.elhaddioui.mohamed.digital.banking.security.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);

    void deleteUser(Long id);

    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String rolename);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();

    AppUser update(AppUser appUser);
}
