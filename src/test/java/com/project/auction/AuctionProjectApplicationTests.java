package com.project.auction;

import com.project.auction.dto.ItemDto;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Item;
import com.project.auction.model.ItemImage;
import com.project.auction.model.Person;
import com.project.auction.repository.ItemRepository;
import com.project.auction.service.ItemService;
import com.project.auction.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class AuctionProjectApplicationTests {

	ItemService itemService;
	PersonService personService;

	@Autowired
	public AuctionProjectApplicationTests(ItemService itemService, PersonService personService) {
		this.itemService = itemService;
		this.personService = personService;
	}

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional(readOnly = true)
	void a() {
		try {
			Person p = personService.getPersonById(1);
			List<Item> subastasActivas = p.getItems().stream().filter(item -> !item.isFinalized()).toList();
			List<Item> subastasFinalizadas = p.getItems().stream().filter(Item::isFinalized).toList();

			System.out.println("activas: "+subastasActivas.size());
			System.out.println("finalizado: "+subastasFinalizadas.size());

		} catch (UnkownIdentifierException e) {
			e.printStackTrace();
		}
	}

//	@Test
//	void testSaveOrder(){
//
//		// create Order object
//		ItemDto itemDto = new ItemDto();
//
//		ItemImage itemImage = new ItemImage();
//		itemImage.setBytes(new byte[]{0,1,0});
//		itemImage.setFileName("hola");
//		itemImage.setContentType("jpg");
//
//		ItemImage itemImage2 = new ItemImage();
//		itemImage.setBytes(new byte[]{1,1,0});
//		itemImage.setFileName("h241");
//		itemImage.setContentType("jpg");
//
//		itemDto.setName("a");
//		itemDto.setDescription("b");
//		itemDto.setStartDate(new Date(new Date().getTime()+1000000));
//		itemDto.setFinishDate(new Date(new Date().getTime()+4000000));
//
//		itemDto.getItemImages().add(itemImage);
//		itemDto.getItemImages().add(itemImage2);
//
//		Item item = itemService.getItem(itemDto);
//		long id = itemService.save(item);
//		System.out.println(id);
//	}

}
