package com.project.auction.service;

import com.project.auction.dao.RolDao;
import com.project.auction.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolDao rolDao;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listRoles() {
        return (List<Rol>) rolDao.findAll();
    }

    @Override
    @Transactional
    public void save(Rol rol) {
        rolDao.save(rol);
    }

    @Override
    @Transactional
    public void delete(Rol rol) {
        rolDao.delete(rol);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol getRol(Rol rol) {
        return rolDao.findById(rol.getId()).orElse(null);
    }
}
