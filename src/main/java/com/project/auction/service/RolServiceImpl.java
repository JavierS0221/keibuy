package com.project.auction.service;

import com.project.auction.repository.RolRepository;
import com.project.auction.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listRoles() {
        return (List<Rol>) rolRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

    @Override
    @Transactional
    public void delete(Rol rol) {
        rolRepository.delete(rol);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol getRol(Rol rol) {
        return rolRepository.findById(rol.getId()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol getRol(String name) {
        return rolRepository.findByName(name);
    }
}
