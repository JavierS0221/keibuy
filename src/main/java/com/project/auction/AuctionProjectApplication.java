package com.project.auction;

import com.project.auction.model.Rol;
import com.project.auction.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class AuctionProjectApplication{

	private static RolService rolService;

	@Autowired
	public AuctionProjectApplication(RolService rolService) {
		AuctionProjectApplication.rolService = rolService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuctionProjectApplication.class, args);


		try {
			// Adding and updating roles in db that are in the yml file
			Yaml yaml = new Yaml();
			File file = ResourceUtils.getFile("classpath:roles.yml");
			InputStream in = new FileInputStream(file);
			Map<String, Map<String, Object>> obj = yaml.load(in);
			List<String> listRolesInFile = new ArrayList<String>();
			for(String x : obj.keySet()) {

				Rol rol = rolService.getRol(x);
				if(rol == null) rol = new Rol();

				rol.setName(x);
				rol.setPriority((Integer) obj.get(x).get("priority"));
				rol.setHasExpire((Boolean) obj.get(x).get("hasExpire"));
				rolService.save(rol);

				listRolesInFile.add(x);
			}

			// Delete roles in db that are not in the yml file
			if(rolService.listRoles() != null) {
				List<Rol> listRoles = rolService.listRoles();

				for (Rol rol : listRoles) {
					if (!listRolesInFile.contains(rol.getName())) {
						rolService.delete(rol);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
