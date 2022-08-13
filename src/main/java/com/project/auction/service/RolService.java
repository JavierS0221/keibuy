package com.project.auction.service;

import com.project.auction.model.Rol;

import java.util.List;

public interface RolService {
    public List<Rol> listRoles();
    public void save(Rol rol);
    public void delete(Rol rol);
    public Rol getRol(Rol rol);
}
