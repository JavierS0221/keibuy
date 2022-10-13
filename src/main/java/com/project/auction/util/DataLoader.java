package com.project.auction.util;

import com.project.auction.model.Rol;
import com.project.auction.repository.RolRepository;
import com.project.auction.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final RolService rolService;

    @Autowired
    public DataLoader(RolService rolService) {
        this.rolService = rolService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        this.initRoles();
    }

    private void initRoles() {

        if (rolService == null)
            return;
        try {
            Yaml yaml = new Yaml();
            File file = null;

            file = ResourceUtils.getFile("classpath:roles.yml");


            InputStream inputStream = new FileInputStream(file);
            Map<String, Map<String, Object>> obj = yaml.load(inputStream);

            for (String key : obj.keySet()) {
                Rol rol = rolService.getRol(key);
                if (rol == null) {
                    rol = new Rol();
                    rol.setName(key);
                }
                int priority = (int) obj.get(key).get("priority");
                boolean hasExpire = (boolean) obj.get(key).get("has-expire");

                rol.setPriority(priority);
                rol.setHasExpire(hasExpire);
                rolService.save(rol);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}